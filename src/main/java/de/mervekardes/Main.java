package de.mervekardes;

import java.math.BigDecimal;
import java.util.Map;

public class Main {

    static void main() {

        ProductRepo productRepo = new ProductRepo();

        Product product1 = new Product(
                1,
                "Laptop",
                new BigDecimal("999.99"),
                10
        );

        Product product2 = new Product(
                2,
                "Mouse",
                new BigDecimal("29.99"),
                10
        );

        Product product3 = new Product(
                3,
                "Keyboard",
                new BigDecimal("79.99"),
                10
        );

        productRepo.addProduct(product1);
        productRepo.addProduct(product2);
        productRepo.addProduct(product3);

        OrderRepo orderRepo = new OrderMapRepo();

        ShopService shopService = new ShopService(
                productRepo,
                orderRepo
        );

        System.out.println("Products before order:");
        for (Product product : shopService.getProducts()) {
            System.out.println(product);
        }

        shopService.addOrder(
                1,
                Map.of(
                        1, 2,
                        2, 3
                )
        );

        System.out.println("\nOrder:");
        System.out.println(shopService.getOrder(1));

        System.out.println("\nProducts after order:");
        for (Product product : shopService.getProducts()) {
            System.out.println(product);
        }

        Order order = shopService.getOrder(1);

        System.out.println("\nTotal price:");
        System.out.println(order.getTotalPrice());

        shopService.changeQuantity(
                1,
                2,
                5
        );

        System.out.println("\nOrder after quantity change:");
        System.out.println(shopService.getOrder(1));

        System.out.println("\nNew total price:");
        System.out.println(order.getTotalPrice());
    }
}