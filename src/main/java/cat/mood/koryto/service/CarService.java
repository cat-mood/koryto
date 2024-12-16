package cat.mood.koryto.service;

import cat.mood.koryto.model.*;
import cat.mood.koryto.repository.CarDAO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CarService {
    final CarDAO carDAO;

    public List<CarBrand> getAllBrands() throws Exception {
        return carDAO.getAllBrands();
    }

    public List<CarModel> getModelsByBrand(int brandId) throws Exception {
        return carDAO.getModelsByBrandId(brandId);
    }

    public Car getCarByBrandNameAndModelName(String brand, String modelName) throws Exception {
        return carDAO.getCarByBrandNameAndModelName(brand, modelName);
    }

    public Car getCarByBrandAndModel(int brandId, int modelId) throws Exception {
        return carDAO.getCarByBrandAndModel(brandId, modelId);
    }

    public Car getByUser(User user) throws Exception {
        return carDAO.getCarById(user.getCarId());
    }
}
