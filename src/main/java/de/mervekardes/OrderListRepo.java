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
    public void remove(int id) {
        Order order = getById(id);

        if (order != null) {
            orders.remove(order);
        }
    }

    @Override
    public Order getById(int id) {
        for (Order order : orders) {
            if (order.id() == id) {
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