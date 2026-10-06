package de.mervekardes;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class OrderListRepoTest {

    @Test
    void add_shouldAddOrder() {

        OrderListRepo orderRepo = new OrderListRepo();

        Product product = new Product(
                1,
                "Laptop",
                new BigDecimal("999.99"),
                10
        );

        OrderItem item = new OrderItem(product, 2);
        Order order = new Order(1, List.of(item),OrderStatus.PROCESSING);

        orderRepo.add(order);

        assertThat(orderRepo.getAll())
                .contains(order);
    }

    @Test
    void getById_shouldReturnOrder_whenOrderExists() {

        OrderListRepo orderRepo = new OrderListRepo();

        Product product = new Product(
                1,
                "Laptop",
                new BigDecimal("999.99"),
                10
        );

        OrderItem item = new OrderItem(product, 2);
        Order order = new Order(1, List.of(item),OrderStatus.PROCESSING);

        orderRepo.add(order);

        Order actual = orderRepo.getById(1);

        assertThat(actual)
                .isEqualTo(order);
    }

    @Test
    void getById_shouldReturnNull_whenOrderDoesNotExist() {

        OrderListRepo orderRepo = new OrderListRepo();

        Order actual = orderRepo.getById(99);

        assertThat(actual)
                .isNull();
    }

    @Test
    void remove_shouldRemoveOrder_whenOrderExists() {

        OrderListRepo orderRepo = new OrderListRepo();

        Product product = new Product(
                1,
                "Laptop",
                new BigDecimal("999.99"),
                10
        );

        OrderItem item = new OrderItem(product, 2);
        Order order = new Order(1, List.of(item),OrderStatus.PROCESSING);

        orderRepo.add(order);

        orderRepo.remove(1);

        assertThat(orderRepo.getAll())
                .doesNotContain(order);
    }
}