package de.mervekardes;

import java.math.BigDecimal;
import java.util.Map;
import java.util.UUID;

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
        IdService idService = new IdService() {
            @Override
            public String generateId() {
                return UUID.randomUUID().toString();
            }
        };

        ShopService shopService = new ShopService(
                productRepo,
                orderRepo,
                idService
        );

        System.out.println("Products before order:");
        for (Product product : shopService.getProducts()) {
            System.out.println(product);
        }

        String orderId1 = shopService.addOrder(
                Map.of(
                        1, 2,
                        2, 3
                )
        );

        String orderId2 = shopService.addOrder(
                Map.of(
                        2, 1,
                        3, 2
                )
        );

        String orderId3 = shopService.addOrder(
                Map.of(
                        1, 1,
                        3, 1
                )
        );

        Order order = shopService.getOrder(orderId1);

        System.out.println("\nOrder:");
        System.out.println(order);

        System.out.println("\nProducts after order:");
        for (Product product : shopService.getProducts()) {
            System.out.println(product);
        }

        System.out.println("\nTotal price:");
        System.out.println(order.getTotalPrice());

        shopService.changeQuantity(
                orderId1,
                2,
                5
        );

        System.out.println("\nOrder after quantity change:");
        System.out.println(order);

        System.out.println("\nNew total price:");
        System.out.println(order.getTotalPrice());

        System.out.println("\nStock after quantity change:");
        System.out.println(product2);

        // Wareneingang
        shopService.receiveGoods(1, 5);

        System.out.println("\nStock after receiving 5 laptops:");
        System.out.println(product1);

        // Warenausgang
        shopService.removeGoods(1, 3);

        System.out.println("\nStock after removing 3 laptops:");
        System.out.println(product1);
    }
}