package cat.mood.koryto.service;

import cat.mood.koryto.repository.CarDAO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CarService {
    final CarDAO carDAO;

    public List<String> getAllBrands() {
        return carDAO.getAllBrands();
    }

    public List<String> getModelsByBrand(String brand) {
        return carDAO.getModelsByBrand(brand);
    }
}
