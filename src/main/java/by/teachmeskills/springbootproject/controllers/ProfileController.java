package by.teachmeskills.springbootproject.controllers;

import by.teachmeskills.springbootproject.constants.SessionAttributesNames;
import by.teachmeskills.springbootproject.entities.PagingParams;
import by.teachmeskills.springbootproject.entities.User;
import by.teachmeskills.springbootproject.services.UserService;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;

@Controller
@RequestMapping("/profile")
@SessionAttributes(SessionAttributesNames.ORDER_PAGING_PARAMS)
@RequiredArgsConstructor
public class ProfileController {

    private final UserService userService;

    @GetMapping
    public ModelAndView openProfilePage(@SessionAttribute(SessionAttributesNames.USER) User user,
                                        @ModelAttribute(SessionAttributesNames.ORDER_PAGING_PARAMS) PagingParams params) {
        params.setPageNumber(0);
        return userService.getUserInfo(user, params);
    }

    @PostMapping
    public ModelAndView addAddressAndPhoneNumberInfo(@Valid @ModelAttribute(SessionAttributesNames.USER) User user, BindingResult bindingResult,
                                                     @SessionAttribute(SessionAttributesNames.USER) User userInSession,
                                                     @ModelAttribute(SessionAttributesNames.ORDER_PAGING_PARAMS) PagingParams params) {
        return userService.addAddressAndPhoneNumberInfo(user.getAddress(), user.getPhoneNumber(), userInSession, bindingResult, params);
    }

    @PostMapping("/saveOrders")
    public void saveOrdersToFile(@SessionAttribute(SessionAttributesNames.USER) User user,
                                 HttpServletResponse response) throws CsvRequiredFieldEmptyException, CsvDataTypeMismatchException, IOException {
        userService.saveOrdersToFile(user.getId(), response);
    }

    @PostMapping("/loadOrders")
    public ModelAndView loadOrdersFromFile(@SessionAttribute(SessionAttributesNames.USER) User user,
                                           @RequestParam("file") MultipartFile file)
            throws IOException {
        return userService.loadOrdersFromFile(user, file);
    }

    @GetMapping("/next")
    public ModelAndView findPagedNext(@SessionAttribute(SessionAttributesNames.USER) User user,
                                      @ModelAttribute(SessionAttributesNames.ORDER_PAGING_PARAMS) PagingParams params) {
        params.setPageNumber(params.getPageNumber() + 1);
        return userService.getUserInfo(user, params);
    }

    @GetMapping("/prev")
    public ModelAndView findPagedPrev(@SessionAttribute(SessionAttributesNames.USER) User user,
                                      @ModelAttribute(SessionAttributesNames.ORDER_PAGING_PARAMS) PagingParams params) {
        params.setPageNumber(params.getPageNumber() - 1);
        return userService.getUserInfo(user, params);
    }

    @GetMapping("/{pageNumber}")
    public ModelAndView findPagedNumber(@PathVariable int pageNumber,
                                        @SessionAttribute(SessionAttributesNames.USER) User user,
                                        @ModelAttribute(SessionAttributesNames.ORDER_PAGING_PARAMS) PagingParams params) {
        params.setPageNumber(pageNumber);
        return userService.getUserInfo(user, params);
    }

    @GetMapping("/pageSize/{pageSize}")
    public ModelAndView setPageSize(@PathVariable int pageSize,
                                    @SessionAttribute(SessionAttributesNames.USER) User user,
                                    @ModelAttribute(SessionAttributesNames.ORDER_PAGING_PARAMS) PagingParams params) {
        params.setPageSize(pageSize);
        params.setPageNumber(0);
        return userService.getUserInfo(user, params);
    }

    @ModelAttribute(SessionAttributesNames.ORDER_PAGING_PARAMS)
    public PagingParams initializePagingParams() {
        return new PagingParams(0, 3);
    }

}
