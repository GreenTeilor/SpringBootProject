package by.teachmeskills.springbootproject.services.implementation;

import by.teachmeskills.springbootproject.constants.PagesPaths;
import by.teachmeskills.springbootproject.constants.RequestAttributesNames;
import by.teachmeskills.springbootproject.entities.Order;
import by.teachmeskills.springbootproject.entities.Product;
import by.teachmeskills.springbootproject.entities.Statistics;
import by.teachmeskills.springbootproject.entities.User;
import by.teachmeskills.springbootproject.exceptions.AuthorizationException;
import by.teachmeskills.springbootproject.exceptions.EntityOperationException;
import by.teachmeskills.springbootproject.exceptions.UserAlreadyExistsException;
import by.teachmeskills.springbootproject.repositories.ProductRepository;
import by.teachmeskills.springbootproject.repositories.UserRepository;
import by.teachmeskills.springbootproject.services.UserService;
import by.teachmeskills.springbootproject.utils.ErrorPopulatorUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.ModelAndView;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final ProductRepository productRepository;

    @Override
    public User getUserByEmail(String email) throws EntityOperationException {
        return userRepository.getUserByEmail(email);
    }

    @Override
    public User getUserById(int id) throws EntityOperationException {
        return userRepository.getUserById(id);
    }

    @Override
    public ModelAndView getUser(String email, String password, BindingResult bindingResult, Model model) throws EntityOperationException, AuthorizationException {
        ModelAndView modelAndView = new ModelAndView(PagesPaths.LOGIN_PAGE);
        if (!bindingResult.hasFieldErrors(RequestAttributesNames.EMAIL) && !bindingResult.hasFieldErrors(RequestAttributesNames.PASSWORD)) {
            User authenticatedUser = userRepository.getUser(email, password);
            if (authenticatedUser != null) {
                model.addAttribute(RequestAttributesNames.USER, authenticatedUser);
                return new ModelAndView("redirect:" + PagesPaths.HOME_PAGE);
            }
            throw new AuthorizationException("Неверный логин или пароль");
        }
        ErrorPopulatorUtils.populateError(RequestAttributesNames.EMAIL, modelAndView, bindingResult);
        ErrorPopulatorUtils.populateError(RequestAttributesNames.PASSWORD, modelAndView, bindingResult);
        return modelAndView;
    }

    @Override
    public void updateAddressAndPhoneNumber(String address, String phoneNumber, String email) throws EntityOperationException {
        userRepository.updateAddressAndPhoneNumber(address, phoneNumber, email);
    }

    public static ModelAndView makeModelAndView(User user, Statistics statistics, List<Order> orders) {
        ModelAndView modelAndView = new ModelAndView(PagesPaths.PROFILE_PAGE);
        modelAndView.addObject(user);
        modelAndView.addObject(statistics);
        modelAndView.addObject(RequestAttributesNames.ORDERS, orders);
        return modelAndView;
    }

    @Override
    public ModelAndView getUserOrders(User user) throws EntityOperationException {
        Statistics statistics = Statistics.builder().id(1).userId(1).daysRegistered(10).orderCount(2).booksCount(5).favoriteGenre("Фантастика").build();
        List<Product> list1 = new ArrayList<>(List.of(productRepository.getProductById(1), productRepository.getProductById(2), productRepository.getProductById(3)));
        List<Product> list2 = new ArrayList<>(List.of(productRepository.getProductById(2), productRepository.getProductById(1)));
        List<Order> orders = new ArrayList<>(List.of(Order.builder().id(1).date(LocalDate.now()).products(list1).userId(2).price(BigDecimal.valueOf(40.0)).build(),
                Order.builder().id(1).date(LocalDate.now()).products(list2).userId(2).price(BigDecimal.valueOf(50.0)).build()));
        return makeModelAndView(user, statistics, orders);
    }

    @Override
    public ModelAndView addAddressAndPhoneNumberInfo(String address, String phoneNumber, User user, BindingResult bindingResult) throws EntityOperationException {
        Statistics statistics = Statistics.builder().id(1).userId(1).daysRegistered(10).orderCount(2).booksCount(5).favoriteGenre("Фантастика").build();
        List<Product> list1 = new ArrayList<>(List.of(productRepository.getProductById(1), productRepository.getProductById(2), productRepository.getProductById(3)));
        List<Product> list2 = new ArrayList<>(List.of(productRepository.getProductById(2), productRepository.getProductById(1)));
        List<Order> orders = new ArrayList<>(List.of(Order.builder().id(1).date(LocalDate.now()).products(list1).userId(2).price(BigDecimal.valueOf(40.0)).build(),
                Order.builder().id(1).date(LocalDate.now()).products(list2).userId(2).price(BigDecimal.valueOf(50.0)).build()));
        if (!bindingResult.hasFieldErrors(RequestAttributesNames.ADDRESS) && !bindingResult.hasFieldErrors(RequestAttributesNames.PHONE_NUMBER)) {
            user.setAddress(address);
            user.setPhoneNumber(phoneNumber);
            userRepository.updateAddressAndPhoneNumber(address, phoneNumber, user.getEmail());
        }
        ModelAndView modelAndView = makeModelAndView(user, statistics, orders);
        ErrorPopulatorUtils.populateError(RequestAttributesNames.ADDRESS, modelAndView, bindingResult);
        ErrorPopulatorUtils.populateError(RequestAttributesNames.PHONE_NUMBER, modelAndView, bindingResult);
        return modelAndView;
    }

    @Override
    public ModelAndView create(User user) throws EntityOperationException, UserAlreadyExistsException {
        ModelAndView modelAndView = new ModelAndView(PagesPaths.REGISTER_PAGE);
        user.setBalance(BigDecimal.valueOf(0.0));
        user.setRegistrationDate(LocalDate.now());
        userRepository.create(user);
        modelAndView.addObject(RequestAttributesNames.STATUS, "Успешно");
        modelAndView.addObject(RequestAttributesNames.COLOR, "green");
        return modelAndView;
    }

    @Override
    public ModelAndView read() throws EntityOperationException {
        ModelAndView modelAndView = new ModelAndView(PagesPaths.HOME_PAGE);
        modelAndView.addObject(RequestAttributesNames.USER, userRepository.read());
        return modelAndView;
    }

    @Override
    public User update(User user) throws EntityOperationException {
        return userRepository.update(user);
    }

    @Override
    public void delete(int id) throws EntityOperationException {
        userRepository.delete(id);
    }
}
