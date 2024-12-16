package cat.mood.koryto.controller;

import cat.mood.koryto.model.Part;
import cat.mood.koryto.model.PartView;
import cat.mood.koryto.service.PartService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/part")
@Slf4j
@RequiredArgsConstructor
public class PartController {
    final PartService partService;

    @GetMapping("/all")
    public ResponseEntity<List<PartView>> getParts() {
        List<PartView> parts;
        try {
            parts = partService.getAll();
        } catch (Exception e) {
            log.error(e.getMessage());
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(parts);
    }

    @GetMapping
    public ResponseEntity<PartView> getPart(@RequestParam int id) {
        PartView part;
        try {
            part = partService.getById(id);
        } catch (Exception e) {
            log.error(e.getMessage());
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(part);
    }
}
