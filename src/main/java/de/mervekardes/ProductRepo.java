package de.mervekardes;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ProductRepo {
    private List<Product> products = new ArrayList<>();

    public void addProduct(Product product) {
        products.add(product);
    }

    public Optional<Product> getProductById(int id) {
        for (Product product : products) {
            if (product.getId() == id) {
                return Optional.of(product);
            }
        }

        return Optional.empty();
    }

    public List<Product> getAllProducts() {
        return products;
    }

    public void removeProduct(int id) {
        getProductById(id)
                .ifPresent(products::remove);
    }
}
