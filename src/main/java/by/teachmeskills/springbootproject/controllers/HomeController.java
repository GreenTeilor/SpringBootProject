package by.teachmeskills.springbootproject.controllers;

import by.teachmeskills.springbootproject.constants.RequestAttributesNames;
import by.teachmeskills.springbootproject.entities.User;
import by.teachmeskills.springbootproject.exceptions.EntityOperationException;
import by.teachmeskills.springbootproject.services.CategoryService;
import by.teachmeskills.springbootproject.services.implementation.CategoryServiceImpl;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("home")
public class HomeController {
    private static final CategoryService categoryService = new CategoryServiceImpl();

    @GetMapping
    public ModelAndView openHomePage(@SessionAttribute(RequestAttributesNames.USER) User user) throws EntityOperationException {
        ModelAndView modelAndView = categoryService.read();
        modelAndView.addObject(RequestAttributesNames.NAME, user.getName());
        modelAndView.addObject(RequestAttributesNames.LAST_NAME, user.getLastName());
        modelAndView.addObject(RequestAttributesNames.BALANCE, user.getBalance());
        return modelAndView;
    }
}

