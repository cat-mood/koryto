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
        List<PartView> parts = partService.getAll();
        Collections.sort(parts);
        model.addAttribute("parts", parts);
        List<User> users = userService.getAll();
        model.addAttribute("users", users);
        return "admin";
    }

    @PostMapping("/add-part")
    @ResponseBody
    public ResponseEntity<Void> addPart(@Valid @RequestBody PartView partView) {
        partService.add(partView);

        return ResponseEntity.ok().build();
    }

    @PostMapping("/update-part")
    @ResponseBody
    public ResponseEntity<Void> updatePart(@Valid @RequestBody PartView partView) {
        partService.update(partView);

        return ResponseEntity.ok().build();
    }

    @PostMapping("/update-role")
    @ResponseBody
    public ResponseEntity<Void> updateUser(@Valid @RequestBody User user) {
        log.info(user.toString());
        userService.updateRole(user.getUserId(), user.getRole());

        return ResponseEntity.ok().build();
    }
}
