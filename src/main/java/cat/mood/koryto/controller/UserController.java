package cat.mood.koryto.controller;

import cat.mood.koryto.model.User;
import cat.mood.koryto.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<List<User>> getAll() {
        List<User> users;
        try {
            users = userService.getAll();
        } catch (Exception e) {
            log.error(e.getMessage());
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(users);
    }

    @GetMapping
    public ResponseEntity<User> getById(@RequestParam int id) {
        User user;
        try {
            user = userService.getUserById(id);
        } catch (Exception e) {
            log.error(e.getMessage());
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(user);
    }
}
