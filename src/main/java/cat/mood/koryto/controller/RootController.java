package cat.mood.koryto.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

@Controller("/")
@Slf4j
@RequiredArgsConstructor
public class RootController {
    private final PasswordEncoder passwordEncoder;

    @GetMapping("/login")
    public String login() {
        return "auth";
    }

    @GetMapping("/")
    public String home() {
        return "home";
    }

//    @GetMapping("/crypt")
//    public String crypt(Model model, @RequestParam String password) {
//        model.addAttribute("password", passwordEncoder.encode(password));
//        return "crypt";
//    }

    @GetMapping("/register")
    public String register() {
        return "register";
    }
}
