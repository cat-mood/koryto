CREATE OR REPLACE VIEW cars_view AS
SELECT
    car_brand_name,
    car_model_name,
    car_brand_id,
    car_model_id,
    car_id
FROM
    cars
        JOIN car_brands USING (car_brand_id)
        JOIN car_models USING (car_model_id);