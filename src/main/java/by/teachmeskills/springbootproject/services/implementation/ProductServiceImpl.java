package by.teachmeskills.springbootproject.services.implementation;

import by.teachmeskills.springbootproject.constants.PagesPaths;
import by.teachmeskills.springbootproject.constants.RequestAttributesNames;
import by.teachmeskills.springbootproject.entities.Cart;
import by.teachmeskills.springbootproject.entities.Product;
import by.teachmeskills.springbootproject.exceptions.BadConnectionException;
import by.teachmeskills.springbootproject.exceptions.UserAlreadyExistsException;
import by.teachmeskills.springbootproject.repositories.CategoryRepository;
import by.teachmeskills.springbootproject.repositories.ProductRepository;
import by.teachmeskills.springbootproject.repositories.implementation.CategoryRepositoryImpl;
import by.teachmeskills.springbootproject.repositories.implementation.ProductRepositoryImpl;
import by.teachmeskills.springbootproject.services.ProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository = new ProductRepositoryImpl();
    private final CategoryRepository categoryRepository = new CategoryRepositoryImpl();
    private static final Logger logger = LoggerFactory.getLogger(ProductServiceImpl.class);

    @Override
    public ModelAndView getCategoryProducts(String category) {
        try {
            ModelAndView modelAndView = new ModelAndView(PagesPaths.CATEGORY_PAGE);
            List<Product> products = productRepository.getCategoryProducts(category);
            modelAndView.addObject(RequestAttributesNames.CATEGORY_PRODUCTS, products);
            return modelAndView;
        } catch (BadConnectionException e) {
            logger.error(e.getMessage());
        }
        return new ModelAndView(PagesPaths.ERROR_PAGE);
    }

    @Override
    public ModelAndView getProductById(int id) {
        try {
            ModelAndView modelAndView = new ModelAndView(PagesPaths.PRODUCT_PAGE);
            Product product = productRepository.getProductById(id);
            modelAndView.addObject(product.getName());
            modelAndView.addObject(product);
            return modelAndView;
        } catch (BadConnectionException e) {
            logger.error(e.getMessage());
        }
        return new ModelAndView(PagesPaths.ERROR_PAGE);
    }

    @Override
    public ModelAndView findProducts(String keyWords) {
        try {
            ModelAndView modelAndView = new ModelAndView(PagesPaths.SEARCH_PAGE);
            modelAndView.addObject(RequestAttributesNames.PRODUCTS, productRepository.findProducts(keyWords));
            modelAndView.addObject(RequestAttributesNames.CATEGORIES, categoryRepository.read());
            return modelAndView;
        } catch (BadConnectionException e) {
            logger.error(e.getMessage());
        }
        return new ModelAndView(PagesPaths.ERROR_PAGE);
    }

    @Override
    public ModelAndView addProductToCart(int id, Cart cart) {
        try {
            ModelAndView modelAndView = new ModelAndView("redirect:/products/" + id);
            Product product = productRepository.getProductById(id);
            cart.addProduct(product);
            return modelAndView;
        } catch (BadConnectionException e) {
            logger.error(e.getMessage());
        }
        return new ModelAndView(PagesPaths.ERROR_PAGE);
    }

    @Override
    public ModelAndView processCartOperation(Cart cart, String actionType, Integer productId) {
        ModelAndView modelAndView = new ModelAndView(PagesPaths.CART_PAGE);
        switch (actionType) {
            case "addProduct" -> {
                return this.addProductToCart(productId, cart);
            }
            case "removeProduct" -> cart.removeProduct(productId);
            case "clearCart" -> cart.clear();
            case "makeOrder" -> {
            }
            default -> throw new RuntimeException("Unknown parameter value");
        }
        modelAndView.addObject(RequestAttributesNames.PRODUCTS, cart.getProducts());
        return modelAndView;
    }

    @Override
    public ModelAndView create(Product product) {
        try {
            ModelAndView modelAndView = new ModelAndView(PagesPaths.PRODUCT_PAGE);
            productRepository.create(product);
            return modelAndView;
        } catch (BadConnectionException | UserAlreadyExistsException e) {
            logger.error(e.getMessage());
        }
        return new ModelAndView(PagesPaths.ERROR_PAGE);
    }

    @Override
    public ModelAndView read() {
        try {
            ModelAndView modelAndView = new ModelAndView(PagesPaths.SEARCH_PAGE);
            modelAndView.addObject(RequestAttributesNames.PRODUCTS, productRepository.read());
            modelAndView.addObject(RequestAttributesNames.CATEGORIES, categoryRepository.read());
            return modelAndView;
        } catch (BadConnectionException e) {
            logger.error(e.getMessage());
        }
        return new ModelAndView(PagesPaths.ERROR_PAGE);
    }

    @Override
    public Product update(Product product) throws BadConnectionException {
        return productRepository.update(product);
    }

    @Override
    public void delete(int id) throws BadConnectionException {
        productRepository.delete(id);
    }
}
