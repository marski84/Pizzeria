CREATE TABLE IF NOT EXISTS ingredient
(
    id                      BIGSERIAL PRIMARY KEY,
    product_name            VARCHAR(255) NOT NULL UNIQUE,
    amount_in_stock         BIGINT       NOT NULL CHECK (amount_in_stock >= 0),
    minimum_required_amount BIGINT       NOT NULL CHECK (minimum_required_amount >= 0),
    unit_type               VARCHAR(20)  NOT NULL,
    created_at              TIMESTAMP WITH TIME ZONE,
    updated_at              TIMESTAMP WITH TIME ZONE
);

-- Indeks dla nazwy produktu
CREATE INDEX IF NOT EXISTS idx_ingredient_product_name ON ingredient (product_name);

-- Indeks dla sprawdzania stanów magazynowych
CREATE INDEX IF NOT EXISTS idx_ingredient_stock_check ON ingredient (amount_in_stock, minimum_required_amount);