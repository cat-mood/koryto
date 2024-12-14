CREATE VIEW orders_view AS
    SELECT
        order_id,
        user_id,
        cost,
        created_at,
        part_id
    FROM
        orders
        JOIN orders_body USING (order_id)
