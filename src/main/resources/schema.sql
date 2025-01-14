CREATE SCHEMA IF NOT EXISTS supply_system;
CREATE SCHEMA IF NOT EXISTS inventory;
CREATE SCHEMA IF NOT EXISTS ordering_system;

CREATE TABLE IF NOT EXISTS supply_system.ingredients
(
    id                      BIGSERIAL PRIMARY KEY,
    product_name            VARCHAR(255) NOT NULL UNIQUE,
    amount_in_stock         BIGINT       NOT NULL CHECK (amount_in_stock >= 0),
    minimum_required_amount BIGINT       NOT NULL CHECK (minimum_required_amount >= 0),
    unit_type               VARCHAR(255) NOT NULL,
    created_at              TIMESTAMP WITH TIME ZONE,
    updated_at              TIMESTAMP WITH TIME ZONE
);

CREATE TABLE IF NOT EXISTS inventory.restock_orders
(
    id         BIGSERIAL PRIMARY KEY,
    created_at TIMESTAMP WITH TIME ZONE,
    processed  BOOLEAN DEFAULT FALSE
);

CREATE TABLE IF NOT EXISTS inventory.restock_items
(
    restock_id        BIGINT REFERENCES inventory.restock_orders (id),
    ingredient_id     BIGINT       NOT NULL,
    product_name      VARCHAR(255) NOT NULL,
    current_stock     INTEGER      NOT NULL,
    minimum_required  INTEGER      NOT NULL,
    amount_to_restock INTEGER      NOT NULL
);

CREATE TABLE IF NOT EXISTS ordering_system.customers
(
    id           BIGSERIAL PRIMARY KEY,
    first_name   VARCHAR(255) NOT NULL,
    last_name    VARCHAR(255) NOT NULL,
    address      VARCHAR(255) NOT NULL,
    email        VARCHAR(255) NOT NULL UNIQUE,
    phone_number VARCHAR(20)  NOT NULL UNIQUE,
    CONSTRAINT customer_email_unique UNIQUE (email),
    CONSTRAINT customer_phone_unique UNIQUE (phone_number)
);

CREATE TABLE IF NOT EXISTS ordering_system.orders
(
    id                    BIGSERIAL PRIMARY KEY,
    order_received_date   TIMESTAMP WITH TIME ZONE NOT NULL,
    order_processing_date TIMESTAMP WITH TIME ZONE,
    order_finalized_date  TIMESTAMP WITH TIME ZONE,
    order_status          VARCHAR(20)              NOT NULL CHECK (order_status IN ('NEW', 'PROCESSING', 'FINALIZED')),
    customer_id           BIGINT                   NOT NULL,
    CONSTRAINT fk_customer
        FOREIGN KEY (customer_id)
            REFERENCES customers (id)
            ON DELETE RESTRICT
);

CREATE INDEX idx_customer_email ON customers (email);
CREATE INDEX idx_customer_phone ON customers (phone_number);
CREATE INDEX idx_order_status ON orders (order_status);
CREATE INDEX idx_order_customer ON orders (customer_id);
CREATE INDEX idx_order_received_date ON orders (order_received_date);