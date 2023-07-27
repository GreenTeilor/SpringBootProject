package by.teachmeskills.springbootproject.repositories;

import by.teachmeskills.springbootproject.entities.Product;
import by.teachmeskills.springbootproject.exceptions.BadConnectionException;

import java.util.List;

public interface ProductRepository extends BaseRepository<Product> {
    List<Product> getCategoryProducts(String category) throws BadConnectionException;

    Product getProductById(int id) throws BadConnectionException;

    List<Product> findProducts(String keyWords) throws BadConnectionException;
}
