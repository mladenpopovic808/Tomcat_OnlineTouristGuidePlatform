package rs.raf.demo.repositories.implementations;

import rs.raf.demo.entities.Category;
import rs.raf.demo.repositories.MySqlAbstractRepository;
import rs.raf.demo.repositories.interfaces.CategoryRepository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SqlCategoryRepository extends MySqlAbstractRepository implements CategoryRepository {
    @Override
    public void deleteCategory(Integer id) throws Exception {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = this.newConnection();

            //if there are articles with this category,dont delete
            preparedStatement = connection.prepareStatement("SELECT * FROM articles WHERE categoryId = ?");
            preparedStatement.setInt(1, id);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                throw new Exception("Artitkli sa ovom kategorijom jos uvek postoje");
            }


            preparedStatement = connection.prepareStatement("DELETE FROM categories WHERE id = ?");
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            this.closeStatement(preparedStatement);
            this.closeConnection(connection);
        }
    }



    @Override
    public Category addCategory(Category category) throws CategoryAlreadyExistsException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = newConnection();

            //check if category with that name exists
            preparedStatement = connection.prepareStatement("SELECT * FROM categories WHERE name = ?");
            preparedStatement.setString(1, category.getName());
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                //go to catch block
               throw new CategoryAlreadyExistsException("Category with that name already exists");

            }

            preparedStatement = connection.prepareStatement("INSERT INTO categories (name, description) VALUES (?, ?)",
                    Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, category.getName());
            preparedStatement.setString(2, category.getDescription());
            preparedStatement.executeUpdate();
            resultSet = preparedStatement.getGeneratedKeys();

            if (resultSet.next()) {
                category.setId(resultSet.getInt(1));
            }
        } catch (SQLException e) {
            //e.printStackTrace();
        }
        finally {
            closeStatement(preparedStatement);
            closeResultSet(resultSet);
            closeConnection(connection);
        }
        return category;
    }
    public class CategoryAlreadyExistsException extends Exception {
        public CategoryAlreadyExistsException(String message) {
            super(message);
        }
    }


    @Override
    public Category updateCategory(Category category) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = newConnection();
            preparedStatement = connection.prepareStatement("UPDATE categories SET name = ?, description = ? WHERE id = ?");
            preparedStatement.setString(1, category.getName());
            preparedStatement.setString(2, category.getDescription());
            preparedStatement.setInt(3, category.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeStatement(preparedStatement);
            closeConnection(connection);
        }

        return category;
    }

    @Override
    public Category findCategory(Integer id) {
        Category category = null;

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = this.newConnection();
            preparedStatement = connection.prepareStatement("SELECT * FROM categories WHERE id = ?");
            preparedStatement.setInt(1, id);
            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                Integer catId = resultSet.getInt("id");
                String name = resultSet.getString("name");
                String description = resultSet.getString("description");

                category = new Category(catId, name, description);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return category;
    }

    @Override
    public Category findCategoryByName(String name) {
        Category category = null;

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = this.newConnection();
            preparedStatement = connection.prepareStatement("SELECT * FROM categories WHERE name = ?");
            preparedStatement.setString(1, name);
            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                Integer id = resultSet.getInt("id");
                String catName = resultSet.getString("name");
                String description = resultSet.getString("description");

                category = new Category(id, catName, description);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return category;
    }

    @Override
    public List<Category> allCategories() {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        List<Category> categories = new ArrayList<>();

        try {
            connection = newConnection();
            preparedStatement = connection.prepareStatement("SELECT * FROM categories");
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                categories.add(new Category(resultSet.getInt("id"),resultSet.getString("name"),
                        resultSet.getString("description")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeStatement(preparedStatement);
            closeResultSet(resultSet);
            closeConnection(connection);
        }

        return categories;
    }

    @Override
    public List<Category> categoriesByPage(Integer pageNum) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        List<Category> categories = new ArrayList<>();

        try {
            connection = newConnection();
            preparedStatement = connection.prepareStatement("SELECT * FROM categories LIMIT 5 OFFSET ?");
            preparedStatement.setInt(1, (pageNum - 1) * 5);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                Category category = new Category(
                        resultSet.getInt("id"),
                        resultSet.getString("name"),
                        resultSet.getString("description"));
                categories.add(category);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeStatement(preparedStatement);
            closeResultSet(resultSet);
            closeConnection(connection);
        }

        return categories;
    }
}
