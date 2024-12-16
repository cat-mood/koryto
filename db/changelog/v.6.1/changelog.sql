CREATE USER regular_user PASSWORD 'regular_password';

GRANT SELECT, INSERT, UPDATE, DELETE ON TABLE cart TO regular_user;
GRANT SELECT, INSERT ON TABLE users TO regular_user;
GRANT SELECT ON TABLE cars_view, cart_view, orders_view, parts_view, user_register_view TO regular_user;
GRANT SELECT ON TABLE car_brands, car_models, cars, categories, manufacturers, parts TO regular_user;
GRANT SELECT, INSERT ON TABLE orders, orders_body TO regular_user;
GRANT USAGE ON SEQUENCE users_user_id_seq, orders_order_id_seq TO regular_user;
