package by.teachmeskills.springbootproject.exceptions;

import by.teachmeskills.springbootproject.constants.PagesPaths;
import by.teachmeskills.springbootproject.constants.RequestAttributesNames;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice
@Slf4j
public class GlobalControllerExceptionHandler {

    @ExceptionHandler(NoResourceFoundException.class)
    public ResponseEntity<String> handleNoResourceFoundException(NoResourceFoundException e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ModelAndView handleException(Exception e) {
        log.error(e.getMessage());
        ModelAndView modelAndView = new ModelAndView(PagesPaths.ERROR_PAGE);
        modelAndView.addObject("info", e.getMessage());
        return modelAndView;
    }

    @ExceptionHandler(UserAlreadyExistsException.class)
    @ResponseStatus(HttpStatus.OK)
    public ModelAndView handleUserAlreadyExistsException(UserAlreadyExistsException e) {
        ModelAndView modelAndView = new ModelAndView(PagesPaths.REGISTER_PAGE);
        modelAndView.addObject(RequestAttributesNames.STATUS, e.getMessage());
        modelAndView.addObject(RequestAttributesNames.COLOR, "red");
        return modelAndView;
    }

    @ExceptionHandler(AuthorizationException.class)
    @ResponseStatus(HttpStatus.OK)
    public ModelAndView handleAuthorizationException(AuthorizationException e) {
        ModelAndView modelAndView = new ModelAndView(PagesPaths.LOGIN_PAGE);
        modelAndView.addObject(RequestAttributesNames.STATUS, e.getMessage());
        return modelAndView;
    }

    @ExceptionHandler({InsufficientFundsException.class, NoProductsInOrderException.class})
    @ResponseStatus(HttpStatus.OK)
    public ModelAndView handleOrderMakingExceptions(Exception e) {
        ModelAndView modelAndView = new ModelAndView(PagesPaths.CART_PAGE);
        modelAndView.addObject(RequestAttributesNames.STATUS, e.getMessage());
        modelAndView.addObject(RequestAttributesNames.COLOR, "red");
        return modelAndView;
    }
}

