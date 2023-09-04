package by.teachmeskills.springbootproject.controllers;

import by.teachmeskills.springbootproject.constants.RequestAttributesNames;
import by.teachmeskills.springbootproject.constants.SessionAttributesNames;
import by.teachmeskills.springbootproject.entities.PagingParams;
import by.teachmeskills.springbootproject.entities.User;
import by.teachmeskills.springbootproject.services.CategoryService;
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
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;

@Controller
@RequestMapping("/home")
@SessionAttributes(SessionAttributesNames.CATEGORY_PAGING_PARAMS)
@RequiredArgsConstructor
public class HomeController {
    private final CategoryService categoryService;

    @GetMapping
    public ModelAndView openHomePage(@SessionAttribute(SessionAttributesNames.USER) User user,
                                     @ModelAttribute(SessionAttributesNames.CATEGORY_PAGING_PARAMS) PagingParams params) {
        params.setPageNumber(0);
        ModelAndView modelAndView = categoryService.read(params);
        modelAndView.addObject(RequestAttributesNames.USER, user);
        return modelAndView;
    }

    @PostMapping("/csv/exportCategories")
    public void exportCategoriesToCsv(HttpServletResponse response) throws IOException, CsvRequiredFieldEmptyException, CsvDataTypeMismatchException {
        categoryService.saveToFile(response);
    }

    @PostMapping("/csv/importCategories")
    public ModelAndView importCategoriesFromCsv(@RequestParam("file") MultipartFile file) throws IOException {
        return categoryService.loadFromFile(file);
    }

    @GetMapping("/next")
    public ModelAndView findPagedNext(@ModelAttribute(SessionAttributesNames.CATEGORY_PAGING_PARAMS) PagingParams params) {
        params.setPageNumber(params.getPageNumber() + 1);
        return categoryService.read(params);
    }

    @GetMapping("/prev")
    public ModelAndView findPagedPrev(@ModelAttribute(SessionAttributesNames.CATEGORY_PAGING_PARAMS) PagingParams params) {
        params.setPageNumber(params.getPageNumber() - 1);
        return categoryService.read(params);
    }

    @GetMapping("/{pageNumber}")
    public ModelAndView findPagedNumber(@PathVariable int pageNumber,
                                        @ModelAttribute(SessionAttributesNames.CATEGORY_PAGING_PARAMS) PagingParams params) {
        params.setPageNumber(pageNumber);
        return categoryService.read(params);
    }

    @GetMapping("/pageSize/{pageSize}")
    public ModelAndView setPageSize(@PathVariable int pageSize,
                                    @ModelAttribute(SessionAttributesNames.CATEGORY_PAGING_PARAMS) PagingParams params) {
        params.setPageSize(pageSize);
        params.setPageNumber(0);
        return categoryService.read(params);
    }

    @ModelAttribute(SessionAttributesNames.CATEGORY_PAGING_PARAMS)
    public PagingParams initializePagingParams() {
        return new PagingParams(0, 3);
    }
}

