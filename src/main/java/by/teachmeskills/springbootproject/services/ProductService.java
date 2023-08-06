package by.teachmeskills.springbootproject.services;

import by.teachmeskills.springbootproject.entities.Cart;
import by.teachmeskills.springbootproject.entities.Product;
import org.springframework.web.servlet.ModelAndView;

public interface ProductService extends BaseService<Product>{
    ModelAndView getCategoryProducts(String category);
    ModelAndView getProductById(int id);
    ModelAndView findProducts(String keyWords);
    ModelAndView addProductToCart(int id, Cart cart);
}
