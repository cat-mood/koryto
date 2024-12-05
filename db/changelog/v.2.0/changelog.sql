CREATE TABLE cars (
    car_id SERIAL PRIMARY KEY,
    car_brand VARCHAR(255) NOT NULL,
    car_model VARCHAR(255) NOT NULL
);

INSERT INTO cars (car_brand, car_model) VALUES
('Toyota', 'Camry'),
('Toyota', 'Corolla'),
('Toyota', 'RAV4'),
('Toyota', 'Highlander'),
('Toyota', 'Tacoma'),
('Ford', 'F-150'),
('Ford', 'Escape'),
('Ford', 'Explorer'),
('Ford', 'Mustang'),
('Ford', 'Ranger'),
('Honda', 'Civic'),
('Honda', 'Accord'),
('Honda', 'CR-V'),
('Honda', 'Pilot'),
('Honda', 'Odyssey'),
('BMW', '3 Series'),
('BMW', '5 Series'),
('BMW', 'X3'),
('BMW', 'X5'),
('BMW', '7 Series'),
('Mercedes-Benz', 'C-Class'),
('Mercedes-Benz', 'E-Class'),
('Mercedes-Benz', 'S-Class'),
('Mercedes-Benz', 'GLC'),
('Mercedes-Benz', 'GLE');
