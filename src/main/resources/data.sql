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
    INSERT INTO ordering_system.customers (first_name, last_name, address, email, phone_number)
    VALUES
        ('Jan', 'Kowalski', 'ul. Kwiatowa 1, Warszawa', 'jan.kowalski@email.com', '+48123456789'),
        ('Anna', 'Nowak', 'ul. Słoneczna 2, Kraków', 'anna.nowak@email.com', '+48234567890'),
        ('Piotr', 'Wiśniewski', 'ul. Leśna 3, Wrocław', 'piotr.wisniewski@email.com', '+48345678901'),
        ('Maria', 'Wójcik', 'ul. Polna 4, Poznań', 'maria.wojcik@email.com', '+48456789012'),
        ('Andrzej', 'Kowalczyk', 'ul. Ogrodowa 5, Gdańsk', 'andrzej.kowalczyk@email.com', '+48567890123');

    -- Inserty dla pizz
    INSERT INTO ordering_system.pizzas (name)
    VALUES
        ('Margherita'),
        ('Marinara'),
        ('Funghi'),
        ('Capricciosa'),
        ('Quattro Formaggi'),
        ('Diavola'),
        ('Prosciutto e Rucola'),
        ('Vegetariana'),
        ('Calabrese'),
        ('Napoletana'),
        ('BBQ Chicken'),
        ('Pepperoni'),
        ('Spinaci'),
        ('Parmigiana'),
        ('Truffle');

    -- Inserty dla składników każdej pizzy
    INSERT INTO ordering_system.pizza_ingredients (pizza_id, ingredient_id, amount)
    VALUES
        -- Margherita
        ((SELECT id FROM ordering_system.pizzas WHERE name = 'Margherita'),
         (SELECT id FROM supply_system.ingredients WHERE product_name = 'Mąka do pizzy'), 200),
        ((SELECT id FROM ordering_system.pizzas WHERE name = 'Margherita'),
         (SELECT id FROM supply_system.ingredients WHERE product_name = 'Sos pomidorowy'), 100),
        ((SELECT id FROM ordering_system.pizzas WHERE name = 'Margherita'),
         (SELECT id FROM supply_system.ingredients WHERE product_name = 'Mozzarella'), 150),
        ((SELECT id FROM ordering_system.pizzas WHERE name = 'Margherita'),
         (SELECT id FROM supply_system.ingredients WHERE product_name = 'Bazylia świeża'), 10),

        -- Marinara
        ((SELECT id FROM ordering_system.pizzas WHERE name = 'Marinara'),
         (SELECT id FROM supply_system.ingredients WHERE product_name = 'Mąka do pizzy'), 200),
        ((SELECT id FROM ordering_system.pizzas WHERE name = 'Marinara'),
         (SELECT id FROM supply_system.ingredients WHERE product_name = 'Sos pomidorowy'), 100),
        ((SELECT id FROM ordering_system.pizzas WHERE name = 'Marinara'),
         (SELECT id FROM supply_system.ingredients WHERE product_name = 'Czosnek granulowany'), 15),
        ((SELECT id FROM ordering_system.pizzas WHERE name = 'Marinara'),
         (SELECT id FROM supply_system.ingredients WHERE product_name = 'Oregano suszone'), 5),

        -- Funghi
        ((SELECT id FROM ordering_system.pizzas WHERE name = 'Funghi'),
         (SELECT id FROM supply_system.ingredients WHERE product_name = 'Mąka do pizzy'), 200),
        ((SELECT id FROM ordering_system.pizzas WHERE name = 'Funghi'),
         (SELECT id FROM supply_system.ingredients WHERE product_name = 'Sos pomidorowy'), 100),
        ((SELECT id FROM ordering_system.pizzas WHERE name = 'Funghi'),
         (SELECT id FROM supply_system.ingredients WHERE product_name = 'Mozzarella'), 150),
        ((SELECT id FROM ordering_system.pizzas WHERE name = 'Funghi'),
         (SELECT id FROM supply_system.ingredients WHERE product_name = 'Pieczarki'), 100),

        -- Quattro Formaggi
        ((SELECT id FROM ordering_system.pizzas WHERE name = 'Quattro Formaggi'),
         (SELECT id FROM supply_system.ingredients WHERE product_name = 'Mąka do pizzy'), 200),
        ((SELECT id FROM ordering_system.pizzas WHERE name = 'Quattro Formaggi'),
         (SELECT id FROM supply_system.ingredients WHERE product_name = 'Mozzarella'), 100),
        ((SELECT id FROM ordering_system.pizzas WHERE name = 'Quattro Formaggi'),
         (SELECT id FROM supply_system.ingredients WHERE product_name = 'Gorgonzola'), 75),
        ((SELECT id FROM ordering_system.pizzas WHERE name = 'Quattro Formaggi'),
         (SELECT id FROM supply_system.ingredients WHERE product_name = 'Parmezan'), 50),
        ((SELECT id FROM ordering_system.pizzas WHERE name = 'Quattro Formaggi'),
         (SELECT id FROM supply_system.ingredients WHERE product_name = 'Fontina'), 75),

        -- Diavola
        ((SELECT id FROM ordering_system.pizzas WHERE name = 'Diavola'),
         (SELECT id FROM supply_system.ingredients WHERE product_name = 'Mąka do pizzy'), 200),
        ((SELECT id FROM ordering_system.pizzas WHERE name = 'Diavola'),
         (SELECT id FROM supply_system.ingredients WHERE product_name = 'Sos pomidorowy'), 100),
        ((SELECT id FROM ordering_system.pizzas WHERE name = 'Diavola'),
         (SELECT id FROM supply_system.ingredients WHERE product_name = 'Mozzarella'), 150),
        ((SELECT id FROM ordering_system.pizzas WHERE name = 'Diavola'),
         (SELECT id FROM supply_system.ingredients WHERE product_name = 'Pepperoni'), 100),
        ((SELECT id FROM ordering_system.pizzas WHERE name = 'Diavola'),
         (SELECT id FROM supply_system.ingredients WHERE product_name = 'Jalapeño'), 30);

    -- Inserty dla orders (zgodne z @PrePersist i @PreUpdate)
    INSERT INTO ordering_system.orders (order_received_date, order_processing_date, order_finalized_date, order_status, customer_id)
    VALUES
        -- Nowe zamówienie
        (CURRENT_TIMESTAMP,
         NULL,
         NULL,
         'NEW',
         (SELECT id FROM ordering_system.customers WHERE email = 'jan.kowalski@email.com')),

        -- Zamówienie w trakcie realizacji
        (CURRENT_TIMESTAMP - interval '1 hour',
         CURRENT_TIMESTAMP - interval '30 minutes',
         NULL,
         'PROCESSING',
         (SELECT id FROM ordering_system.customers WHERE email = 'anna.nowak@email.com')),

        -- Zfinalizowane zamówienie
        (CURRENT_TIMESTAMP - interval '2 hours',
         CURRENT_TIMESTAMP - interval '1 hour',
         CURRENT_TIMESTAMP,
         'FINALIZED',
         (SELECT id FROM ordering_system.customers WHERE email = 'piotr.wisniewski@email.com')),

        -- Wczorajsze zamówienia
        (CURRENT_TIMESTAMP - interval '1 day',
         CURRENT_TIMESTAMP - interval '1 day' + interval '30 minutes',
         CURRENT_TIMESTAMP - interval '1 day' + interval '1 hour',
         'FINALIZED',
         (SELECT id FROM ordering_system.customers WHERE email = 'maria.wojcik@email.com')),

        (CURRENT_TIMESTAMP - interval '1 day 2 hours',
         CURRENT_TIMESTAMP - interval '1 day 1 hour',
         CURRENT_TIMESTAMP - interval '1 day',
         'FINALIZED',
         (SELECT id FROM ordering_system.customers WHERE email = 'andrzej.kowalczyk@email.com'));