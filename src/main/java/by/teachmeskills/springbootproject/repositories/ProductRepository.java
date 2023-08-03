package by.teachmeskills.springbootproject.repositories;

import by.teachmeskills.springbootproject.entities.Product;
import by.teachmeskills.springbootproject.exceptions.EntityOperationException;

import java.util.List;

public interface ProductRepository extends BaseRepository<Product> {
    List<Product> getCategoryProducts(String category) throws EntityOperationException;

    Product getProductById(int id) throws EntityOperationException;

    List<Product> findProducts(String keyWords) throws EntityOperationException;
}
