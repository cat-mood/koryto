package cat.mood.koryto.controller;

import cat.mood.koryto.model.Cart;
import cat.mood.koryto.model.User;
import cat.mood.koryto.service.CartService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Map;

@Controller
@Slf4j
@RequestMapping("/cart")
@RequiredArgsConstructor
public class CartController {
    final CartService cartService;

    @GetMapping
    public String cart(Model model, @AuthenticationPrincipal User user) {
        try {
            int carts = cartService.getCartSizeByUserId(user.getUserId());
            log.info("Cart {}", carts);
            Cart cart = cartService.getCartByUserId(user.getUserId());
            if (cart == null) {
                cart = new Cart();
                cart.setItems(Map.of());
            }
            model.addAttribute("parts", cart);
            model.addAttribute("user", user);
            model.addAttribute("total", cartService.getTotal(user.getUserId()));
            model.addAttribute("cartSize", cartService.getCartSizeByUserId(user.getUserId()));
        } catch (Exception e) {
            log.error(e.getMessage());
            return "redirect:/error";
        }

        return "cart";
    }
}
