package by.teachmeskills.springbootproject.services;

import by.teachmeskills.springbootproject.entities.User;
import by.teachmeskills.springbootproject.exceptions.AuthorizationException;
import by.teachmeskills.springbootproject.exceptions.EntityOperationException;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.ModelAndView;

public interface UserService extends BaseService<User>{
    User getUserByEmail(String email) throws EntityOperationException;
    User getUserById(int id) throws EntityOperationException;
    ModelAndView getUser(String email, String password, BindingResult bindingResult, Model model) throws EntityOperationException, AuthorizationException;
    void updateAddressAndPhoneNumber(String address, String phoneNumber, String email) throws EntityOperationException;
    ModelAndView getUserOrders(User user) throws EntityOperationException;
    ModelAndView addAddressAndPhoneNumberInfo(String address, String phoneNumber, User userInSession, BindingResult bindingResult) throws EntityOperationException;
}
