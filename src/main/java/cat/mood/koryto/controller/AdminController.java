package cat.mood.koryto.controller;

import cat.mood.koryto.model.Part;
import cat.mood.koryto.model.PartView;
import cat.mood.koryto.model.User;
import cat.mood.koryto.service.PartService;
import cat.mood.koryto.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/admin")
@RequiredArgsConstructor
@Slf4j
public class AdminController {
    final PartService partService;
    final UserService userService;

    @GetMapping
    public String admin(Model model) {
        List<PartView> parts;
        List<User> users;
        try {
            parts = partService.getAll();
            users = userService.getAll();
        } catch (Exception e) {
            log.error(e.getMessage());
            parts = Collections.emptyList();
            users = Collections.emptyList();
            return "redirect:/error";
        }
        Collections.sort(parts);
        model.addAttribute("parts", parts);
        model.addAttribute("users", users);
        return "admin";
    }

    @PostMapping("/add-part")
    @ResponseBody
    public ResponseEntity<String> addPart(@Valid @RequestBody PartView partView) {
        try {
            partService.add(partView);
        } catch (Exception e) {
            log.error(e.getMessage());
            return ResponseEntity.internalServerError().body(e.getMessage());
        }

        return ResponseEntity.ok().build();
    }

    @PostMapping("/update-part")
    @ResponseBody
    public ResponseEntity<String> updatePart(@Valid @RequestBody PartView partView) {
        try {
            partService.update(partView);
        } catch (Exception e) {
            log.error(e.getMessage());
            return ResponseEntity.internalServerError().body(e.getMessage());
        }

        return ResponseEntity.ok().build();
    }

    @PostMapping("/update-role")
    @ResponseBody
    public ResponseEntity<String> updateUser(@Valid @RequestBody User user) {
        log.info(user.toString());
        try {
            userService.updateRole(user.getUserId(), user.getRole());
        } catch (Exception e) {
            log.error(e.getMessage());
            return ResponseEntity.internalServerError().body(e.getMessage());
        }

        return ResponseEntity.ok().build();
    }

    @PostMapping("/delete-user")
    @ResponseBody
    public ResponseEntity<String> deleteUser(@Valid @RequestBody Map<String, Integer> id) {
        try {
            userService.deleteUser(id.get("id"));
        } catch (Exception e) {
            log.error(e.getMessage());
            return ResponseEntity.internalServerError().body(e.getMessage());
        }

        return ResponseEntity.ok().build();
    }
}
