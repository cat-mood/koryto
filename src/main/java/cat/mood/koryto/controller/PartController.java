package cat.mood.koryto.controller;

import cat.mood.koryto.model.Part;
import cat.mood.koryto.model.PartView;
import cat.mood.koryto.service.PartService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/parts")
@Slf4j
@RequiredArgsConstructor
public class PartController {
    final PartService partService;

    @GetMapping
    public List<PartView> getParts() {
        return partService.getAll();
    }
}
