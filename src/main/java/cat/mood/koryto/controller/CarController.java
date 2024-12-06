package cat.mood.koryto.controller;

import cat.mood.koryto.service.CarService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/cars")
@RequiredArgsConstructor
public class CarController {
    final CarService carService;

    @GetMapping("/brands")
    public List<String> getBrands() {
        return carService.getAllBrands();
    }

    @GetMapping("/models")
    public List<String> getModelsByBrand(@RequestParam String brand) {
        return carService.getModelsByBrand(brand);
    }
}
