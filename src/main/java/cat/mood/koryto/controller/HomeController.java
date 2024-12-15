package cat.mood.koryto.controller;

import cat.mood.koryto.model.User;
import cat.mood.koryto.service.CarService;
import cat.mood.koryto.service.CartService;
import cat.mood.koryto.service.PartService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@Slf4j
@RequiredArgsConstructor
public class HomeController {
    final PartService partService;
    final CarService carService;
    final CartService cartService;

    @GetMapping("/")
    public String home(Model model, @AuthenticationPrincipal User user) {
        model.addAttribute("parts", partService.getAll());
        model.addAttribute("user", user);
        model.addAttribute("recommended", partService.getRecommended(user));
        model.addAttribute("cartSize", cartService.getCartSizeByUserId(user.getUserId()));

        return "home";
    }
}
