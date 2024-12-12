package cat.mood.koryto.controller;

import cat.mood.koryto.model.Part;
import cat.mood.koryto.model.PartView;
import cat.mood.koryto.service.PartService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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
        List<PartView> parts = partService.getAll();
        Collections.sort(parts);
        model.addAttribute("parts", parts);
        return "admin";
    }

    @PostMapping("/add-part")
    @ResponseBody
    public ResponseEntity<Void> addPart(@Valid @RequestBody PartView partView) {
        log.info("addPart(): category name: " + partView.getCategoryName());
        partService.add(partView);

        return ResponseEntity.ok().build();
    }
}
