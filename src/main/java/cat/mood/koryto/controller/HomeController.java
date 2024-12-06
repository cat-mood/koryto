package cat.mood.koryto.controller;

import cat.mood.koryto.service.PartService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.sql.SQLOutput;

@Controller
@Slf4j
@RequiredArgsConstructor
public class HomeController {
    final PartService partService;

    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("parts", partService.getAll());
        return "home";
    }
}
