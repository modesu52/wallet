package com.finance.wallet.model;

import java.time.LocalDateTime;
import jakarta.persistence.*;

@Entity // Говорит SPRING: этот класс будет таблицей в базе данных
@Table(name= "transactions") // имя таблицы (если не вписывать, то будет "transaction"
public class Transaction {

    @Id // это поле будет первичным ключом (уникальный ID)
    @GeneratedValue(strategy =  GenerationType.IDENTITY) // ID будет генерироваться автоматически
    private Long id; // это уже переменная

    @Column(nullable = false)  // Поле не может быть пустым в базе
    private Double amount;      // Сумма транзакции

    @Column(nullable = false)
    private LocalDateTime date;  // Дата и время операции

    private String description;  // Описание (что купил/получил)

    @Enumerated(EnumType.STRING)  // Сохранять enum как строку (INCOME/EXPENSE)
    @Column(nullable = false)
    private TransactionType type;  // Тип: доход или расход

    // Конструктор по умолчанию (обязателен для JPA), Hibernate требует наличия пустого конструктора,
    //чтобы создавать объекты, когда читает данные из базы
    public Transaction() {}

    // Конструктор для удобного создания транзакций
    public Transaction(Double amount, LocalDateTime date, String description, TransactionType type) {
        this.amount = amount;
        this.date = date;
        this.description = description;
        this.type = type;
    }

    // Геттеры и сеттеры (методы для доступа к полям)

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public TransactionType getType() {
        return type;
    }

    public void setType(TransactionType type) {
        this.type = type;
    }

}
