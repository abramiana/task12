-- Додавання даних клієнтів
INSERT INTO client (name) VALUES
('Artur'),
('Olga'),
('Anton'),
('Karina'),
('Maks'),
('Oksana'),
('German'),
('Ilona'),
('Viktor'),
('Oleksandr');

-- Додавання даних планет
INSERT INTO planet (id, name) VALUES
('MARS', 'Mars'),
('VEN', 'Venus'),
('MER', 'Mercury'),
('SAT', 'Saturn'),
('JUP', 'Jupiter');

-- Додавання даних квитків
INSERT INTO ticket (created_at, client_id, from_planet_id, to_planet_id) VALUES
('2024-03-30 12:00:00', 1, 'MARS', 'VEN'),
('2024-03-30 13:00:00', 2, 'MER', 'SAT'),
('2024-03-31 10:00:00', 3, 'JUP', 'MARS'),
('2024-03-31 11:00:00', 4, 'VEN', 'MARS'),
('2024-03-31 12:00:00', 5, 'SAT', 'MER'),
('2024-03-31 13:00:00', 6, 'MARS', 'SAT'),
('2024-04-01 10:00:00', 7, 'VEN', 'JUP'),
('2024-04-01 11:00:00', 8, 'MARS', 'VEN'),
('2024-04-01 12:00:00', 9, 'MER', 'JUP'),
('2024-04-01 13:00:00', 10, 'MARS', 'VEN');