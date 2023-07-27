package by.teachmeskills.springbootproject.services;

import by.teachmeskills.springbootproject.entities.User;
import by.teachmeskills.springbootproject.exceptions.BadConnectionException;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.ModelAndView;

public interface UserService extends BaseService<User>{
    User getUserByEmail(String email) throws BadConnectionException;
    User getUserById(int id) throws BadConnectionException;
    ModelAndView getUser(String email, String password, BindingResult bindingResult, Model model);
    void updateAddressAndPhoneNumber(String address, String phoneNumber, String email) throws BadConnectionException;
    ModelAndView getUserOrders(User user);
    ModelAndView addAddressAndPhoneNumberInfo(String address, String phoneNumber, User userInSession, BindingResult bindingResult);
}
