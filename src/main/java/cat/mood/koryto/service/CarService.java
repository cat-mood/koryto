package cat.mood.koryto.service;

import cat.mood.koryto.model.Car;
import cat.mood.koryto.model.CarView;
import cat.mood.koryto.model.User;
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

    public Car getCarByBrandNameAndModelName(String brand, String modelName) {
        return carDAO.getCarByBrandNameAndModelName(brand, modelName);
    }

    public CarView getCarByBrandAndModel(int brandId, int modelId) {
        return carDAO.getCarByBrandAndModel(brandId, modelId);
    }

    public Car getByUser(User user) {
        return carDAO.getCarById(user.getCarId());
    }
}
