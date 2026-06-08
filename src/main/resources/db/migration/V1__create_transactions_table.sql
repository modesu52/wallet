CREATE TABLE IF NOT EXISTS transactions (
       id      BIGSERIAL PRIMARY KEY,
    amount      FLOAT       NOT NULL,
    date        TIMESTAMP   NOT NULL,
    description VARCHAR(255),
    type        VARCHAR(255) NOT NULL CHECK (type IN ('INCOME', 'EXPENSE'))
);