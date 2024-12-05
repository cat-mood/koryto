DROP TABLE cars;

CREATE TABLE car_brands (
    car_brand_id SERIAL PRIMARY KEY,
    car_brand_name VARCHAR(255) NOT NULL
);

CREATE TABLE car_models (
    car_model_id SERIAL PRIMARY KEY,
    car_model_name VARCHAR(255) NOT NULL
);

CREATE TABLE cars (
    car_id SERIAL PRIMARY KEY,
    car_brand_id BIGINT NOT NULL REFERENCES car_brands (car_brand_id),
    car_model_id BIGINT NOT NULL REFERENCES car_models (car_model_id)
);

INSERT INTO car_brands (car_brand_name) VALUES
('Toyota'),
('Ford'),
('Honda'),
('BMW'),
('Mercedes-Benz');

INSERT INTO car_models (car_model_name) VALUES
('Camry'),
('Corolla'),
('RAV4'),
('Highlander'),
('Tacoma'),
('F-150'),
('Escape'),
('Explorer'),
('Mustang'),
('Ranger'),
('Civic'),
('Accord'),
('CR-V'),
('Pilot'),
('Odyssey'),
('3 Series'),
('5 Series'),
('X3'),
('X5'),
('7 Series'),
('C-Class'),
('E-Class'),
('S-Class'),
('GLC'),
('GLE');

INSERT INTO cars (car_brand_id, car_model_id) VALUES
(1, 1),
(1, 2),
(1, 3),
(1, 4),
(1, 5),
(2, 6),
(2, 7),
(2, 8),
(2, 9),
(2, 10),
(3, 11),
(3, 12),
(3, 13),
(3, 14),
(3, 15),
(4, 16),
(4, 17),
(4, 18),
(4, 19),
(4, 20),
(5, 21),
(5, 22),
(5, 23),
(5, 24),
(5, 25);
