INSERT INTO supply_system.ingredients (product_name, amount_in_stock, minimum_required_amount, unit_type, created_at)
VALUES
    -- Podstawowe składniki do pizzy
    ('Mąka do pizzy', 100, 20000, 'GRAMS', CURRENT_TIMESTAMP),
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

INSERT INTO ordering_system.customers (first_name, last_name, address, email, phone_number)
VALUES ('Jan', 'Kowalski', 'ul. Kwiatowa 1, Warszawa', 'jan.kowalski@email.com', '+48123456789'),
       ('Anna', 'Nowak', 'ul. Słoneczna 2, Kraków', 'anna.nowak@email.com', '+48234567890'),
       ('Piotr', 'Wiśniewski', 'ul. Leśna 3, Wrocław', 'piotr.wisniewski@email.com', '+48345678901'),
       ('Maria', 'Wójcik', 'ul. Polna 4, Poznań', 'maria.wojcik@email.com', '+48456789012'),
       ('Andrzej', 'Kowalczyk', 'ul. Ogrodowa 5, Gdańsk', 'andrzej.kowalczyk@email.com', '+48567890123'),
       ('Katarzyna', 'Kamińska', 'ul. Długa 6, Łódź', 'katarzyna.kaminska@email.com', '+48678901234'),
       ('Tomasz', 'Lewandowski', 'ul. Krótka 7, Szczecin', 'tomasz.lewandowski@email.com', '+48789012345'),
       ('Barbara', 'Zielińska', 'ul. Szeroka 8, Lublin', 'barbara.zielinska@email.com', '+48890123456'),
       ('Marek', 'Szymański', 'ul. Wąska 9, Białystok', 'marek.szymanski@email.com', '+48901234567'),
       ('Ewa', 'Dąbrowska', 'ul. Prosta 10, Katowice', 'ewa.dabrowska@email.com', '+48012345678'),
       ('Michał', 'Kozłowski', 'ul. Cicha 11, Gdynia', 'michal.kozlowski@email.com', '+48123123123'),
       ('Magdalena', 'Jankowska', 'ul. Głośna 12, Częstochowa', 'magdalena.jankowska@email.com', '+48234234234'),
       ('Krzysztof', 'Wojciechowski', 'ul. Zielona 13, Radom', 'krzysztof.wojciechowski@email.com', '+48345345345'),
       ('Agnieszka', 'Kwiatkowska', 'ul. Żółta 14, Toruń', 'agnieszka.kwiatkowska@email.com', '+48456456456'),
       ('Robert', 'Mazur', 'ul. Czerwona 15, Kielce', 'robert.mazur@email.com', '+48567567567'),
       ('Monika', 'Krawczyk', 'ul. Niebieska 16, Rzeszów', 'monika.krawczyk@email.com', '+48678678678'),
       ('Grzegorz', 'Piotrowski', 'ul. Biała 17, Olsztyn', 'grzegorz.piotrowski@email.com', '+48789789789'),
       ('Joanna', 'Grabowska', 'ul. Czarna 18, Ruda Śląska', 'joanna.grabowska@email.com', '+48890890890'),
       ('Paweł', 'Pawłowski', 'ul. Brązowa 19, Rybnik', 'pawel.pawlowski@email.com', '+48901901901'),
       ('Małgorzata', 'Michalska', 'ul. Szara 20, Tychy', 'malgorzata.michalska@email.com', '+48012012012'),
       ('Adam', 'Nowakowski', 'ul. Różowa 21, Opole', 'adam.nowakowski@email.com', '+48123789456'),
       ('Karolina', 'Adamczyk', 'ul. Fioletowa 22, Gliwice', 'karolina.adamczyk@email.com', '+48234890567'),
       ('Łukasz', 'Dudek', 'ul. Pomarańczowa 23, Bytom', 'lukasz.dudek@email.com', '+48345901678'),
       ('Aleksandra', 'Majewska', 'ul. Granatowa 24, Zabrze', 'aleksandra.majewska@email.com', '+48456012789'),
       ('Marcin', 'Jaworski', 'ul. Bordowa 25, Bielsko-Biała', 'marcin.jaworski@email.com', '+48567123890'),
       ('Natalia', 'Witkowska', 'ul. Miętowa 26, Chorzów', 'natalia.witkowska@email.com', '+48678234901'),
       ('Daniel', 'Walczak', 'ul. Srebrna 27, Tarnów', 'daniel.walczak@email.com', '+48789345012'),
       ('Dominika', 'Sikora', 'ul. Złota 28, Płock', 'dominika.sikora@email.com', '+48890456123'),
       ('Kamil', 'Baran', 'ul. Platynowa 29, Wałbrzych', 'kamil.baran@email.com', '+48901567234'),
       ('Weronika', 'Szczepańska', 'ul. Miedziana 30, Włocławek', 'weronika.szczepanska@email.com', '+48012678345');


-- Orders (50 zamówień z losowym przypisaniem do klientów)
INSERT INTO ordering_system.orders (order_received_date, order_processing_date, order_finalized_date, order_status, customer_id)
VALUES
-- Dzisiejsze zamówienia
(NOW() - INTERVAL '1 hour', NOW() - INTERVAL '30 minutes', NULL, 'PROCESSING', 5),
(NOW() - INTERVAL '2 hours', NOW() - INTERVAL '1 hour', NOW(), 'FINALIZED', 12),
(NOW() - INTERVAL '30 minutes', NULL, NULL, 'NEW', 8),
(NOW() - INTERVAL '45 minutes', NULL, NULL, 'NEW', 15),
(NOW() - INTERVAL '3 hours', NOW() - INTERVAL '2 hours', NOW(), 'FINALIZED', 3),

-- Wczorajsze zamówienia
(NOW() - INTERVAL '1 day 2 hours', NOW() - INTERVAL '1 day 1 hour', NOW() - INTERVAL '1 day', 'FINALIZED', 7),
(NOW() - INTERVAL '1 day 3 hours', NOW() - INTERVAL '1 day 2 hours', NOW() - INTERVAL '1 day 1 hour', 'FINALIZED', 22),
(NOW() - INTERVAL '1 day 4 hours', NOW() - INTERVAL '1 day 3 hours', NOW() - INTERVAL '1 day 2 hours', 'FINALIZED', 19),
(NOW() - INTERVAL '1 day 5 hours', NOW() - INTERVAL '1 day 4 hours', NOW() - INTERVAL '1 day 3 hours', 'FINALIZED', 11),
(NOW() - INTERVAL '1 day 6 hours', NOW() - INTERVAL '1 day 5 hours', NOW() - INTERVAL '1 day 4 hours', 'FINALIZED', 28),

-- Przedwczorajsze zamówienia
(NOW() - INTERVAL '2 days 2 hours', NOW() - INTERVAL '2 days 1 hour', NOW() - INTERVAL '2 days', 'FINALIZED', 14),
(NOW() - INTERVAL '2 days 3 hours', NOW() - INTERVAL '2 days 2 hours', NOW() - INTERVAL '2 days 1 hour', 'FINALIZED',
 6),
(NOW() - INTERVAL '2 days 4 hours', NOW() - INTERVAL '2 days 3 hours', NOW() - INTERVAL '2 days 2 hours', 'FINALIZED',
 25),
(NOW() - INTERVAL '2 days 5 hours', NOW() - INTERVAL '2 days 4 hours', NOW() - INTERVAL '2 days 3 hours', 'FINALIZED',
 17),
(NOW() - INTERVAL '2 days 6 hours', NOW() - INTERVAL '2 days 5 hours', NOW() - INTERVAL '2 days 4 hours', 'FINALIZED',
 9),

-- 3 dni temu
(NOW() - INTERVAL '3 days 2 hours', NOW() - INTERVAL '3 days 1 hour', NOW() - INTERVAL '3 days', 'FINALIZED', 30),
(NOW() - INTERVAL '3 days 3 hours', NOW() - INTERVAL '3 days 2 hours', NOW() - INTERVAL '3 days 1 hour', 'FINALIZED',
 4),
(NOW() - INTERVAL '3 days 4 hours', NOW() - INTERVAL '3 days 3 hours', NOW() - INTERVAL '3 days 2 hours', 'FINALIZED',
 21),
(NOW() - INTERVAL '3 days 5 hours', NOW() - INTERVAL '3 days 4 hours', NOW() - INTERVAL '3 days 3 hours', 'FINALIZED',
 13),
(NOW() - INTERVAL '3 days 6 hours', NOW() - INTERVAL '3 days 5 hours', NOW() - INTERVAL '3 days 4 hours', 'FINALIZED',
 26),

-- 4 dni temu
(NOW() - INTERVAL '4 days 2 hours', NOW() - INTERVAL '4 days 1 hour', NOW() - INTERVAL '4 days', 'FINALIZED', 2),
(NOW() - INTERVAL '4 days 3 hours', NOW() - INTERVAL '4 days 2 hours', NOW() - INTERVAL '4 days 1 hour', 'FINALIZED',
 16),
(NOW() - INTERVAL '4 days 4 hours', NOW() - INTERVAL '4 days 3 hours', NOW() - INTERVAL '4 days 2 hours', 'FINALIZED',
 23),
(NOW() - INTERVAL '4 days 5 hours', NOW() - INTERVAL '4 days 4 hours', NOW() - INTERVAL '4 days 3 hours', 'FINALIZED',
 10),
(NOW() - INTERVAL '4 days 6 hours', NOW() - INTERVAL '4 days 5 hours', NOW() - INTERVAL '4 days 4 hours', 'FINALIZED',
 27),

-- 5 dni temu
(NOW() - INTERVAL '5 days 2 hours', NOW() - INTERVAL '5 days 1 hour', NOW() - INTERVAL '5 days', 'FINALIZED', 1),
(NOW() - INTERVAL '5 days 3 hours', NOW() - INTERVAL '5 days 2 hours', NOW() - INTERVAL '5 days 1 hour', 'FINALIZED',
 18),
(NOW() - INTERVAL '5 days 4 hours', NOW() - INTERVAL '5 days 3 hours', NOW() - INTERVAL '5 days 2 hours', 'FINALIZED',
 29),
(NOW() - INTERVAL '5 days 5 hours', NOW() - INTERVAL '5 days 4 hours', NOW() - INTERVAL '5 days 3 hours', 'FINALIZED',
 8),
(NOW() - INTERVAL '5 days 6 hours', NOW() - INTERVAL '5 days 5 hours', NOW() - INTERVAL '5 days 4 hours', 'FINALIZED',
 20),

-- 6 dni temu
(NOW() - INTERVAL '6 days 2 hours', NOW() - INTERVAL '6 days 1 hour', NOW() - INTERVAL '6 days', 'FINALIZED', 15),
(NOW() - INTERVAL '6 days 3 hours', NOW() - INTERVAL '6 days 2 hours', NOW() - INTERVAL '6 days 1 hour', 'FINALIZED',
 24),
(NOW() - INTERVAL '6 days 4 hours', NOW() - INTERVAL '6 days 3 hours', NOW() - INTERVAL '6 days 2 hours', 'FINALIZED',
 7),
(NOW() - INTERVAL '6 days 5 hours', NOW() - INTERVAL '6 days 4 hours', NOW() - INTERVAL '6 days 3 hours', 'FINALIZED',
 3),
(NOW() - INTERVAL '6 days 6 hours', NOW() - INTERVAL '6 days 5 hours', NOW() - INTERVAL '6 days 4 hours', 'FINALIZED',
 11),

-- 7 dni temu
(NOW() - INTERVAL '7 days 2 hours', NOW() - INTERVAL '7 days 1 hour', NOW() - INTERVAL '7 days', 'FINALIZED', 22),
(NOW() - INTERVAL '7 days 3 hours', NOW() - INTERVAL '7 days 2 hours', NOW() - INTERVAL '7 days 1 hour', 'FINALIZED',
 5),
(NOW() - INTERVAL '7 days 4 hours', NOW() - INTERVAL '7 days 3 hours', NOW() - INTERVAL '7 days 2 hours', 'FINALIZED',
 28),
(NOW() - INTERVAL '7 days 5 hours', NOW() - INTERVAL '7 days 4 hours', NOW() - INTERVAL '7 days 3 hours', 'FINALIZED',
 14),
(NOW() - INTERVAL '7 days 6 hours', NOW() - INTERVAL '7 days 5 hours', NOW() - INTERVAL '7 days 4 hours', 'FINALIZED',
 19),

-- Zamówienia w trakcie realizacji
(NOW() - INTERVAL '1 hour', NOW() - INTERVAL '30 minutes', NULL, 'PROCESSING', 9),
(NOW() - INTERVAL '2 hours', NOW() - INTERVAL '1 hour', NULL, 'PROCESSING', 25),
(NOW() - INTERVAL '3 hours', NOW() - INTERVAL '2 hours', NULL, 'PROCESSING', 13),

-- Nowe zamówienia
(NOW() - INTERVAL '15 minutes', NULL, NULL, 'NEW', 16),
(NOW() - INTERVAL '25 minutes', NULL, NULL, 'NEW', 4),
(NOW() - INTERVAL '35 minutes', NULL, NULL, 'NEW', 21),
(NOW() - INTERVAL '45 minutes', NULL, NULL, 'NEW', 30),
(NOW() - INTERVAL '55 minutes', NULL, NULL, 'NEW', 12),

-- Ostatnie zamówienia dnia
(NOW() - INTERVAL '1 hour', NOW() - INTERVAL '30 minutes', NOW(), 'FINALIZED', 17),
(NOW() - INTERVAL '2 hours', NOW() - INTERVAL '1 hour', NOW() - INTERVAL '30 minutes', 'FINALIZED', 26),
(NOW() - INTERVAL '3 hours', NOW() - INTERVAL '2 hours', NOW() - INTERVAL '1 hour', 'FINALIZED', 10),
(NOW() - INTERVAL '4 hours', NOW() - INTERVAL '3 hours', NOW() - INTERVAL '2 hours', 'FINALIZED', 23);