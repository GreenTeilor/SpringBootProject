package by.teachmeskills.springbootproject.controllers;

import by.teachmeskills.springbootproject.constants.PagesPaths;
import by.teachmeskills.springbootproject.constants.RequestAttributesNames;
import by.teachmeskills.springbootproject.entities.User;
import by.teachmeskills.springbootproject.services.UserService;
import by.teachmeskills.springbootproject.services.implementation.UserServiceImpl;
import org.springframework.stereotype.Controller;
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
    public ModelAndView registerUser(@ModelAttribute(RequestAttributesNames.USER) User user) {
        return service.create(user);
    }
}
