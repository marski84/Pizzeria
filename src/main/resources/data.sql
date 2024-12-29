INSERT INTO ingredient (product_name, amount_in_stock, minimum_required_amount, unit_type, created_at)
VALUES
    -- Podstawowe składniki do pizzy
    ('Mąka do pizzy', 100000, 20000, 'GRAMS', CURRENT_TIMESTAMP),
    ('Pomidory krojone', 50000, 10000, 'GRAMS', CURRENT_TIMESTAMP),
    ('Mozzarella', 30000, 5000, 'GRAMS', CURRENT_TIMESTAMP),
    ('Oliwa z oliwek', 5000, 1000, 'MILLILITERS', CURRENT_TIMESTAMP),
    ('Drożdże', 1000, 200, 'GRAMS', CURRENT_TIMESTAMP),

    -- Zioła i przyprawy
    ('Bazylia świeża', 2000, 500, 'GRAMS', CURRENT_TIMESTAMP),
    ('Oregano suszone', 1500, 300, 'GRAMS', CURRENT_TIMESTAMP),
    ('Rozmaryn świeży', 1000, 200, 'GRAMS', CURRENT_TIMESTAMP),
    ('Tymianek', 800, 150, 'GRAMS', CURRENT_TIMESTAMP),
    ('Czosnek granulowany', 2000, 400, 'GRAMS', CURRENT_TIMESTAMP),

    -- Mięsa
    ('Szynka', 15000, 3000, 'GRAMS', CURRENT_TIMESTAMP),
    ('Pepperoni', 12000, 2500, 'GRAMS', CURRENT_TIMESTAMP),
    ('Chorizo', 10000, 2000, 'GRAMS', CURRENT_TIMESTAMP),
    ('Salami', 10000, 2000, 'GRAMS', CURRENT_TIMESTAMP),
    ('Boczek', 8000, 1500, 'GRAMS', CURRENT_TIMESTAMP),
    ('Kurczak grillowany', 15000, 3000, 'GRAMS', CURRENT_TIMESTAMP),
    ('Prosciutto crudo', 5000, 1000, 'GRAMS', CURRENT_TIMESTAMP),
    ('Nduja', 3000, 500, 'GRAMS', CURRENT_TIMESTAMP),

    -- Warzywa i grzyby
    ('Pieczarki', 10000, 2000, 'GRAMS', CURRENT_TIMESTAMP),
    ('Cebula', 8000, 2000, 'GRAMS', CURRENT_TIMESTAMP),
    ('Papryka świeża', 7000, 1500, 'GRAMS', CURRENT_TIMESTAMP),
    ('Kukurydza', 5000, 1000, 'GRAMS', CURRENT_TIMESTAMP),
    ('Rukola', 2000, 400, 'GRAMS', CURRENT_TIMESTAMP),
    ('Szpinak świeży', 3000, 600, 'GRAMS', CURRENT_TIMESTAMP),
    ('Pomidory koktajlowe', 5000, 1000, 'GRAMS', CURRENT_TIMESTAMP),
    ('Kapary', 2000, 400, 'GRAMS', CURRENT_TIMESTAMP),

    -- Oliwki i dodatki
    ('Oliwki czarne', 4000, 800, 'GRAMS', CURRENT_TIMESTAMP),
    ('Oliwki zielone', 4000, 800, 'GRAMS', CURRENT_TIMESTAMP),
    ('Anchois', 2000, 400, 'GRAMS', CURRENT_TIMESTAMP),
    ('Karczochy', 3000, 600, 'GRAMS', CURRENT_TIMESTAMP),
    ('Jalapeño', 2000, 400, 'GRAMS', CURRENT_TIMESTAMP),

    -- Sery
    ('Parmezan', 5000, 1000, 'GRAMS', CURRENT_TIMESTAMP),
    ('Gorgonzola', 4000, 800, 'GRAMS', CURRENT_TIMESTAMP),
    ('Ricotta', 5000, 1000, 'GRAMS', CURRENT_TIMESTAMP),
    ('Ser kozi', 3000, 600, 'GRAMS', CURRENT_TIMESTAMP),
    ('Fontina', 4000, 800, 'GRAMS', CURRENT_TIMESTAMP),

    -- Sosy i bazy
    ('Sos pomidorowy', 20000, 4000, 'MILLILITERS', CURRENT_TIMESTAMP),
    ('Pesto bazyliowe', 5000, 1000, 'GRAMS', CURRENT_TIMESTAMP),
    ('Sos BBQ', 5000, 1000, 'MILLILITERS', CURRENT_TIMESTAMP),
    ('Oliwa truflowa', 2000, 400, 'MILLILITERS', CURRENT_TIMESTAMP)
ON CONFLICT (product_name) DO UPDATE
    SET amount_in_stock         = EXCLUDED.amount_in_stock,
        minimum_required_amount = EXCLUDED.minimum_required_amount,
        unit_type               = EXCLUDED.unit_type,
        updated_at              = CURRENT_TIMESTAMP;