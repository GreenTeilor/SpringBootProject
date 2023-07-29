package by.teachmeskills.springbootproject.controllers;

import by.teachmeskills.springbootproject.constants.PagesPaths;
import by.teachmeskills.springbootproject.constants.RequestAttributesNames;
import by.teachmeskills.springbootproject.entities.User;
import by.teachmeskills.springbootproject.exceptions.UnableToExecuteQueryException;
import by.teachmeskills.springbootproject.exceptions.UserAlreadyExistsException;
import by.teachmeskills.springbootproject.services.UserService;
import by.teachmeskills.springbootproject.services.implementation.UserServiceImpl;
import by.teachmeskills.springbootproject.utils.ErrorPopulatorUtils;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("register")
public class RegisterController {

    private static final UserService service = new UserServiceImpl();

    @GetMapping
    public ModelAndView openRegisterPage() {
        return new ModelAndView(PagesPaths.REGISTER_PAGE);
    }

    @PostMapping
    public ModelAndView registerUser(@Valid @ModelAttribute(RequestAttributesNames.USER) User user, BindingResult bindingResult) throws UnableToExecuteQueryException, UserAlreadyExistsException {
        if (bindingResult.hasErrors()) {
            ModelAndView modelAndView = new ModelAndView(PagesPaths.REGISTER_PAGE);
            ErrorPopulatorUtils.populateError(RequestAttributesNames.NAME, modelAndView, bindingResult);
            ErrorPopulatorUtils.populateError(RequestAttributesNames.LAST_NAME, modelAndView, bindingResult);
            ErrorPopulatorUtils.populateError(RequestAttributesNames.EMAIL, modelAndView, bindingResult);
            ErrorPopulatorUtils.populateError(RequestAttributesNames.PASSWORD, modelAndView, bindingResult);
            ErrorPopulatorUtils.populateError(RequestAttributesNames.BIRTH_DATE, modelAndView, bindingResult);
            return modelAndView;
        } else {
            return service.create(user);
        }
    }
}
