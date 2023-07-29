package by.teachmeskills.springbootproject.repositories;

import by.teachmeskills.springbootproject.entities.Product;
import by.teachmeskills.springbootproject.exceptions.UnableToExecuteQueryException;

import java.util.List;

public interface ProductRepository extends BaseRepository<Product> {
    List<Product> getCategoryProducts(String category) throws UnableToExecuteQueryException;

    Product getProductById(int id) throws UnableToExecuteQueryException;

    List<Product> findProducts(String keyWords) throws UnableToExecuteQueryException;
}
