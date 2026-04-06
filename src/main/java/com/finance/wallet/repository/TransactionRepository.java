// Репозиторий — это слой, который отвечает за сохранение, поиск и удаление данных в базе.

package com.finance.wallet.repository;


import com.finance.wallet.model.Transaction;
import com.finance.wallet.model.TransactionType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository // говорим SPRING, что это компонент для работы с БД
public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    // Это Описание самого интерфейса
// extends JpaRepository<Transaction, Long> — получаем готовые методы: save(), findAll(), findById(), delete()
// Все методы в интерфейсе Spring реализует автоматически по названию!
    // Найти все транзакции по типу (доход или расход)
    List<Transaction> findByType(TransactionType type);

    // Найти все транзакции за период
    List<Transaction> findByDateBetween(LocalDateTime start, LocalDateTime end);

    // Найти все транзакции по типу за период
    List<Transaction> findByTypeAndDateBetween(TransactionType type, LocalDateTime start, LocalDateTime end);

    // Найти все транзакции, где описание содержит слово (без учета регистра)
    List<Transaction> findByDescriptionContainingIgnoreCase(String keyword);


}
