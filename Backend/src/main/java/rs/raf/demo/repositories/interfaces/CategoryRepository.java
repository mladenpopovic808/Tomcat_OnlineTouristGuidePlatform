package rs.raf.demo.repositories.interfaces;

import rs.raf.demo.entities.Category;
import rs.raf.demo.repositories.implementations.SqlCategoryRepository;

import java.util.List;

public interface CategoryRepository {

    public void deleteCategory(Integer id) throws Exception;
    public Category addCategory(Category category) throws SqlCategoryRepository.CategoryAlreadyExistsException;
    public Category updateCategory(Category category);
    public Category findCategory(Integer id);
    public Category findCategoryByName(String name);
    public List<Category> allCategories();
    public List<Category> categoriesByPage(Integer pageNum);

}
