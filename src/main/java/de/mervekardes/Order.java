package de.mervekardes;

import lombok.With;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

@With
public record Order(
        String id,
        List<OrderItem> items,
        OrderStatus status,
        Instant createdAt
) {

    public BigDecimal getTotalPrice() {
        BigDecimal total = BigDecimal.ZERO;

        for (OrderItem item : items) {
            BigDecimal itemTotal = item.getProduct()
                    .getPrice()
                    .multiply(BigDecimal.valueOf(item.getQuantity()));

            total = total.add(itemTotal);
        }

        return total;
    }
}