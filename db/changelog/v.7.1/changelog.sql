CREATE OR REPLACE VIEW users_statistics AS
with orders_size as (select distinct user_id,
                                     order_id,
                                     sum(amount) over (partition by order_id) as order_size
                     from orders_view),
     avg_order_size as (select user_id,
                               round(avg(order_size), 0)::integer as average_order_size
                        from orders_size
                        group by user_id),
     user_income as (select distinct user_id,
                                     sum(cost) over (partition by user_id) as total_income
                     from orders)

select
    user_id,
    username,
    average_order_size,
    total_income
from
    avg_order_size
        join user_income using (user_id)
        join users using (user_id);

CREATE OR REPLACE VIEW orders_statistics AS
with income as (select distinct part_id,
                                part_name,
                                sum(amount) over (partition by part_id)         as sold_amount,
                                sum(amount) over (partition by part_id) * price as income
                from parts
                         left join orders_body using (part_id)
)

select
    part_id,
    part_name,
    coalesce(sold_amount, 0) as sold_amount,
    coalesce(income, 0) as income
from
    income;

select * from parts_view;
