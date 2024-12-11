package cat.mood.koryto.controller;

import cat.mood.koryto.model.Part;
import cat.mood.koryto.service.PartService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Collections;
import java.util.List;

@Controller
@RequestMapping("/admin")
@RequiredArgsConstructor
@Slf4j
public class AdminController {
    final PartService partService;
    @GetMapping
    public String admin(Model model) {
        List<Part> parts = partService.getAll();
        Collections.sort(parts);
        model.addAttribute("parts", parts);
        return "admin";
    }
}
