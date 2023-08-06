package by.teachmeskills.springbootproject.controllers;

import by.teachmeskills.springbootproject.services.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("search")
@RequiredArgsConstructor
public class SearchController {
    private final ProductService productService;

    @GetMapping
    public ModelAndView openSearchPage() {
        return productService.read();
    }

    @PostMapping
    public ModelAndView search(String searchCriteria) {
        return productService.findProducts(searchCriteria);
    }
}
