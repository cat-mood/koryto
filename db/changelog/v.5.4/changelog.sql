CREATE OR REPLACE VIEW orders_view AS
SELECT
    order_id,
    user_id,
    cost,
    created_at,
    part_id,
    part_name,
    category_id,
    manufacturer_id,
    car_id,
    part_description,
    price
FROM
    orders
        JOIN orders_body USING (order_id)
        JOIN parts USING (part_id);
