package by.teachmeskills.springbootproject.repositories.implementation;

import by.teachmeskills.springbootproject.entities.Product;
import by.teachmeskills.springbootproject.exceptions.UnableToExecuteQueryException;
import by.teachmeskills.springbootproject.repositories.ProductRepository;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

@Repository
public class ProductRepositoryImpl implements ProductRepository {
    private final static String GET_CATEGORY_PRODUCTS_QUERY = "SELECT * FROM products WHERE category = ?";
    private final static String GET_PRODUCT_BY_ID_QUERY = "SELECT * FROM products WHERE id = ?";

    private final static String GET_PRODUCTS_QUERY = "SELECT * FROM products";
    private final static String ADD_PRODUCT_QUERY = "INSERT INTO products (name, description, imagePath, category, price) VALUES (?, ?, ?, ?, ?)";
    private final static String DELETE_PRODUCT_BY_ID_QUERY = "DELETE FROM products WHERE id = ?";
    private final static String SEARCH_PRODUCTS_QUERY = "SELECT * FROM products WHERE name LIKE ? OR description LIKE ? ORDER BY name ASC";

    @Override
    public List<Product> getCategoryProducts(String category) throws UnableToExecuteQueryException {
        List<Product> result = new ArrayList<>();
        Connection connection = pool.getConnection();
        try (PreparedStatement statement = connection.prepareStatement(GET_CATEGORY_PRODUCTS_QUERY)) {
            statement.setString(1, category);
            ResultSet set = statement.executeQuery();
            while (set.next()) {
                result.add(Product.builder().id(set.getInt("id")).name(set.getString("name")).description(set.getString("description")).
                        description(set.getString("description")).imagePath(set.getString("imagePath")).category(set.getString("category")).
                        price(set.getBigDecimal("price")).build());
            }
            set.close();
            return result;
        } catch (SQLException e) {
            throw new UnableToExecuteQueryException("Не удалось получить товары категории");
        } finally {
            pool.returnConnection(connection);
        }
    }

    @Override
    public Product getProductById(int id) throws UnableToExecuteQueryException {
        Connection connection = pool.getConnection();
        Product product = null;
        try (PreparedStatement statement = connection.prepareStatement(GET_PRODUCT_BY_ID_QUERY)) {
            statement.setInt(1, id);
            ResultSet set = statement.executeQuery();
            while (set.next()) {
                product = Product.builder().id(set.getInt("id")).name(set.getString("name")).description(set.getString("description")).
                        description(set.getString("description")).imagePath(set.getString("imagePath")).category(set.getString("category")).
                        price(set.getBigDecimal("price")).build();
            }
            set.close();
            return product;
        } catch (SQLException e) {
            throw new UnableToExecuteQueryException("Не удалось получить товар по ID");
        } finally {
            pool.returnConnection(connection);
        }
    }

    @Override
    public List<Product> findProducts(String keyWords) throws UnableToExecuteQueryException {
        Connection connection = pool.getConnection();
        List<Product> result = new LinkedList<>();
        try (PreparedStatement searchStatement = connection.prepareStatement(SEARCH_PRODUCTS_QUERY)) {
            keyWords = "%" + keyWords.trim() + "%";
            searchStatement.setString(1, keyWords);
            searchStatement.setString(2, keyWords);
            ResultSet set = searchStatement.executeQuery();
            while (set.next()) {
                result.add(Product.builder().id(set.getInt("id")).name(set.getString("name")).
                        description(set.getString("description")).imagePath(set.getString("imagePath")).
                        category(set.getString("category")).price(set.getBigDecimal("price")).build());
            }
            set.close();
            return result;
        } catch (SQLException e) {
            throw new UnableToExecuteQueryException("Не удалось произвести поиск товаров");
        } finally {
            pool.returnConnection(connection);
        }
    }

    @Override
    public Product create(Product product) throws UnableToExecuteQueryException {
        Connection connection = pool.getConnection();
        try (PreparedStatement statement = connection.prepareStatement(ADD_PRODUCT_QUERY)) {
            statement.setString(1, product.getName());
            statement.setString(2, product.getDescription());
            statement.setString(3, product.getImagePath());
            statement.setString(4, product.getCategory());
            statement.setBigDecimal(5, product.getPrice());
            statement.execute();
            ResultSet autoGenerated = statement.getGeneratedKeys();
            if (autoGenerated.next()) {
                product.setId(autoGenerated.getInt(1));
            }
            autoGenerated.close();
            return product;
        } catch (SQLException e) {
            throw new UnableToExecuteQueryException("Не удалось создать товар");
        } finally {
            pool.returnConnection(connection);
        }
    }

    @Override
    public List<Product> read() throws UnableToExecuteQueryException {
        List<Product> result = new ArrayList<>();
        Connection connection = pool.getConnection();
        try (Statement statement = connection.createStatement()) {
            ResultSet set = statement.executeQuery(GET_PRODUCTS_QUERY);
            while (set.next()) {
                result.add(Product.builder().id(set.getInt("id")).name(set.getString("name")).description(set.getString("description")).
                        description(set.getString("description")).imagePath(set.getString("imagePath")).category(set.getString("category")).
                        price(set.getBigDecimal("price")).build());
            }
            set.close();
            return result;
        } catch (SQLException e) {
            throw new UnableToExecuteQueryException("Не удалось получить товары");
        } finally {
            pool.returnConnection(connection);
        }
    }

    @Override
    public Product update(Product product) throws UnableToExecuteQueryException {
        return getProductById(product.getId());
    }

    @Override
    public void delete(int id) throws UnableToExecuteQueryException {
        Connection connection = pool.getConnection();
        try (PreparedStatement statement = connection.prepareStatement(DELETE_PRODUCT_BY_ID_QUERY)) {
            statement.setString(1, String.valueOf(id));
            statement.execute();
        } catch (SQLException e) {
            throw new UnableToExecuteQueryException("Не удалось удалить товар");
        } finally {
            pool.returnConnection(connection);
        }
    }
}
