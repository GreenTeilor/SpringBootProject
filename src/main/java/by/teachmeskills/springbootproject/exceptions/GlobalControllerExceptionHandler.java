package by.teachmeskills.springbootproject.exceptions;

import by.teachmeskills.springbootproject.constants.PagesPaths;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice
public class GlobalControllerExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(GlobalControllerExceptionHandler.class);
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ModelAndView handleException(Exception e) {
        logger.error(e.getMessage());
        ModelAndView modelAndView = new ModelAndView(PagesPaths.ERROR_PAGE);
        modelAndView.addObject("info", e.getMessage());
        return modelAndView;
    }

}

