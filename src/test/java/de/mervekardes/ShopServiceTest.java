package de.mervekardes;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

class ShopServiceTest {

    @Test
    void addOrder_shouldAddOrderWithCorrectQuantity_whenProductsExist() {

        ProductRepo productRepo = new ProductRepo();

        Product product = new Product(
                1,
                "Laptop",
                new BigDecimal("999.99"),
                10
        );

        productRepo.addProduct(product);

        OrderRepo orderRepo = new OrderListRepo();

        ShopService shopService = new ShopService(
                productRepo,
                orderRepo
        );

        shopService.addOrder(
                1,
                Map.of(1, 2)
        );

        Order order = shopService.getOrder(1);

        assertThat(order).isNotNull();
        assertThat(order.items()).hasSize(1);
        assertThat(order.items().get(0).getProduct()).isEqualTo(product);
        assertThat(order.items().get(0).getQuantity()).isEqualTo(2);
    }
    @Test
    void addOrder_shouldNotAddOrder_whenProductDoesNotExist() {

        ProductRepo productRepo = new ProductRepo();

        Product product = new Product(
                1,
                "Laptop",
                new BigDecimal("999.99"),
                10
        );

        productRepo.addProduct(product);

        OrderRepo orderRepo = new OrderListRepo();

        ShopService shopService = new ShopService(
                productRepo,
                orderRepo
        );

        shopService.addOrder(
                1,
                Map.of(99, 2)
        );

        Order order = shopService.getOrder(1);

        assertThat(order).isNull();
    }
    @Test
    void changeQuantity_shouldChangeQuantity_whenProductExistsInOrder() {

        ProductRepo productRepo = new ProductRepo();

        Product product = new Product(
                1,
                "Laptop",
                new BigDecimal("999.99"),
                10
        );

        productRepo.addProduct(product);

        OrderRepo orderRepo = new OrderListRepo();

        ShopService shopService = new ShopService(
                productRepo,
                orderRepo
        );

        shopService.addOrder(
                1,
                Map.of(1, 2)
        );

        shopService.changeQuantity(
                1,
                1,
                5
        );

        Order order = shopService.getOrder(1);

        assertThat(order.items().get(0).getQuantity())
                .isEqualTo(5);
        assertThat(product.getStock())
                .isEqualTo(5);
    }
    @Test
    void addOrder_shouldReduceStock_whenStockIsAvailable() {

        ProductRepo productRepo = new ProductRepo();

        Product product = new Product(
                1,
                "Laptop",
                new BigDecimal("999.99"),
                10
        );

        productRepo.addProduct(product);

        OrderRepo orderRepo = new OrderListRepo();

        ShopService shopService = new ShopService(
                productRepo,
                orderRepo
        );

        shopService.addOrder(
                1,
                Map.of(1, 2)
        );

        assertThat(product.getStock())
                .isEqualTo(8);
    }
    @Test
    void addOrder_shouldNotAddOrder_whenStockIsNotEnough() {

        ProductRepo productRepo = new ProductRepo();

        Product product = new Product(
                1,
                "Laptop",
                new BigDecimal("999.99"),
                10
        );

        productRepo.addProduct(product);

        OrderRepo orderRepo = new OrderListRepo();

        ShopService shopService = new ShopService(
                productRepo,
                orderRepo
        );

        shopService.addOrder(
                1,
                Map.of(1, 15)
        );

        Order order = shopService.getOrder(1);

        assertThat(order).isNull();
        assertThat(product.getStock()).isEqualTo(10);
    }

    @Test
    void receiveGoods_shouldIncreaseStock_whenProductExists() {

        ProductRepo productRepo = new ProductRepo();

        Product product = new Product(
                1,
                "Laptop",
                new BigDecimal("999.99"),
                10
        );

        productRepo.addProduct(product);

        OrderRepo orderRepo = new OrderListRepo();
        ShopService shopService = new ShopService(productRepo, orderRepo);

        shopService.receiveGoods(1, 5);

        assertThat(product.getStock())
                .isEqualTo(15);
    }

    @Test
    void removeGoods_shouldReduceStock_whenStockIsAvailable() {

        ProductRepo productRepo = new ProductRepo();

        Product product = new Product(
                1,
                "Laptop",
                new BigDecimal("999.99"),
                10
        );

        productRepo.addProduct(product);

        OrderRepo orderRepo = new OrderListRepo();
        ShopService shopService = new ShopService(productRepo, orderRepo);

        shopService.removeGoods(1, 3);

        assertThat(product.getStock())
                .isEqualTo(7);
    }

    @Test
    void removeGoods_shouldNotReduceStock_whenStockIsNotEnough() {

        ProductRepo productRepo = new ProductRepo();

        Product product = new Product(
                1,
                "Laptop",
                new BigDecimal("999.99"),
                10
        );

        productRepo.addProduct(product);

        OrderRepo orderRepo = new OrderListRepo();
        ShopService shopService = new ShopService(productRepo, orderRepo);

        shopService.removeGoods(1, 15);

        assertThat(product.getStock())
                .isEqualTo(10);
    }
}