package de.mervekardes;

import java.util.ArrayList;
import java.util.List;

public class OrderListRepo implements OrderRepo {

    private List<Order> orders = new ArrayList<>();

    @Override
    public void add(Order order) {
        orders.add(order);
    }

    @Override
    public void remove(String id) {
        orders.removeIf(order -> order.id().equals(id));
    }

    @Override
    public Order getById(String id) {
        for (Order order : orders) {
            if (order.id().equals(id)) {
                return order;
            }
        }
        return null;
    }

    @Override
    public List<Order> getAll() {
        return orders;
    }
}