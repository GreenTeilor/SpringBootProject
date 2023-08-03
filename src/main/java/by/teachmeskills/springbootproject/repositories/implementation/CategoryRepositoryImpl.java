package by.teachmeskills.springbootproject.repositories.implementation;

import by.teachmeskills.springbootproject.entities.Category;
import by.teachmeskills.springbootproject.exceptions.EntityOperationException;
import by.teachmeskills.springbootproject.repositories.CategoryRepository;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

@Repository
public class CategoryRepositoryImpl implements CategoryRepository {
    private final static String GET_CATEGORIES_QUERY = "SELECT * FROM categories";
    private final static String ADD_CATEGORY_QUERY = "INSERT INTO categories (name, imagePath) VALUES (?, ?)";
    private final static String GET_CATEGORY_BY_ID_QUERY = "SELECT * FROM categories WHERE id = ?";
    private final static String DELETE_CATEGORY_BY_ID_QUERY = "DELETE FROM categories WHERE id = ?";
    @Override
    public Category create(Category category) throws EntityOperationException {
        Connection connection = pool.getConnection();
        try (PreparedStatement statement = connection.prepareStatement(ADD_CATEGORY_QUERY)) {
            statement.setString(1, category.getName());
            statement.setString(2, category.getImagePath());
            statement.execute();
            ResultSet autoGenerated = statement.getGeneratedKeys();
            if (autoGenerated.next()) {
                category.setId(autoGenerated.getInt(1));
            }
            autoGenerated.close();
            return category;
        } catch (SQLException e) {
            throw new EntityOperationException("Не удалось создать категорию");
        } finally {
            pool.returnConnection(connection);
        }
    }

    @Override
    public List<Category> read() throws EntityOperationException {
        List<Category> result = new ArrayList<>();
        Connection connection = pool.getConnection();
        try (Statement statement = connection.createStatement()) {
            ResultSet set = statement.executeQuery(GET_CATEGORIES_QUERY);
            while (set.next()) {
                result.add(Category.builder().id(set.getInt("id")).name(set.getString("name")).imagePath(set.getString("imagePath")).build());
            }
            set.close();
            return result;
        } catch (SQLException e) {
            throw new EntityOperationException("Не удалось получить категории");
        } finally {
            pool.returnConnection(connection);
        }
    }

    @Override
    public Category update(Category category) throws EntityOperationException {
        Category updatedCategory = null;
        Connection connection = pool.getConnection();
        try (PreparedStatement statement = connection.prepareStatement(GET_CATEGORY_BY_ID_QUERY)) {
            statement.setInt(1, category.getId());
            ResultSet set = statement.executeQuery();
            if (set.next()) {
                updatedCategory = Category.builder().id(set.getInt("id")).name(set.getString("name")).
                imagePath(set.getString("imagePath")).build();
            }
            set.close();
            return updatedCategory;
        } catch (SQLException e) {
            throw new EntityOperationException("Не удалось обновить категорию");
        } finally {
            pool.returnConnection(connection);
        }
    }

    @Override
    public void delete(int id) throws EntityOperationException {
        Connection connection = pool.getConnection();
        try (PreparedStatement statement = connection.prepareStatement(DELETE_CATEGORY_BY_ID_QUERY)) {
            statement.setString(1, String.valueOf(id));
            statement.execute();
        } catch (SQLException e) {
            throw new EntityOperationException("Не удалось удалить категорию");
        } finally {
            pool.returnConnection(connection);
        }
    }
}
