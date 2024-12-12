package cat.mood.koryto.controller;

import cat.mood.koryto.model.Category;
import cat.mood.koryto.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/category")
@RequiredArgsConstructor
public class CategoryController {
    final CategoryService categoryService;

    @GetMapping
    public List<Category> getCategories() {
        return categoryService.getAll();
    }
}
