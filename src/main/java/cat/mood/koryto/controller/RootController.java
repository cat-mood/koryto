package cat.mood.koryto.controller;

import cat.mood.koryto.model.User;
import cat.mood.koryto.repository.UserDAO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Optional;

@Controller("/")
@Slf4j
@RequiredArgsConstructor
public class RootController {
    private final PasswordEncoder passwordEncoder;
    private final UserDAO userDAO;

    @GetMapping("/login")
    public String login() {
        return "auth";
    }

    @GetMapping("/")
    public String home() {
        return "home";
    }

    @PostMapping("/register")
    public String postRegister(@Valid @ModelAttribute User user, BindingResult bindingResult, Model model) {
        Optional<User> foundUser = userDAO.getUserByUsername(user.getUsername());
        if (foundUser.isPresent()) {
            return "redirect:/register?error=1";
        }
        user.setRole("ROLE_USER");
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userDAO.insertUser(user);
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
