package cat.mood.koryto.service;

import cat.mood.koryto.model.Car;
import cat.mood.koryto.model.Category;
import cat.mood.koryto.model.Manufacturer;
import cat.mood.koryto.model.Part;
import cat.mood.koryto.model.PartView;
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

    public void add(PartView partView) {
        Category category = categoryService.getByName(partView.getCategoryName());
        Manufacturer manufacturer = manufacturerService.getByName(partView.getManufacturerName());
        log.debug("car brand name: " + partView.getCarBrandName());
        Car car = carService.getCarByBrandAndModel(partView.getCarBrandName(), partView.getCarModelName());
        Part part = new Part(
                partView.getPartId(),
                partView.getPartName(),
                category.getCategoryId(),
                manufacturer.getManufacturerId(),
                car.getCarId(),
                partView.getPartDescription(),
                partView.getPrice()
        );

        add(part);
    }

    public void add(Part part) {
        dao.add(part);
    }
}
