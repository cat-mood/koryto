CREATE VIEW cart_view AS
    SELECT
        part_id,
        part_name,
        price,
        user_id,
        amount
    FROM
        cart
        JOIN parts USING (part_id);
