-- Inserty dla składników
INSERT INTO supply_system.ingredients (product_name, amount_in_stock, minimum_required_amount, unit_type)
VALUES
    -- Podstawowe składniki do pizzy
    ('Mąka do pizzy', 100000, 20000, 'GRAMS'),
    ('Pomidory krojone', 9000, 10000, 'GRAMS'),
    ('Mozzarella', 4000, 5000, 'GRAMS'),
    ('Oliwa z oliwek', 5000, 1000, 'MILLILITERS'),
    ('Drożdże', 1000, 200, 'GRAMS'),

    -- Zioła i przyprawy
    ('Bazylia świeża', 2000, 500, 'GRAMS'),
    ('Oregano suszone', 1500, 300, 'GRAMS'),
    ('Rozmaryn świeży', 1000, 200, 'GRAMS'),
    ('Tymianek', 800, 150, 'GRAMS'),
    ('Czosnek granulowany', 2000, 400, 'GRAMS'),

    -- Mięsa
    ('Szynka', 15000, 3000, 'GRAMS'),
    ('Pepperoni', 12000, 2500, 'GRAMS'),
    ('Chorizo', 10000, 2000, 'GRAMS'),
    ('Salami', 10000, 2000, 'GRAMS'),
    ('Boczek', 8000, 1500, 'GRAMS'),
    ('Kurczak grillowany', 15000, 3000, 'GRAMS'),
    ('Prosciutto crudo', 5000, 1000, 'GRAMS'),
    ('Nduja', 3000, 500, 'GRAMS'),

    -- Warzywa i grzyby
    ('Pieczarki', 10000, 2000, 'GRAMS'),
    ('Cebula', 8000, 2000, 'GRAMS'),
    ('Papryka świeża', 7000, 1500, 'GRAMS'),
    ('Kukurydza', 5000, 1000, 'GRAMS'),
    ('Rukola', 2000, 400, 'GRAMS'),
    ('Szpinak świeży', 3000, 600, 'GRAMS'),
    ('Pomidory koktajlowe', 5000, 1000, 'GRAMS'),
    ('Kapary', 2000, 400, 'GRAMS'),

    -- Oliwki i dodatki
    ('Oliwki czarne', 4000, 800, 'GRAMS'),
    ('Oliwki zielone', 4000, 800, 'GRAMS'),
    ('Anchois', 2000, 400, 'GRAMS'),
    ('Karczochy', 3000, 600, 'GRAMS'),
    ('Jalapeño', 2000, 400, 'GRAMS'),

    -- Sery
    ('Parmezan', 5000, 1000, 'GRAMS'),
    ('Gorgonzola', 4000, 800, 'GRAMS'),
    ('Ricotta', 5000, 1000, 'GRAMS'),
    ('Ser kozi', 3000, 600, 'GRAMS'),
    ('Fontina', 4000, 800, 'GRAMS'),

    -- Sosy i bazy
    ('Sos pomidorowy', 20000, 4000, 'MILLILITERS'),
    ('Pesto bazyliowe', 5000, 1000, 'GRAMS'),
    ('Sos BBQ', 5000, 1000, 'MILLILITERS'),
    ('Oliwa truflowa', 2000, 400, 'MILLILITERS');

-- Inserty dla customers
INSERT INTO ordering_system.customers (first_name, last_name, age, is_Student, address, email, phone_number)
VALUES ('Jan', 'Kowalski', 10, false, 'ul. Kwiatowa 1, Warszawa', 'jan.kowalski@email.com', '+48123456789'),
       ('Anna', 'Nowak', 18, true, 'ul. Słoneczna 2, Kraków', 'anna.nowak@email.com', '+48234567890'),
       ('Piotr', 'Wiśniewski', 40, false, 'ul. Leśna 3, Wrocław', 'piotr.wisniewski@email.com', '+48345678901'),
       ('Maria', 'Wójcik', 30, true, 'ul. Polna 4, Poznań', 'maria.wojcik@email.com', '+48456789012'),
       ('Andrzej', 'Kowalczyk', 25, true, 'ul. Ogrodowa 5, Gdańsk', 'andrzej.kowalczyk@email.com', '+48567890123');

-- Inserty dla pizz
-- Najpierw inserty dla pizz z cenami
INSERT INTO ordering_system.pizzas (name, price)
VALUES ('Margherita', 32.00),
       ('Marinara', 28.00),
       ('Funghi', 34.00),
       ('Capricciosa', 38.00),
       ('Quattro Formaggi', 42.00),
       ('Quattro Formaggi', 42.00),
       ('Diavola', 36.00),
       ('Prosciutto e Rucola', 44.00),
       ('Vegetariana', 34.00),
       ('Calabrese', 38.00),
       ('Napoletana', 36.00),
       ('BBQ Chicken', 40.00),
       ('Pepperoni', 38.00),
       ('Spinaci', 34.00),
       ('Parmigiana', 36.00),
       ('Truffle', 48.00);

-- Inserty dla pizza_ingredients już bezpośrednio z id składników
INSERT INTO ordering_system.pizza_ingredients (pizza_id, ingredient_id, amount)
VALUES
    -- Margherita (id: 1)
    (1, 1, 200),  -- Mąka do pizzy
    (1, 38, 100), -- Sos pomidorowy
    (1, 3, 150),  -- Mozzarella
    (1, 6, 10),   -- Bazylia świeża

    -- Marinara (id: 2)
    (2, 1, 200),  -- Mąka do pizzy
    (2, 38, 100), -- Sos pomidorowy
    (2, 10, 15),  -- Czosnek granulowany
    (2, 7, 5),    -- Oregano suszone

    -- Funghi (id: 3)
    (3, 1, 200),  -- Mąka do pizzy
    (3, 38, 100), -- Sos pomidorowy
    (3, 3, 150),  -- Mozzarella
    (3, 19, 100), -- Pieczarki

    -- Quattro Formaggi (id: 4)
    (4, 1, 200),  -- Mąka do pizzy
    (4, 3, 100),  -- Mozzarella
    (4, 34, 75),  -- Gorgonzola
    (4, 33, 50),  -- Parmezan
    (4, 37, 75),  -- Fontina

    -- Diavola (id: 5)
    (5, 1, 200),  -- Mąka do pizzy
    (5, 38, 100), -- Sos pomidorowy
    (5, 3, 150),  -- Mozzarella
    (5, 12, 100), -- Pepperoni
    (5, 31, 30);
-- Jalapeño

-- Inserty dla orders (zgodne z @PrePersist i @PreUpdate)
INSERT INTO ordering_system.orders (order_received_date, order_processing_date, order_finalized_date, order_value, order_status,
                                    customer_id)
VALUES
    -- Nowe zamówienie
    (CURRENT_TIMESTAMP,
     NULL,
     NULL,
     200,
     'NEW',
     (SELECT id FROM ordering_system.customers WHERE email = 'jan.kowalski@email.com')),

    -- Zamówienie w trakcie realizacji
    (CURRENT_TIMESTAMP - interval '1 hour',
     CURRENT_TIMESTAMP - interval '30 minutes',
     NULL,
     300,
     'PROCESSING',
     (SELECT id FROM ordering_system.customers WHERE email = 'anna.nowak@email.com')),

    -- Zfinalizowane zamówienie
    (CURRENT_TIMESTAMP - interval '2 hours',
     CURRENT_TIMESTAMP - interval '1 hour',
     CURRENT_TIMESTAMP,
     500,
     'FINALIZED',
     (SELECT id FROM ordering_system.customers WHERE email = 'piotr.wisniewski@email.com')),

    -- Wczorajsze zamówienia
    (CURRENT_TIMESTAMP - interval '1 day',
     CURRENT_TIMESTAMP - interval '1 day' + interval '30 minutes',
     CURRENT_TIMESTAMP - interval '1 day' + interval '1 hour',
     350,
     'FINALIZED',
     (SELECT id FROM ordering_system.customers WHERE email = 'maria.wojcik@email.com')),

    (CURRENT_TIMESTAMP - interval '1 day 2 hours',
     CURRENT_TIMESTAMP - interval '1 day 1 hour',
     CURRENT_TIMESTAMP - interval '1 day',
     400,
     'FINALIZED',
     (SELECT id FROM ordering_system.customers WHERE email = 'andrzej.kowalczyk@email.com'));