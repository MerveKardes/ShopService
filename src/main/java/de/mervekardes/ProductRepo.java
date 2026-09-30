package de.mervekardes;

import java.util.ArrayList;
import java.util.List;

public class ProductRepo {
    public List<Product> products = new ArrayList<>();

    public void addProduct(Product product) {
        products.add(product);
    }

    public Product getProductById(int id) {
        for (Product product : products) {
            if (product.getId() == id) {
                return product;
            }
        }

        return null;
    }

    public List<Product> getAllProducts() {
        return products;
    }

    public void removeProduct(int id) {
        Product product = getProductById(id);

        if (product != null) {
            products.remove(product);
        }
    }
}
