ALTER TABLE orders_body DROP CONSTRAINT orders_body_part_id_fkey;

ALTER TABLE orders_body
    ADD CONSTRAINT orders_body_part_id_fkey
        FOREIGN KEY (part_id) REFERENCES parts (part_id) ON DELETE CASCADE;
