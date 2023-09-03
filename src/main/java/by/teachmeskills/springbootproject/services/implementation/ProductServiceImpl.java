package by.teachmeskills.springbootproject.services.implementation;

import by.teachmeskills.springbootproject.constants.PagesPaths;
import by.teachmeskills.springbootproject.constants.RequestAttributesNames;
import by.teachmeskills.springbootproject.csv.ProductCsv;
import by.teachmeskills.springbootproject.csv.converters.ProductConverter;
import by.teachmeskills.springbootproject.entities.Cart;
import by.teachmeskills.springbootproject.entities.Product;
import by.teachmeskills.springbootproject.entities.SearchCriteria;
import by.teachmeskills.springbootproject.exceptions.UserAlreadyExistsException;
import by.teachmeskills.springbootproject.repositories.CategoryRepository;
import by.teachmeskills.springbootproject.repositories.implementation.ProductRepositoryImpl;
import by.teachmeskills.springbootproject.services.ProductService;
import com.opencsv.CSVWriter;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ProductServiceImpl implements ProductService {
    private final ProductRepositoryImpl productRepository;
    private final CategoryRepository categoryRepository;
    private final ProductConverter productConverter;

    @Override
    public ModelAndView getCategoryProducts(String category) {
        ModelAndView modelAndView = new ModelAndView(PagesPaths.CATEGORY_PAGE);
        List<Product> products = productRepository.getCategoryProducts(category);
        modelAndView.addObject(RequestAttributesNames.CATEGORY_PRODUCTS, products);
        return modelAndView;
    }

    @Override
    public ModelAndView getProductById(int id) {
        ModelAndView modelAndView = new ModelAndView(PagesPaths.PRODUCT_PAGE);
        Product product = productRepository.getProductById(id);
        modelAndView.addObject(product.getName());
        modelAndView.addObject(product);
        return modelAndView;
    }

    @Override
    public ModelAndView findProducts(SearchCriteria searchCriteria) {
        ModelAndView modelAndView = new ModelAndView(PagesPaths.SEARCH_PAGE);
        if (searchCriteria.getPaginationNumber() < 1) {
            searchCriteria.setPaginationNumber(1);
        }
        modelAndView.addObject(RequestAttributesNames.PRODUCTS, productRepository.findProducts(searchCriteria.getKeyWords(), searchCriteria.getPaginationNumber()));
        modelAndView.addObject(RequestAttributesNames.CATEGORIES, categoryRepository.read());
        return modelAndView;
    }

    @Override
    public ModelAndView addProductToCart(int id, Cart cart) {
        ModelAndView modelAndView = new ModelAndView("redirect:/products/" + id);
        Product product = productRepository.getProductById(id);
        cart.addProduct(product);
        return modelAndView;
    }

    @Override
    public void saveToFile(String categoryName, HttpServletResponse response) throws IOException, CsvRequiredFieldEmptyException, CsvDataTypeMismatchException {
        try (Writer writer = new OutputStreamWriter(response.getOutputStream())) {
            StatefulBeanToCsv<ProductCsv> beanToCsv = new StatefulBeanToCsvBuilder<ProductCsv>(writer)
                    .withQuotechar(CSVWriter.NO_QUOTE_CHARACTER)
                    .withSeparator('~')
                    .build();
            response.setContentType("text/csv");
            response.setHeader("Content-Disposition", "attachment; filename=" + "products.csv");
            List<Product> products = productRepository.getCategoryProducts(categoryName);
            products.forEach(p -> p.setId(null));
            beanToCsv.write(products.stream().map(productConverter::toCsv).toList());
        }
    }

    @Override
    @Transactional
    public ModelAndView loadFromFile(String categoryName, MultipartFile file) throws IOException {
        ModelAndView modelAndView = new ModelAndView("redirect:/categories/" + categoryName);
        try (Reader reader = new BufferedReader(new InputStreamReader(file.getInputStream()))) {
            CsvToBean<ProductCsv> csvToBean = new CsvToBeanBuilder<ProductCsv>(reader)
                    .withType(ProductCsv.class)
                    .withIgnoreLeadingWhiteSpace(true)
                    .withSeparator('~')
                    .build();
            List<ProductCsv> products = new ArrayList<>();
            csvToBean.forEach(products::add);
            products.stream().map(productConverter::fromCsv).forEach(productRepository::create);
            return modelAndView;
        }
    }

    @Override
    @Transactional
    public ModelAndView create(Product product) {
        ModelAndView modelAndView = new ModelAndView(PagesPaths.PRODUCT_PAGE);
        productRepository.create(product);
        return modelAndView;
    }

    @Override
    public ModelAndView read() {
        ModelAndView modelAndView = new ModelAndView(PagesPaths.SEARCH_PAGE);
        modelAndView.addObject(RequestAttributesNames.PRODUCTS, productRepository.read());
        modelAndView.addObject(RequestAttributesNames.CATEGORIES, categoryRepository.read());
        return modelAndView;
    }

    @Override
    @Transactional
    public Product update(Product product) {
        return productRepository.update(product);
    }

    @Override
    @Transactional
    public void delete(int id) {
        productRepository.delete(id);
    }
}
