CREATE OR REPLACE VIEW parts_view AS
SELECT
    part_name,
    category_name,
    manufacturer_name,
    manufacturer_address,
    manufacturer_phone_number,
    car_brand_name,
    car_model_name,
    part_description
FROM
    parts
        JOIN categories USING (category_id)
        JOIN manufacturers USING (manufacturer_id)
        LEFT JOIN cars USING (car_id)
        LEFT JOIN car_brands USING (car_brand_id)
        LEFT JOIN car_models USING (car_model_id);
