

-- Potem tworzymy tabele
CREATE TABLE IF NOT EXISTS ingredients
(
    id                      BIGSERIAL PRIMARY KEY,
    product_name            VARCHAR(255) NOT NULL UNIQUE,
    amount_in_stock         BIGINT       NOT NULL CHECK (amount_in_stock >= 0),
    minimum_required_amount BIGINT       NOT NULL CHECK (minimum_required_amount >= 0),
    unit_type               VARCHAR(255)    NOT NULL,
    created_at              TIMESTAMP WITH TIME ZONE,
    updated_at              TIMESTAMP WITH TIME ZONE
);