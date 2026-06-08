# 💰 Wallet — Personal Finance Tracker

Веб-приложение для учёта личных финансов. Позволяет отслеживать доходы и расходы, считать баланс и вести историю транзакций.

## 🛠 Стек технологий

- **Java 21**
- **Spring Boot 4** — основной фреймворк
- **Spring Data JPA + Hibernate** — работа с базой данных
- **PostgreSQL** — база данных
- **Flyway** — версионирование и миграции БД
- **Thymeleaf** — шаблонизатор для HTML
- **Lombok** — уменьшение boilerplate кода
- **Docker + Docker Compose** — контейнеризация

## 📁 Структура проекта

```
src/
└── main/
    ├── java/com/finance/wallet/
    │   ├── config/          # FlywayConfig
    │   ├── controller/      # WebController, TransactionController
    │   ├── model/           # Transaction, TransactionType
    │   ├── repository/      # TransactionRepository
    │   └── service/         # TransactionService
    └── resources/
        ├── db/migration/    # SQL миграции Flyway
        ├── templates/       # HTML шаблоны Thymeleaf
        └── application.properties
```

## 🚀 Запуск через Docker

### Требования
- [Docker Desktop](https://www.docker.com/products/docker-desktop/)

### Запуск одной командой

```bash
docker-compose up --build
```

Приложение будет доступно по адресу: **http://localhost:8080/web/**

### Остановка

```bash
# Остановить контейнеры
docker-compose down

# Остановить и удалить данные базы
docker-compose down -v
```

## 💻 Локальный запуск (без Docker)

### Требования
- Java 21
- PostgreSQL (локально или через Docker)

### Запуск PostgreSQL через Docker

```bash
docker-compose up postgres -d
```

### Запуск приложения

```bash
./mvnw spring-boot:run
```

## ⚙️ Конфигурация

Настройки подключения к базе данных в `src/main/resources/application.properties`:

```properties
spring.datasource.url=jdbc:postgresql://localhost:5433/wallet
spring.datasource.username=wallet_user
spring.datasource.password=wallet_pass
```

## 🗄 Миграции базы данных

Проект использует **Flyway** для управления схемой БД. Миграции хранятся в `src/main/resources/db/migration/`.

Для добавления нового изменения в БД создайте файл по шаблону:
```
V{номер}__{описание}.sql
```

Например: `V2__add_category_to_transactions.sql`

## 📊 Функциональность

- ✅ Добавление транзакций (доход / расход)
- ✅ Просмотр истории транзакций
- ✅ Подсчёт текущего баланса
- ✅ Удаление транзакций