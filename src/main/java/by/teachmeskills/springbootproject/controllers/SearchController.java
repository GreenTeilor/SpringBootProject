package by.teachmeskills.springbootproject.controllers;

import by.teachmeskills.springbootproject.constants.SessionAttributesNames;
import by.teachmeskills.springbootproject.entities.SearchCriteria;
import by.teachmeskills.springbootproject.services.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/search")
@SessionAttributes(SessionAttributesNames.SEARCH_CRITERIA)
@RequiredArgsConstructor
public class SearchController {
    private final ProductService productService;

    @GetMapping
    public ModelAndView openSearchPage(@ModelAttribute(SessionAttributesNames.SEARCH_CRITERIA) SearchCriteria searchCriteria) {
        searchCriteria.setKeyWords("");
        searchCriteria.setPageNumber(0);
        searchCriteria.setPageSize(3);
        return productService.findProducts(searchCriteria);
    }

    @PostMapping
    public ModelAndView search(@RequestParam String keyWords, @ModelAttribute(SessionAttributesNames.SEARCH_CRITERIA) SearchCriteria searchCriteria) {
        searchCriteria.setKeyWords(keyWords);
        searchCriteria.setPageNumber(0);
        return productService.findProducts(searchCriteria);
    }

    @GetMapping("/next")
    public ModelAndView findPagedNext(@ModelAttribute(SessionAttributesNames.SEARCH_CRITERIA) SearchCriteria searchCriteria) {
        searchCriteria.setPageNumber(searchCriteria.getPageNumber() + 1);
        return productService.findProducts(searchCriteria);
    }

    @GetMapping("/prev")
    public ModelAndView findPagedPrev(@ModelAttribute(SessionAttributesNames.SEARCH_CRITERIA) SearchCriteria searchCriteria) {
        searchCriteria.setPageNumber(searchCriteria.getPageNumber() - 1);
        return productService.findProducts(searchCriteria);
    }

    @GetMapping("/{pageNumber}")
    public ModelAndView findPagedNumber(@PathVariable int pageNumber, @ModelAttribute(SessionAttributesNames.SEARCH_CRITERIA) SearchCriteria searchCriteria) {
        searchCriteria.setPageNumber(pageNumber);
        return productService.findProducts(searchCriteria);
    }

    @GetMapping("/pageSize/{pageSize}")
    public ModelAndView setPageSize(@PathVariable int pageSize,
                                    @ModelAttribute(SessionAttributesNames.SEARCH_CRITERIA) SearchCriteria searchCriteria) {
        searchCriteria.setPageSize(pageSize);
        searchCriteria.setPageNumber(0);
        return productService.findProducts(searchCriteria);
    }

    @PostMapping("setFilter")
    public ModelAndView setFilter(@ModelAttribute(SessionAttributesNames.SEARCH_CRITERIA) SearchCriteria searchCriteria,
                                  @RequestParam(required = false) String category,
                                  @RequestParam(required = false) Integer priceFrom,
                                  @RequestParam(required = false) Integer priceTo) {
        return productService.changeFilter(searchCriteria, category, priceFrom, priceTo);
    }

    @GetMapping("resetFilter")
    public ModelAndView resetFilter(@ModelAttribute(SessionAttributesNames.SEARCH_CRITERIA) SearchCriteria searchCriteria) {
        return productService.changeFilter(searchCriteria, null, null, null);
    }

    @ModelAttribute(SessionAttributesNames.SEARCH_CRITERIA)
    public SearchCriteria initializeSearchCriteria() {
        return new SearchCriteria();
    }
}
