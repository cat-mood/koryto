package cat.mood.koryto.controller;

import cat.mood.koryto.model.UserDetails;
import cat.mood.koryto.service.CarService;
import cat.mood.koryto.service.CartService;
import cat.mood.koryto.service.PartService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.sql.SQLOutput;

@Controller
@Slf4j
@RequiredArgsConstructor
public class HomeController {
    final PartService partService;
    final CarService carService;

    @GetMapping("/")
    public String home(Model model, @AuthenticationPrincipal UserDetails userDetails) {
        model.addAttribute("parts", partService.getAll());
        model.addAttribute("user", userDetails);
        model.addAttribute("recommended", partService.getRecommended(userDetails.getUser()));
        return "home";
    }
}
