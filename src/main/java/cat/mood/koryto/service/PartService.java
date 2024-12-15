package cat.mood.koryto.service;

import cat.mood.koryto.model.*;
import cat.mood.koryto.repository.PartDAO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class PartService {
    final PartDAO dao;
    final CategoryService categoryService;
    final ManufacturerService manufacturerService;
    final CarService carService;

    public List<PartView> getAll() {
        return dao.getAll();
    }

    public PartView getById(int id) {
        return dao.getById(id);
    }

    Part toPart(PartView partView) {
        Category category = categoryService.getByName(partView.getCategoryName());
        Manufacturer manufacturer = manufacturerService.getByName(partView.getManufacturerName());
        Car car;
        if (partView.getCarBrandId() == null || partView.getCarModelId() == null) {
            car = carService.getCarByBrandNameAndModelName(partView.getCarBrandName(), partView.getCarModelName());
        } else {
            car = carService.getCarByBrandAndModel(partView.getCarBrandId(), partView.getCarModelId());
        }

        return new Part(
                partView.getPartId(),
                partView.getPartName(),
                category.getCategoryId(),
                manufacturer.getManufacturerId(),
                car.getCarId(),
                partView.getPartDescription(),
                partView.getPrice()
        );
    }

    public void add(PartView partView) {
        Part part = toPart(partView);
        add(part);
    }

    public void add(Part part) {
        dao.add(part);
    }

    public void update(PartView partView) {
        Part part = toPart(partView);
        update(part);
    }

    public void update(Part part) {
        dao.update(part);
    }

    public List<PartView> getRecommended(User user) {
        log.debug("PartService.getRecommended(): {}", user.toString());
        Car car = carService.getByUser(user);
        if (car == null) return List.of();
        log.debug("PartService.getRecommended(): car = {}", car.getCarId());
        return dao.getByBrandAndModel(car.getCarBrandId(), car.getCarModelId());
    }
}
