package com.finance.wallet.service;

import com.finance.wallet.model.Transaction;
import com.finance.wallet.model.TransactionType;
import com.finance.wallet.repository.TransactionRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
class TransactionServiceTest {

    @Mock
    private TransactionRepository transactionRepository;

    @InjectMocks
    private TransactionService transactionService;

    @Test
    void save_shouldThrowException_whenAmountIsNotPositive() {
        Transaction transaction = new Transaction(-1000.00, LocalDateTime.now(), "покупка", TransactionType.EXPENSE);
        assertThrows(IllegalArgumentException.class, () -> transactionService.save(transaction));
    }

    @Test
    void save_shouldReturnSavedTransaction_whenAmountIsPositive() {
        Transaction transaction = new Transaction(1000.0, LocalDateTime.now(), "зарплата", TransactionType.INCOME);
        Mockito.when(transactionRepository.save(transaction)).thenReturn(transaction);
        
        Transaction result = transactionService.save(transaction);

        assertEquals(transaction, result);
        Mockito.verify(transactionRepository).save(transaction);
    }

    @Test
    void findById_shouldReturnTransaction_whenExists() {
        Transaction transaction = new Transaction(1000.00, LocalDateTime.now(), "покупка", TransactionType.EXPENSE);
        Mockito.when(transactionRepository.findById(1L)).thenReturn(Optional.of(transaction));
        Transaction result = transactionService.findById(1L);
        assertEquals(transaction, result);
    }

    @Test
    void findById_shouldThrowException_whenNotExists() {
        Mockito.when(transactionRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(RuntimeException.class, () -> transactionService.findById(1L));
    }

    @Test
    void deleteById_shouldCallRepositoryDelete() {
        transactionService.deleteByID(1L);
        Mockito.verify(transactionRepository).deleteById(1L);
    }

    @Test
    void calculateBalance_shouldReturnDifference_betweenIncomeExpense() {
        Transaction expense = new Transaction(1000.00, LocalDateTime.now(), "покупка", TransactionType.EXPENSE);
        Transaction income = new Transaction(1500.0, LocalDateTime.now(), "зарплата", TransactionType.INCOME);

        Mockito.when(transactionRepository.findAll()).thenReturn(List.of(income, expense));

        Double difference = transactionService.calculateBalance();

        assertEquals(500.0, difference);
    }

    @Test
    void update_shouldUpdateFieldsAndSave() {
        Transaction oldTransaction = new Transaction(100.00, LocalDateTime.now(), "old", TransactionType.EXPENSE);
        Transaction newTransaction = new Transaction(150.00, LocalDateTime.now(), "new", TransactionType.INCOME);

        Mockito.when(transactionRepository.findById(1L)).thenReturn(Optional.of(oldTransaction));
        Mockito.when(transactionRepository.save(oldTransaction)).thenReturn(oldTransaction);

        Transaction result = transactionService.update(1L, newTransaction);

        assertEquals(150.0, result.getAmount());
        assertEquals("new", result.getDescription());
        assertEquals(TransactionType.INCOME, result.getType());
    }
}