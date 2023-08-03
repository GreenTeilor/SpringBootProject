package by.teachmeskills.springbootproject.services.implementation;

import by.teachmeskills.springbootproject.constants.PagesPaths;
import by.teachmeskills.springbootproject.constants.RequestAttributesNames;
import by.teachmeskills.springbootproject.entities.Cart;
import by.teachmeskills.springbootproject.entities.Product;
import by.teachmeskills.springbootproject.exceptions.EntityOperationException;
import by.teachmeskills.springbootproject.exceptions.UserAlreadyExistsException;
import by.teachmeskills.springbootproject.repositories.CategoryRepository;
import by.teachmeskills.springbootproject.repositories.ProductRepository;
import by.teachmeskills.springbootproject.services.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    @Override
    public ModelAndView getCategoryProducts(String category) throws EntityOperationException {
        ModelAndView modelAndView = new ModelAndView(PagesPaths.CATEGORY_PAGE);
        List<Product> products = productRepository.getCategoryProducts(category);
        modelAndView.addObject(RequestAttributesNames.CATEGORY_PRODUCTS, products);
        return modelAndView;
    }

    @Override
    public ModelAndView getProductById(int id) throws EntityOperationException {
        ModelAndView modelAndView = new ModelAndView(PagesPaths.PRODUCT_PAGE);
        Product product = productRepository.getProductById(id);
        modelAndView.addObject(product.getName());
        modelAndView.addObject(product);
        return modelAndView;
    }

    @Override
    public ModelAndView findProducts(String keyWords) throws EntityOperationException {
        ModelAndView modelAndView = new ModelAndView(PagesPaths.SEARCH_PAGE);
        modelAndView.addObject(RequestAttributesNames.PRODUCTS, productRepository.findProducts(keyWords));
        modelAndView.addObject(RequestAttributesNames.CATEGORIES, categoryRepository.read());
        return modelAndView;
    }

    @Override
    public ModelAndView addProductToCart(int id, Cart cart) throws EntityOperationException {
        ModelAndView modelAndView = new ModelAndView("redirect:/products/" + id);
        Product product = productRepository.getProductById(id);
        cart.addProduct(product);
        return modelAndView;
    }

    @Override
    public ModelAndView create(Product product) throws EntityOperationException, UserAlreadyExistsException {
        ModelAndView modelAndView = new ModelAndView(PagesPaths.PRODUCT_PAGE);
        productRepository.create(product);
        return modelAndView;
    }

    @Override
    public ModelAndView read() throws EntityOperationException {
        ModelAndView modelAndView = new ModelAndView(PagesPaths.SEARCH_PAGE);
        modelAndView.addObject(RequestAttributesNames.PRODUCTS, productRepository.read());
        modelAndView.addObject(RequestAttributesNames.CATEGORIES, categoryRepository.read());
        return modelAndView;
    }

    @Override
    public Product update(Product product) throws EntityOperationException {
        return productRepository.update(product);
    }

    @Override
    public void delete(int id) throws EntityOperationException {
        productRepository.delete(id);
    }
}
