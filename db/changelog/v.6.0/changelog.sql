create function clean_cart_func()
    returns trigger as
$clean_cart_func$
begin
    delete from cart where user_id = NEW.user_id;
    return null;
end
$clean_cart_func$
    language plpgsql;

create trigger clean_cart_trigger
    after insert on orders
    for each row
execute function clean_cart_func();
