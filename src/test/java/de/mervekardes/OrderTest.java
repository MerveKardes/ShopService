package de.mervekardes;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class OrderTest {

    @Test
    void getTotalPrice_shouldReturnCorrectTotalPrice() {

        Product laptop = new Product(
                1,
                "Laptop",
                new BigDecimal("999.99"),
                10
        );

        Product mouse = new Product(
                2,
                "Mouse",
                new BigDecimal("29.99"),
                10
        );

        OrderItem laptopItem = new OrderItem(laptop, 2);
        OrderItem mouseItem = new OrderItem(mouse, 3);

        Order order = new Order(
                1,
                List.of(laptopItem, mouseItem),
                OrderStatus.PROCESSING
        );

        assertThat(order.getTotalPrice())
                .isEqualByComparingTo(new BigDecimal("2089.95"));
    }
}