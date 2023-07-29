package by.teachmeskills.springbootproject.services;

import by.teachmeskills.springbootproject.entities.Cart;
import by.teachmeskills.springbootproject.entities.Product;
import by.teachmeskills.springbootproject.exceptions.UnableToExecuteQueryException;
import org.springframework.web.servlet.ModelAndView;

public interface ProductService extends BaseService<Product>{
    ModelAndView getCategoryProducts(String category) throws UnableToExecuteQueryException;
    ModelAndView getProductById(int id) throws UnableToExecuteQueryException;
    ModelAndView findProducts(String keyWords) throws UnableToExecuteQueryException;
    ModelAndView addProductToCart(int id, Cart cart) throws UnableToExecuteQueryException;
}
