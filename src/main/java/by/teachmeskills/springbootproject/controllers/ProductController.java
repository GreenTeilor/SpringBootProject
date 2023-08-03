package by.teachmeskills.springbootproject.controllers;

import by.teachmeskills.springbootproject.exceptions.EntityOperationException;
import by.teachmeskills.springbootproject.services.ProductService;
import by.teachmeskills.springbootproject.services.implementation.ProductServiceImpl;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("products")
public class ProductController {
    private static final ProductService service = new ProductServiceImpl();

    @GetMapping("{id}")
    public ModelAndView openProductPage(@PathVariable int id) throws EntityOperationException {
        return service.getProductById(id);
    }

}
