package by.teachmeskills.springbootproject.services.implementation;

import by.teachmeskills.springbootproject.constants.PagesPaths;
import by.teachmeskills.springbootproject.constants.RequestAttributesNames;
import by.teachmeskills.springbootproject.entities.Category;
import by.teachmeskills.springbootproject.exceptions.BadConnectionException;
import by.teachmeskills.springbootproject.repositories.implementation.CategoryRepositoryImpl;
import by.teachmeskills.springbootproject.services.CategoryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

@Service
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepositoryImpl categoryRepository = new CategoryRepositoryImpl();
    private static final Logger logger = LoggerFactory.getLogger(CategoryServiceImpl.class);

    @Override
    public ModelAndView create(Category category) {
        try {
            categoryRepository.create(category);
            return new ModelAndView(PagesPaths.CATEGORY_PAGE);
        } catch (BadConnectionException e) {
            logger.error(e.getMessage());
        }
        return new ModelAndView(PagesPaths.ERROR_PAGE);
    }

    @Override
    public ModelAndView read() {
        try {
            ModelAndView modelAndView = new ModelAndView(PagesPaths.HOME_PAGE);
            modelAndView.addObject(RequestAttributesNames.CATEGORIES, categoryRepository.read());
            return modelAndView;
        } catch (BadConnectionException e) {
            logger.error(e.getMessage());
        }
        return new ModelAndView(PagesPaths.ERROR_PAGE);
    }

    @Override
    public Category update(Category category) throws BadConnectionException {
        return categoryRepository.update(category);
    }

    @Override
    public void delete(int id) throws BadConnectionException {
        categoryRepository.delete(id);
    }
}
