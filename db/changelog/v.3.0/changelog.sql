CREATE TABLE categories (
    category_id SERIAL PRIMARY KEY,
    category_name VARCHAR(255) NOT NULL
);

CREATE TABLE manufacturers (
    manufacturer_id SERIAL PRIMARY KEY,
    manufacturer_name VARCHAR(255) NOT NULL,
    manufacturer_address VARCHAR(255) NOT NULL,
    manufacturer_phone_number CHAR(12) NOT NULL
);

CREATE TABLE parts (
    part_id SERIAL PRIMARY KEY,
    part_name VARCHAR(255) NOT NULL,
    category_id BIGINT NOT NULL REFERENCES categories (category_id),
    manufacturer_id BIGINT NOT NULL REFERENCES manufacturers (manufacturer_id),
    car_id BIGINT REFERENCES cars (car_id),
    part_description TEXT
);

CREATE TABLE cost_changes (
    part_id BIGINT PRIMARY KEY REFERENCES parts (part_id),
    price INT NOT NULL,
    change_time TIMESTAMP NOT NULL
);

-- Insert categories
INSERT INTO categories (category_name) VALUES
('Engine Components'),
('Electrical System'),
('Suspension'),
('Braking System'),
('Exhaust System');

-- Insert manufacturers
INSERT INTO manufacturers (manufacturer_name, manufacturer_address, manufacturer_phone_number) VALUES
('AutoTech Co.', 'Moscow, Nikopolskaya St, 24, building 1', '123-456-7890'),
('DriveWorks Inc.', 'St. Petersburg, Sennaya Sq., 4', '234-567-8901'),
('MotoParts Ltd.', 'Yaroslavl, Kirova St., 90A', '345-678-9012');

-- Insert parts
INSERT INTO parts (part_name, category_id, manufacturer_id, car_id, part_description) VALUES
('Oil Filter', 1, 1, NULL, 'High-performance oil filter for various engines'),
('Spark Plug', 2, 2, NULL, 'Reliable spark plug for better ignition'),
('Brake Pad Set', 4, 3, NULL, 'Durable brake pads for enhanced safety'),
('Exhaust Muffler', 5, 2, NULL, 'Reduces engine noise and emissions'),
('Shock Absorber', 3, 1, NULL, 'Ensures a smooth ride and better control'),
('Air Filter', 1, 2, NULL, 'Improves engine efficiency by filtering air'),
('Alternator', 2, 1, NULL, 'Generates electrical power for vehicle systems'),
('Disc Rotor', 4, 3, NULL, 'Provides excellent braking performance'),
('Catalytic Converter', 5, 1, NULL, 'Reduces harmful emissions'),
('Coil Spring', 3, 3, NULL, 'Supports vehicle weight and absorbs shock');
