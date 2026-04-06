package com.finance.wallet.controller;

import com.finance.wallet.model.Transaction;
import com.finance.wallet.model.TransactionType;
import com.finance.wallet.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController // Говорит Spring, что это REST контроллер
@RequestMapping("/api/transactions") // Базовый URL для всех методов в этом контроллере
public class TransactionController {

    @Autowired
    private TransactionService transactionService;

    // 1. POST /api/transactions - создать новую транзакцию
    @PostMapping
    public ResponseEntity<Transaction> create(@RequestBody Transaction transaction) {
        Transaction saved = transactionService.save(transaction);
        return new ResponseEntity<>(saved, HttpStatus.CREATED); // 201 Created
    }

    // 2. GET /api/transactions - получить все транзакции
    @GetMapping
    public List<Transaction> getAll() {
        return transactionService.findAll();
    }

    // 3. GET /api/transactions/{id} - получить транзакцию по ID
    @GetMapping("{id}")
    public ResponseEntity<Transaction> getById(@PathVariable Long id) {
        Transaction transaction = transactionService.findById(id);
        return ResponseEntity.ok(transaction);
    }

    // 4. GET /api/transactions/balance - получить текущий баланс
    @GetMapping("balance")
    public ResponseEntity<Double> getBalance() {
        Double balance = transactionService.calculateBalance();
        return ResponseEntity.ok(balance);
    }

    // 5. GET /api/transactions/type/{type} - получить транзакции по типу (INCOME/EXPENSE)
    @GetMapping("type/{type}")
    public List<Transaction> getByType(@PathVariable TransactionType type) {
        return transactionService.findByType(type);
    }

    // 6. GET /api/transactions/period - получить за период действия
    @GetMapping("/period")
    public List<Transaction> getByPeriod(@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
                                             LocalDateTime start, @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    LocalDateTime end) {
        return transactionService.findByDateBetween(start, end);
    }

    // 7. PUT /api/transactions/{id} - обновить транзакцию
    @PutMapping("/{id}")
    public ResponseEntity<Transaction> update(@PathVariable Long id, @RequestBody Transaction transaction) {
        Transaction updated = transactionService.update(id, transaction);
        return ResponseEntity.ok(updated);
    }

    // 8. DELETE /api/transactions/{id} - удалить транзакцию
    @DeleteMapping("/{id}")
    public  ResponseEntity<Void> delete(@PathVariable Long id) {
        transactionService.deleteByID(id);
        return ResponseEntity.noContent().build(); // 204 No Content
    }
}
