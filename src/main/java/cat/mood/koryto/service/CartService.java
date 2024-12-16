package cat.mood.koryto.service;

import cat.mood.koryto.model.Cart;
import cat.mood.koryto.model.CartView;
import cat.mood.koryto.repository.CartDAO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class CartService {
    final CartDAO cartDAO;

    public List<CartView> getCartByUserId(int userId) throws Exception {
        return cartDAO.getCartByUserId(userId);
    }

    public void addPartToCart(Cart cart) throws Exception {
        cartDAO.addPartToCart(cart);
    }

    public int getCartSizeByUserId(int userId) throws Exception {
        List<CartView> cart = cartDAO.getCartByUserId(userId);
        int size = 0;

        for (CartView cartView : cart) {
            size += cartView.getAmount();
        }

        return size;
    }

    public void updateCart(Cart cart) throws Exception {
        cartDAO.updatePart(cart);
    }

    public void deletePart(Cart cart) throws Exception {
        cartDAO.deletePart(cart);
    }

    public double getTotal(int userId) throws Exception {
        List<CartView> cart = cartDAO.getCartByUserId(userId);
        double total = 0;
        for (CartView cartView : cart) {
            total += cartView.getPrice() * cartView.getAmount();
        }

        return total;
    }
}
