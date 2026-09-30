package de.mervekardes;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

class ProductRepoTest {

    @Test
    void addProduct_shouldAddProduct() {

        ProductRepo productRepo = new ProductRepo();

        Product product = new Product(
                1,
                "Laptop",
                new BigDecimal("999.99"),
                10
        );

        productRepo.addProduct(product);

        assertThat(productRepo.getAllProducts())
                .contains(product);
    }

    @Test
    void getProductById_shouldReturnProduct_whenProductExists() {

        ProductRepo productRepo = new ProductRepo();

        Product product = new Product(
                1,
                "Laptop",
                new BigDecimal("999.99"),
                10
        );

        productRepo.addProduct(product);

        Product actual = productRepo.getProductById(1);

        assertThat(actual)
                .isEqualTo(product);
    }

    @Test
    void removeProduct_shouldRemoveProduct_whenProductExists() {

        ProductRepo productRepo = new ProductRepo();

        Product product = new Product(
                1,
                "Laptop",
                new BigDecimal("999.99"),
                10
        );

        productRepo.addProduct(product);

        productRepo.removeProduct(1);

        assertThat(productRepo.getAllProducts())
                .doesNotContain(product);
    }

    @Test
    void getProductById_shouldReturnNull_whenProductDoesNotExist() {

        ProductRepo productRepo = new ProductRepo();

        Product actual = productRepo.getProductById(99);

        assertThat(actual)
                .isNull();
    }
}