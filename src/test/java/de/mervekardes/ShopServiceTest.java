package de.mervekardes;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

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

        IdService idService = () -> "order-1";

        ShopService shopService = new ShopService(
                productRepo,
                orderRepo,
                idService
        );

        String orderId = shopService.addOrder(
                Map.of(1, 2)
        );

        Order order = shopService.getOrder(orderId);

        assertThat(order).isNotNull();
        assertThat(order.id()).isEqualTo("order-1");
        assertThat(order.items()).hasSize(1);
        assertThat(order.items().get(0).getProduct()).isEqualTo(product);
        assertThat(order.items().get(0).getQuantity()).isEqualTo(2);
        assertThat(order.status()).isEqualTo(OrderStatus.PROCESSING);
    }

    @Test
    void addOrder_shouldThrowProductNotFoundException_whenProductDoesNotExist() {

        ProductRepo productRepo = new ProductRepo();
        OrderRepo orderRepo = new OrderListRepo();

        IdService idService = () -> "order-1";

        ShopService shopService = new ShopService(
                productRepo,
                orderRepo,
                idService
        );

        ProductNotFoundException exception = assertThrows(
                ProductNotFoundException.class,
                () -> shopService.addOrder(
                        Map.of(99, 2)
                )
        );

        assertThat(exception.getMessage())
                .isEqualTo("Product with ID 99 does not exist.");
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

        IdService idService = () -> "order-1";

        ShopService shopService = new ShopService(
                productRepo,
                orderRepo,
                idService
        );

        String orderId = shopService.addOrder(
                Map.of(1, 2)
        );

        shopService.changeQuantity(
                orderId,
                1,
                5
        );

        Order order = shopService.getOrder(orderId);

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

        IdService idService = () -> "order-1";

        ShopService shopService = new ShopService(
                productRepo,
                orderRepo,
                idService
        );

        shopService.addOrder(
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

        IdService idService = () -> "order-1";

        ShopService shopService = new ShopService(
                productRepo,
                orderRepo,
                idService
        );

        String orderId = shopService.addOrder(
                Map.of(1, 15)
        );

        assertThat(orderId).isNull();
        assertThat(orderRepo.getAll()).isEmpty();
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

        IdService idService = () -> "order-1";

        ShopService shopService = new ShopService(
                productRepo,
                orderRepo,
                idService
        );

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

        IdService idService = () -> "order-1";

        ShopService shopService = new ShopService(
                productRepo,
                orderRepo,
                idService
        );

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

        IdService idService = () -> "order-1";

        ShopService shopService = new ShopService(
                productRepo,
                orderRepo,
                idService
        );

        shopService.removeGoods(1, 15);

        assertThat(product.getStock())
                .isEqualTo(10);
    }

    @Test
    void getOrdersByStatus_shouldReturnOnlyOrdersWithGivenStatus() {

        ProductRepo productRepo = new ProductRepo();
        OrderRepo orderRepo = new OrderListRepo();

        IdService idService = () -> "order-1";

        ShopService shopService = new ShopService(
                productRepo,
                orderRepo,
                idService
        );

        Instant createdAt = Instant.parse("2026-10-06T12:00:00Z");

        Order order1 = new Order(
                "order-1",
                List.of(),
                OrderStatus.PROCESSING,
                createdAt
        );

        Order order2 = new Order(
                "order-2",
                List.of(),
                OrderStatus.COMPLETED,
                createdAt
        );

        Order order3 = new Order(
                "order-3",
                List.of(),
                OrderStatus.PROCESSING,
                createdAt
        );

        orderRepo.add(order1);
        orderRepo.add(order2);
        orderRepo.add(order3);

        List<Order> actual =
                shopService.getOrdersByStatus(OrderStatus.PROCESSING);

        assertThat(actual)
                .containsExactly(order1, order3);
    }

    @Test
    void updateOrder_shouldUpdateOrderStatus() {

        ProductRepo productRepo = new ProductRepo();
        OrderRepo orderRepo = new OrderListRepo();

        IdService idService = () -> "order-1";

        ShopService shopService = new ShopService(
                productRepo,
                orderRepo,
                idService
        );

        Instant createdAt = Instant.parse("2026-10-06T12:00:00Z");

        Order order = new Order(
                "order-1",
                List.of(),
                OrderStatus.PROCESSING,
                createdAt
        );

        orderRepo.add(order);

        shopService.updateOrder(
                "order-1",
                OrderStatus.IN_DELIVERY
        );

        Order updatedOrder = shopService.getOrder("order-1");

        assertThat(updatedOrder.status())
                .isEqualTo(OrderStatus.IN_DELIVERY);
    }

    @Test
    void addOrder_shouldSetCurrentTimestamp() {

        ProductRepo productRepo = new ProductRepo();

        Product product = new Product(
                1,
                "Laptop",
                new BigDecimal("999.99"),
                10
        );

        productRepo.addProduct(product);

        OrderRepo orderRepo = new OrderListRepo();

        IdService idService = () -> "order-1";

        ShopService shopService = new ShopService(
                productRepo,
                orderRepo,
                idService
        );

        Instant before = Instant.now();

        String orderId = shopService.addOrder(
                Map.of(1, 2)
        );

        Instant after = Instant.now();

        Order order = shopService.getOrder(orderId);

        assertThat(order.createdAt())
                .isBetween(before, after);
    }

    @Test
    void getOldestOrderPerStatus_shouldReturnOldestOrderForEachStatus() {

        ProductRepo productRepo = new ProductRepo();
        OrderRepo orderRepo = new OrderListRepo();

        IdService idService = () -> "order-1";

        ShopService shopService = new ShopService(
                productRepo,
                orderRepo,
                idService
        );

        Order order1 = new Order(
                "order-1",
                List.of(),
                OrderStatus.PROCESSING,
                Instant.parse("2026-10-06T10:00:00Z")
        );

        Order order2 = new Order(
                "order-2",
                List.of(),
                OrderStatus.PROCESSING,
                Instant.parse("2026-10-06T12:00:00Z")
        );

        Order order3 = new Order(
                "order-3",
                List.of(),
                OrderStatus.COMPLETED,
                Instant.parse("2026-10-06T09:00:00Z")
        );

        Order order4 = new Order(
                "order-4",
                List.of(),
                OrderStatus.COMPLETED,
                Instant.parse("2026-10-06T13:00:00Z")
        );

        orderRepo.add(order1);
        orderRepo.add(order2);
        orderRepo.add(order3);
        orderRepo.add(order4);

        Map<OrderStatus, Order> actual =
                shopService.getOldestOrderPerStatus();

        assertThat(actual.get(OrderStatus.PROCESSING))
                .isEqualTo(order1);

        assertThat(actual.get(OrderStatus.COMPLETED))
                .isEqualTo(order3);
    }
}