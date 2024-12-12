package cat.mood.koryto.controller;

import cat.mood.koryto.model.Manufacturer;
import cat.mood.koryto.service.ManufacturerService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/manufacturer")
@RequiredArgsConstructor
public class ManufacturerController {
    final ManufacturerService service;

    @GetMapping
    public List<Manufacturer> getAllManufacturers() {
        return service.getAllManufacturers();
    }
}
