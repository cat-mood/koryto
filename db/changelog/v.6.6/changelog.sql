DROP VIEW IF EXISTS user_register_view;

CREATE VIEW user_register_view AS
SELECT
    username,
    password,
    first_name,
    middle_name,
    last_name,
    birth_date,
    city,
    address,
    post_index,
    car_id,
    email,
    car_brand_id,
    car_model_id
FROM
    users
        JOIN cars_view USING (car_id);