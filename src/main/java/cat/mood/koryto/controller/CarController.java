package cat.mood.koryto.controller;

import cat.mood.koryto.service.CarService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/cars")
@RequiredArgsConstructor
@Slf4j
public class CarController {
    final CarService carService;

    @GetMapping("/brands")
    public ResponseEntity<List<String>> getBrands() {
        List<String> brands;

        try {
            brands = carService.getAllBrands();
        } catch (Exception e) {
            log.error(e.getMessage());
            return ResponseEntity.internalServerError().build();
        }

        return ResponseEntity.ok(brands);
    }

    @GetMapping("/models")
    public ResponseEntity<List<String>> getModelsByBrand(@RequestParam String brand) {
        List<String> models;
        try {
            models = carService.getModelsByBrand(brand);
        } catch (Exception e) {
            log.error(e.getMessage());
            return ResponseEntity.internalServerError().build();
        }

        return ResponseEntity.ok(models);
    }
}
