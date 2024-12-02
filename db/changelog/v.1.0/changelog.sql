CREATE TABLE cars (
    car_id SERIAL PRIMARY KEY,
    car_brand VARCHAR(255),
    car_model VARCHAR(255)
);

CREATE TABLE users (
    user_id SERIAL PRIMARY KEY,
    username VARCHAR(255) NOT NULL,
    password VARCHAR(255) NOT NULL,
    first_name VARCHAR(255),
    middle_name VARCHAR(255),
    last_name VARCHAR(255),
    birth_date DATE,
    city VARCHAR(255),
    address VARCHAR(255),
    post_index SMALLINT,
    car_id BIGINT,
    FOREIGN KEY (car_id) REFERENCES cars
);

-- password is 'qwerty'
INSERT INTO users (username, password) VALUES (
    'admin', '$2a$10$LlbNQ0dczQ101Ke1U4QRRexDxewBNjB8PmT90kz.QCN4fStZsmOfG'
)
