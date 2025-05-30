package cat.mood.koryto.service;

import cat.mood.koryto.model.Cart;
import cat.mood.koryto.model.CartItem;
import cat.mood.koryto.model.CartView;
import cat.mood.koryto.model.PartView;
import cat.mood.koryto.repository.CartDAO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class CartService {
    private final CartDAO cartDAO;
    private final PartService partService;

    public Cart getCartByUserId(int userId) {
        return cartDAO.getCart(userId);
    }

    public void addPartToCart(CartItem cartItem, int userId) throws Exception {
        PartView partView = partService.getById(cartItem.getPartId());
        cartItem.setPrice(partView.getPrice());
        cartItem.setPartName(partView.getPartName());
        cartDAO.add(cartItem, userId);
    }

    public void updateCart(CartItem cartItem, int userId) {
        cartDAO.add(cartItem, userId);
    }

    public void updateCart(Cart cart) {
        cartDAO.update(cart);
    }

    public void deletePart(int partId, int userId) {
        cartDAO.delete(partId, userId);
    }

    public void clearCart(int userId) {
        cartDAO.clear(userId);
    }

    public int getCartSizeByUserId(int userId) {
        Cart cart = cartDAO.getCart(userId);
        if (cart == null) return 0;
        int cartSize = 0;
        for (CartItem item : cart.getItems().values()) {
            cartSize += item.getAmount();
        }

        return cartSize;
    }

    public double getTotal(int userId) {
        Cart cart = cartDAO.getCart(userId);
        if (cart == null) return 0;
        double total = 0;
        for (CartItem item : cart.getItems().values()) {
            total += item.getAmount() * item.getPrice();
        }
        return total;
    }
}
