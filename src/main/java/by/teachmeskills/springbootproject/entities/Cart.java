package by.teachmeskills.springbootproject.entities;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Cart {
    private List<Product> cart;

    public Cart() {
        cart = new ArrayList<>();
    }

    public void addProduct(Product product) {
        if (!cart.contains(product))
            cart.add(product);
    }

    public void removeProduct(int id) {
        Optional<Product> product = cart.stream().filter(p -> p.getId() == id).findFirst();
        product.ifPresent(p -> cart.remove(p));
    }

    public int size() {
        return cart.size();
    }

    public List<Product> getProducts() {
        return cart;
    }

    public void clear() {
        cart.clear();
    }
}
