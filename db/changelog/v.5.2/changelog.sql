DROP VIEW IF EXISTS orders_view;

ALTER TABLE IF EXISTS orders
ALTER COLUMN cost TYPE DECIMAL;

CREATE VIEW orders_view AS
SELECT
    order_id,
    user_id,
    cost,
    created_at,
    part_id
FROM
    orders
        JOIN orders_body USING (order_id);
