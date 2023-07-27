package by.teachmeskills.springbootproject.controllers;

import by.teachmeskills.springbootproject.services.ProductService;
import by.teachmeskills.springbootproject.services.implementation.ProductServiceImpl;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("search")
public class SearchController {
    private static final ProductService productService = new ProductServiceImpl();

    @GetMapping
    public ModelAndView openSearchPage() {
        return productService.read();
    }

    @PostMapping
    public ModelAndView search(String searchCriteria) {
        return productService.findProducts(searchCriteria);
    }
}
