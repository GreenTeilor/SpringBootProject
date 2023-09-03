package by.teachmeskills.springbootproject.controllers;

import by.teachmeskills.springbootproject.constants.RequestAttributesNames;
import by.teachmeskills.springbootproject.services.ProductService;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
@Controller
@RequestMapping("/categories")
@RequiredArgsConstructor
public class CategoryController {
    private final ProductService productService;

    @GetMapping("/{name}")
    public ModelAndView openCategory(@PathVariable String name) {
        ModelAndView modelAndView = productService.getCategoryProducts(name);
        modelAndView.addObject(RequestAttributesNames.CATEGORY_NAME, name);
        return modelAndView;
    }

    @PostMapping("/saveProducts")
    public void saveProductsToFile(@RequestParam(RequestAttributesNames.CATEGORY_NAME) String categoryName, HttpServletResponse response) throws IOException, CsvRequiredFieldEmptyException, CsvDataTypeMismatchException {
        productService.saveToFile(categoryName, response);
    }

    @PostMapping ("/loadProducts")
    public ModelAndView loadProductsFromFile(@RequestParam(RequestAttributesNames.CATEGORY_NAME) String categoryName, @RequestParam("file") MultipartFile file) throws IOException {
        return productService.loadFromFile(categoryName, file);
    }
}
