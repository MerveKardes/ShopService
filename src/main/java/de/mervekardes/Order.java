package de.mervekardes;

import java.math.BigDecimal;
import java.util.List;

public record Order(
        int id,
        List<OrderItem> items,
        OrderStatus status
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