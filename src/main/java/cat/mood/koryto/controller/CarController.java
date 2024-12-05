package cat.mood.koryto.controller;

import cat.mood.koryto.repository.CarDAO;
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
    final CarDAO carDAO;

    @GetMapping("/brands")
    public List<String> getBrands() {
        return carDAO.getAllBrands();
    }

    @GetMapping("/models")
    public List<String> getModelsByBrand(@RequestParam String brand) {
        return carDAO.getModelsByBrand(brand);
    }
}
