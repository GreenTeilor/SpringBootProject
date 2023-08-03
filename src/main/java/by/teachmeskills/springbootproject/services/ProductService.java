package by.teachmeskills.springbootproject.services;

import by.teachmeskills.springbootproject.entities.Cart;
import by.teachmeskills.springbootproject.entities.Product;
import by.teachmeskills.springbootproject.exceptions.EntityOperationException;
import org.springframework.web.servlet.ModelAndView;

public interface ProductService extends BaseService<Product>{
    ModelAndView getCategoryProducts(String category) throws EntityOperationException;
    ModelAndView getProductById(int id) throws EntityOperationException;
    ModelAndView findProducts(String keyWords) throws EntityOperationException;
    ModelAndView addProductToCart(int id, Cart cart) throws EntityOperationException;
}
