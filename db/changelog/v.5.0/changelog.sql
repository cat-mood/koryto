CREATE TABLE orders (
    order_id SERIAL PRIMARY KEY,
    user_id INTEGER NOT NULL REFERENCES users (user_id),
    cost INTEGER NOT NULL,
    created_at TIMESTAMP NOT NULL
);

CREATE TABLE orders_body (
     order_id INTEGER REFERENCES orders (order_id),
     part_id INTEGER REFERENCES parts (part_id),
     PRIMARY KEY (order_id, part_id)
);
