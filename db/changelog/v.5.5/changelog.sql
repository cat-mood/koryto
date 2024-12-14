DROP VIEW IF EXISTS orders_view;

ALTER TABLE orders_body
ADD COLUMN amount SMALLINT;

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
    price,
    amount
FROM
    orders
        JOIN orders_body USING (order_id)
        JOIN parts USING (part_id);
