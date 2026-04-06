// Сервис — это слой, где живет логика приложения (расчеты, проверки, вызовы репозитория).

package com.finance.wallet.service;

import com.finance.wallet.model.Transaction;
import com.finance.wallet.model.TransactionType;
import com.finance.wallet.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.jpa.LocalEntityManagerFactoryBean;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Locale;

@Service // Говорит Spring, что это сервис (бизнес-логика)
public class TransactionService {

    @Autowired // Spring сам сюда поставит нужный репозиторий
    private TransactionRepository transactionRepository;

    // 1. Сохранить проверки
    public Transaction save(Transaction transaction) {
        // Здесь можно добавить проверки
        if (transaction.getAmount() <= 0) {
            throw new IllegalArgumentException("Сумма должна быть положительной");
        }
        if (transaction.getDate() == null) {
            transaction.setDate(LocalDateTime.now());
        }
        return transactionRepository.save(transaction);
    }

    // 2. Получить все транзакции
    public List<Transaction> findAll() {
        return transactionRepository.findAll();
    }

    // 3. Получить транзакцию по ID
    public Transaction findById(Long id) {
        return transactionRepository.findById(id).orElseThrow(() -> new
                RuntimeException("Транзакция не найдена с Id " + id));
    }

    // 4. Получить транзакции по типу
    public List<Transaction> findByType(TransactionType type) {
        return transactionRepository.findByType(type);
    }

    // 5. Получить транзакции за период
    public List<Transaction> findByDateBetween(LocalDateTime start, LocalDateTime end) {
        return transactionRepository.findByDateBetween(start, end);
    }

    // 6. Посчитать баланс
    public Double calculateBalance() {
        List<Transaction> allTransactions = transactionRepository.findAll();

        double totalIncome = allTransactions.stream()
                .filter(t -> t.getType() == TransactionType.INCOME)
                .mapToDouble(Transaction::getAmount)
                .sum();

        double totalExpense = allTransactions.stream()
                .filter(t -> t.getType() == TransactionType.EXPENSE)
                .mapToDouble(Transaction::getAmount)
                .sum();

        return totalIncome - totalExpense;
    }

    // 7. Удалить транзакцию
    public void deleteByID(Long id) {
        transactionRepository.deleteById(id);
    }

    // 8. Обновить транзакцию
    public Transaction update(Long id, Transaction transactionDetails) {
        Transaction existing = findById(id);
        existing.setAmount(transactionDetails.getAmount());
        existing.setDate(transactionDetails.getDate());
        existing.setDescription(transactionDetails.getDescription());
        existing.setType(transactionDetails.getType());

        return transactionRepository.save(existing);
    }
}
