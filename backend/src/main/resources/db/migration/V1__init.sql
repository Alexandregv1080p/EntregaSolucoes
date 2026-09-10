CREATE TABLE users (
    id          BIGSERIAL PRIMARY KEY,
    name        VARCHAR(120) NOT NULL,
    email       VARCHAR(160) NOT NULL UNIQUE,
    password    VARCHAR(200) NOT NULL,
    role        VARCHAR(20)  NOT NULL DEFAULT 'USER',
    created_at  TIMESTAMP    NOT NULL DEFAULT now()
);

CREATE TABLE products (
    id          BIGSERIAL PRIMARY KEY,
    title       VARCHAR(160)   NOT NULL,
    description TEXT,
    price       NUMERIC(12,2)  NOT NULL,
    kind        VARCHAR(20)    NOT NULL DEFAULT 'SALE', -- SALE | TRADE
    seller_id   BIGINT         NOT NULL REFERENCES users(id),
    created_at  TIMESTAMP      NOT NULL DEFAULT now()
);
