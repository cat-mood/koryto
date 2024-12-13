package cat.mood.koryto.controller;

import cat.mood.koryto.model.User;
import cat.mood.koryto.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/user")
@Slf4j
@RequiredArgsConstructor
public class UserController {
    final UserService userService;

    @GetMapping("/all")
    public List<User> getAll() {
        return userService.getAll();
    }

    @GetMapping
    public User getById(@RequestParam int id) {
        return userService.getUserById(id);
    }
}
