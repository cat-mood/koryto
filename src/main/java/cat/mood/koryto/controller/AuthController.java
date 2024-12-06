package cat.mood.koryto.controller;

import cat.mood.koryto.exception.UserExist;
import cat.mood.koryto.model.User;
import cat.mood.koryto.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
@Slf4j
@RequiredArgsConstructor
public class AuthController {
    private final UserService userService;

    @GetMapping("/login")
    public String login() {
        return "auth";
    }

    @PostMapping("/register")
    public String postRegister(@Valid @ModelAttribute User user, BindingResult bindingResult, Model model) {
        try {
            userService.registerUser(user);
        } catch (UserExist e) {
            return "redirect:/register?error=1";
        }

        return "redirect:/";
    }

    @GetMapping("/register")
    public String register() {
        return "register";
    }

    @GetMapping("/admin")
    public String admin() {
        return "admin";
    }
}
