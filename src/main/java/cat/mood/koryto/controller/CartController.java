package cat.mood.koryto.controller;

import cat.mood.koryto.model.UserDetails;
import cat.mood.koryto.service.CartService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;

@Controller
@Slf4j
@RequestMapping("/cart")
@RequiredArgsConstructor
public class CartController {
    final CartService cartService;

    @GetMapping
    public String cart(Model model, @AuthenticationPrincipal UserDetails user) {
        model.addAttribute("parts", cartService.getCartByUserId(user.getId()));
        model.addAttribute("user", user);
        model.addAttribute("total", cartService.getTotal(user.getId()));

        return "cart";
    }
}
