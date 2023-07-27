package by.teachmeskills.springbootproject.controllers;

import by.teachmeskills.springbootproject.constants.SessionAttributesNames;
import by.teachmeskills.springbootproject.entities.User;
import by.teachmeskills.springbootproject.services.UserService;
import by.teachmeskills.springbootproject.services.implementation.UserServiceImpl;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("profile")
public class ProfileController {

    private static final UserService userService = new UserServiceImpl();

    @GetMapping
    public ModelAndView openProfilePage(@SessionAttribute(SessionAttributesNames.USER) User user) {
        return userService.getUserOrders(user);
    }

    @PostMapping
    public ModelAndView addAddressAndPhoneNumberInfo(@ModelAttribute(SessionAttributesNames.USER) User user, @SessionAttribute(SessionAttributesNames.USER) User userInSession) {
        return userService.addAddressAndPhoneNumberInfo(user.getAddress(), user.getPhoneNumber(), userInSession);
    }

}
