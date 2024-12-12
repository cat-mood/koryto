package cat.mood.koryto.service;

import cat.mood.koryto.model.Manufacturer;
import cat.mood.koryto.repository.ManufacturerDAO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ManufacturerService {
    final ManufacturerDAO manufacturerDAO;

    public List<Manufacturer> getAll() {
        return manufacturerDAO.getAll();
    }

    public Manufacturer getByName(String name) {
        return manufacturerDAO.getByName(name);
    }
}
