package rs.raf.demo.services;

import rs.raf.demo.entities.Category;
import rs.raf.demo.repositories.implementations.SqlCategoryRepository;
import rs.raf.demo.repositories.interfaces.CategoryRepository;

import javax.inject.Inject;
import java.util.List;

public class CategoryService {

    @Inject
    private CategoryRepository categoryRepository;

    public Category addCategory(Category category) throws SqlCategoryRepository.CategoryAlreadyExistsException {
        return categoryRepository.addCategory(category);
    }

    public void deleteCategory(Integer id) throws Exception {
        categoryRepository.deleteCategory(id);
    }

    public Category updateCategory(Category category) {
        return categoryRepository.updateCategory(category);
    }

    public List<Category> allCategories() {
        return categoryRepository.allCategories();
    }

    public Category findCategory(Integer id) {
        return categoryRepository.findCategory(id);
    }

    public Category findCategoryByName(String name) {
        return categoryRepository.findCategoryByName(name);
    }

    public List<Category> categoriesByPage(Integer pageNum) {
        return categoryRepository.categoriesByPage(pageNum);
    }
}
