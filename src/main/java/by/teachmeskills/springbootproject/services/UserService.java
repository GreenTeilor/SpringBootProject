package by.teachmeskills.springbootproject.services;

import by.teachmeskills.springbootproject.entities.Cart;
import by.teachmeskills.springbootproject.entities.PagingParams;
import by.teachmeskills.springbootproject.entities.Statistics;
import by.teachmeskills.springbootproject.entities.User;
import by.teachmeskills.springbootproject.exceptions.AuthorizationException;
import by.teachmeskills.springbootproject.exceptions.InsufficientFundsException;
import by.teachmeskills.springbootproject.exceptions.NoProductsInOrderException;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.util.Optional;

public interface UserService extends BaseService<User>{
    Optional<User> getUserByEmail(String email);
    ModelAndView authenticateUser(String email, String password, BindingResult bindingResult, Model model) throws AuthorizationException;
    ModelAndView getUserInfo(User user, PagingParams params);
    ModelAndView addAddressAndPhoneNumberInfo(String address, String phoneNumber, User userInSession, BindingResult bindingResult, PagingParams params);
    Statistics getUserStatistics(int id);
    ModelAndView makeOrder(User user, Cart cart) throws InsufficientFundsException, NoProductsInOrderException;
    void saveOrdersToFile(int userId, HttpServletResponse response) throws IOException, CsvRequiredFieldEmptyException, CsvDataTypeMismatchException;
    ModelAndView loadOrdersFromFile(User user, MultipartFile file) throws IOException;
}
