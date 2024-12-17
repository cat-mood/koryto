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

    public List<PartView> getAll() throws Exception {
        return dao.getAll();
    }

    public PartView getById(int id) throws Exception {
        return dao.getById(id);
    }

    Part toPart(PartView partView) throws Exception {
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

    public void add(PartView partView) throws Exception {
        Part part = toPart(partView);
        add(part);
    }

    public void add(Part part) throws Exception {
        dao.add(part);
    }

    public void update(PartView partView) throws Exception {
        Part part = toPart(partView);
        update(part);
    }

    public void update(Part part) throws Exception {
        dao.update(part);
    }

    public List<PartView> getRecommended(User user) throws Exception {
        log.debug("PartService.getRecommended(): {}", user.toString());
        if (user.getCarId() == 0) return List.of();
        return dao.getByCarId(user.getCarId());
    }

    public List<PartView> search(SearchParameters params) throws Exception {
        return dao.search(params);
    }

    public void delete(int id) throws Exception {
        dao.delete(id);
    }
}
