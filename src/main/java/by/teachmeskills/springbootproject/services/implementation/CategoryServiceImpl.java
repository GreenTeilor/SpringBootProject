package by.teachmeskills.springbootproject.services.implementation;

import by.teachmeskills.springbootproject.constants.PagesPaths;
import by.teachmeskills.springbootproject.constants.RequestAttributesNames;
import by.teachmeskills.springbootproject.entities.Category;
import by.teachmeskills.springbootproject.exceptions.EntityOperationException;
import by.teachmeskills.springbootproject.repositories.implementation.CategoryRepositoryImpl;
import by.teachmeskills.springbootproject.services.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepositoryImpl categoryRepository;

    @Override
    public ModelAndView create(Category category) throws EntityOperationException {
        categoryRepository.create(category);
        return new ModelAndView(PagesPaths.CATEGORY_PAGE);
    }

    @Override
    public ModelAndView read() throws EntityOperationException {
        ModelAndView modelAndView = new ModelAndView(PagesPaths.HOME_PAGE);
        modelAndView.addObject(RequestAttributesNames.CATEGORIES, categoryRepository.read());
        return modelAndView;
    }

    @Override
    public Category update(Category category) throws EntityOperationException {
        return categoryRepository.update(category);
    }

    @Override
    public void delete(int id) throws EntityOperationException {
        categoryRepository.delete(id);
    }
}
