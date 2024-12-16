package cat.mood.koryto.controller;

import cat.mood.koryto.model.SearchParameters;
import cat.mood.koryto.model.User;
import cat.mood.koryto.service.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

@Controller
@Slf4j
@RequiredArgsConstructor
public class HomeController {
    final PartService partService;
    final CarService carService;
    final CartService cartService;
    final CategoryService categoryService;
    final ManufacturerService manufacturerService;

    @GetMapping("/")
    public String home(
            Model model,
            @AuthenticationPrincipal User user,
            @ModelAttribute SearchParameters params
    ) {
        log.debug("HomeController.home(): {}", params.toString());
        try {
            model.addAttribute("parts", partService.search(params));
            model.addAttribute("user", user);
            model.addAttribute("recommended", partService.getRecommended(user));
            model.addAttribute("cartSize", cartService.getCartSizeByUserId(user.getUserId()));
            model.addAttribute("categories", categoryService.getAll());
            model.addAttribute("brands", carService.getAllBrands());
            model.addAttribute("manufacturers", manufacturerService.getAll());
        } catch (Exception e) {
            log.error(e.getMessage());
            return "redirect:/error";
        }

        return "home";
    }
}
