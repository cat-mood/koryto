ALTER TABLE orders_body DROP CONSTRAINT orders_body_order_id_fkey;

ALTER TABLE orders_body
    ADD CONSTRAINT orders_body_order_id_fkey
        FOREIGN KEY (order_id) REFERENCES orders (order_id) ON DELETE CASCADE;
