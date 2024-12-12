package cat.mood.koryto.service;

import cat.mood.koryto.model.Category;
import cat.mood.koryto.repository.CategoryDAO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class CategoryService {
    final CategoryDAO categoryDAO;

    public List<Category> getAll() {
        return categoryDAO.getAll();
    }

    public Category getByName(String name) {
        return categoryDAO.getByName(name);
    }
}
