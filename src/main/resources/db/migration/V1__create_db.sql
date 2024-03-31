
-- Креат таблиці клієнтів
CREATE TABLE client (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(200) NOT NULL
);

-- Креат таблиці планет
CREATE TABLE planet (
    id VARCHAR(10) PRIMARY KEY,
    name VARCHAR(500) NOT NULL
);

-- Креат таблиці квитків
CREATE TABLE ticket (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    created_at TIMESTAMP NOT NULL,
    client_id BIGINT,
    from_planet_id VARCHAR(20),
    to_planet_id VARCHAR(20),
    FOREIGN KEY (client_id) REFERENCES client(id),
    FOREIGN KEY (from_planet_id) REFERENCES planet(id),
    FOREIGN KEY (to_planet_id) REFERENCES planet(id)
);