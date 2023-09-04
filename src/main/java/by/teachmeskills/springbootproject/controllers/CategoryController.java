package by.teachmeskills.springbootproject.controllers;

import by.teachmeskills.springbootproject.constants.RequestAttributesNames;
import by.teachmeskills.springbootproject.constants.SessionAttributesNames;
import by.teachmeskills.springbootproject.entities.PagingParams;
import by.teachmeskills.springbootproject.services.ProductService;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
@Controller
@RequestMapping("/categories")
@SessionAttributes(SessionAttributesNames.PRODUCT_PAGING_PARAMS)
@RequiredArgsConstructor
public class CategoryController {
    private final ProductService productService;

    @GetMapping("/{name}")
    public ModelAndView openCategory(@PathVariable String name,
                                     @ModelAttribute(SessionAttributesNames.PRODUCT_PAGING_PARAMS) PagingParams params) {
        params.setPageNumber(0);
        ModelAndView modelAndView = productService.getCategoryProducts(name, params);
        modelAndView.addObject(RequestAttributesNames.CATEGORY_NAME, name);
        return modelAndView;
    }

    @PostMapping("/saveProducts")
    public void saveProductsToFile(@RequestParam(RequestAttributesNames.CATEGORY_NAME) String categoryName,
                                   HttpServletResponse response) throws IOException, CsvRequiredFieldEmptyException, CsvDataTypeMismatchException {
        productService.saveToFile(categoryName, response);
    }

    @PostMapping ("/loadProducts")
    public ModelAndView loadProductsFromFile(@RequestParam(RequestAttributesNames.CATEGORY_NAME) String categoryName,
                                             @RequestParam("file") MultipartFile file) throws IOException {
        return productService.loadFromFile(categoryName, file);
    }

    @GetMapping("/{name}/next")
    public ModelAndView findPagedNext(@PathVariable String name,
                                      @ModelAttribute(SessionAttributesNames.PRODUCT_PAGING_PARAMS) PagingParams params) {
        params.setPageNumber(params.getPageNumber() + 1);
        return productService.getCategoryProducts(name, params);
    }

    @GetMapping("/{name}/prev")
    public ModelAndView findPagedPrev(@PathVariable String name,
                                      @ModelAttribute(SessionAttributesNames.PRODUCT_PAGING_PARAMS) PagingParams params) {
        params.setPageNumber(params.getPageNumber() - 1);
        return productService.getCategoryProducts(name, params);
    }

    @GetMapping("/{name}/{pageNumber}")
    public ModelAndView findPagedNumber(@PathVariable String name,
                                        @PathVariable int pageNumber,
                                        @ModelAttribute(SessionAttributesNames.PRODUCT_PAGING_PARAMS) PagingParams params) {
        params.setPageNumber(pageNumber);
        return productService.getCategoryProducts(name, params);
    }

    @GetMapping("/{name}/pageSize/{pageSize}")
    public ModelAndView setPageSize(@PathVariable String name,
                                    @PathVariable int pageSize,
                                    @ModelAttribute(SessionAttributesNames.PRODUCT_PAGING_PARAMS) PagingParams params) {
        params.setPageSize(pageSize);
        params.setPageNumber(0);
        return productService.getCategoryProducts(name, params);
    }

    @ModelAttribute(SessionAttributesNames.PRODUCT_PAGING_PARAMS)
    public PagingParams initializePagingParams() {
        return new PagingParams(0, 3);
    }
}
