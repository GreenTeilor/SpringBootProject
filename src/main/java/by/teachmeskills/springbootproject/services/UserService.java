package by.teachmeskills.springbootproject.services;

import by.teachmeskills.springbootproject.entities.User;
import by.teachmeskills.springbootproject.exceptions.AuthorizationException;
import by.teachmeskills.springbootproject.exceptions.UnableToExecuteQueryException;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.ModelAndView;

public interface UserService extends BaseService<User>{
    User getUserByEmail(String email) throws UnableToExecuteQueryException;
    User getUserById(int id) throws UnableToExecuteQueryException;
    ModelAndView getUser(String email, String password, BindingResult bindingResult, Model model) throws UnableToExecuteQueryException, AuthorizationException;
    void updateAddressAndPhoneNumber(String address, String phoneNumber, String email) throws UnableToExecuteQueryException;
    ModelAndView getUserOrders(User user) throws UnableToExecuteQueryException;
    ModelAndView addAddressAndPhoneNumberInfo(String address, String phoneNumber, User userInSession, BindingResult bindingResult) throws UnableToExecuteQueryException;
}
