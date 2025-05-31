package cat.mood.koryto.repository;

import cat.mood.koryto.config.DatabaseConfig;
import cat.mood.koryto.model.Cart;
import cat.mood.koryto.model.CartItem;
import cat.mood.koryto.model.CartView;
import cat.mood.koryto.model.Part;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Repository
@RequiredArgsConstructor
@Slf4j
public class CartDAO {

    final RedisTemplate<String, Object> redisTemplate;
    static final String CART_PREFIX = "cart:";
    final PartDAO partDAO;

    final int TIMEOUT = 30;
    TimeUnit TIMEUNIT = TimeUnit.MINUTES;

    public Cart getCart(int userId) {
        String key = CART_PREFIX + userId;

        Cart cart = (Cart) redisTemplate.opsForValue().get(key);
        if (cart == null) {
            log.debug("CartDAO.getCart(): cart is null");
            return null;
        }
        return cart;
    }

    public void add(CartItem cartItem, int userId) {
        log.debug("CartDAO.add(): {}", cartItem.toString());
        String key = CART_PREFIX + userId;

        Cart current = (Cart) redisTemplate.opsForValue().get(key);
        if (current == null) {
            current = new Cart(userId, new HashMap<>());
        }

        Map<Integer, CartItem> items = current.getItems();
        log.debug("Current items in cart before update: {}", items);
        if (items.containsKey(cartItem.getPartId())) {
            // Обновляем количество существующего товара
            items.get(cartItem.getPartId()).setAmount(
                (short) (items.get(cartItem.getPartId()).getAmount() + cartItem.getAmount())
            );
        } else {
            try {
                Map<Integer, CartItem> tempItems = new HashMap<>(current.getItems());
                tempItems.put(cartItem.getPartId(), cartItem);
                current.setItems(tempItems);
            } catch (Exception e) {
                log.error("CartDAO.add(): error adding new item to cart", e);
            }
        }

        log.debug("Current items in cart after update: {}", items);
        // Сохраняем обновленную корзину
        redisTemplate.opsForValue().set(key, current, TIMEOUT, TIMEUNIT);
    }

    public void update(Cart cart) {
        String key = CART_PREFIX + cart.getUserId();
        redisTemplate.opsForValue().set(key, cart, TIMEOUT, TIMEUNIT);
    }

    public void delete(int partId, int userId) {
        String key = CART_PREFIX + userId;
        Cart cart = (Cart) redisTemplate.opsForValue().get(key);
        if (cart == null) return;

        cart.getItems().remove(partId);
        redisTemplate.opsForValue().set(key, cart);
    }

    public void clear(int userId) {
        String key = CART_PREFIX + userId;
        redisTemplate.delete(key);
    }
}

