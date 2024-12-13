CREATE TABLE cart (
    user_id INTEGER REFERENCES users (user_id),
    part_id INTEGER REFERENCES parts (part_id),
    amount SMALLINT,
    PRIMARY KEY (user_id, part_id)
);


