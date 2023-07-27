package by.teachmeskills.springbootproject.controllers;

import by.teachmeskills.springbootproject.constants.PagesPaths;
import by.teachmeskills.springbootproject.constants.RequestAttributesNames;
import by.teachmeskills.springbootproject.constants.SessionAttributesNames;
import by.teachmeskills.springbootproject.entities.Cart;
import by.teachmeskills.springbootproject.services.ProductService;
import by.teachmeskills.springbootproject.services.implementation.ProductServiceImpl;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("cart")
@SessionAttributes(SessionAttributesNames.CART)
public class CartController {

    private static final ProductService service = new ProductServiceImpl();

    @GetMapping
    public ModelAndView openCartPage(@ModelAttribute(SessionAttributesNames.CART) Cart cart) {
        ModelAndView modelAndView = new ModelAndView(PagesPaths.CART_PAGE);
        modelAndView.addObject(RequestAttributesNames.CART, cart);
        return new ModelAndView(PagesPaths.CART_PAGE);
    }

    @GetMapping("addProduct/{id}")
    public ModelAndView addProduct(@PathVariable int id, @ModelAttribute(SessionAttributesNames.CART) Cart cart) {
        return service.addProductToCart(id, cart);
    }

    @GetMapping("removeProduct/{id}")
    public ModelAndView removeProduct(@PathVariable int id, @ModelAttribute(SessionAttributesNames.CART) Cart cart) {
        ModelAndView modelAndView = new ModelAndView(PagesPaths.CART_PAGE);
        cart.removeProduct(id);
        modelAndView.addObject(RequestAttributesNames.CART, cart);
        return modelAndView;
    }

    @GetMapping("clear")
    public ModelAndView clearCart(@ModelAttribute(SessionAttributesNames.CART) Cart cart) {
        ModelAndView modelAndView = new ModelAndView(PagesPaths.CART_PAGE);
        cart.clear();
        modelAndView.addObject(RequestAttributesNames.CART, cart);
        return modelAndView;
    }


    @GetMapping("makeOrder")
    public ModelAndView makeOrder() {
        return new ModelAndView(PagesPaths.CART_PAGE);
    }


    @ModelAttribute(SessionAttributesNames.CART)
    public Cart initializeCartInSession() {
        return new Cart();
    }
}
