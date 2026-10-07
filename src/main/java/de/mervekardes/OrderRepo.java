package de.mervekardes;

import java.util.List;

public interface OrderRepo {
    void add(Order order);

    void remove(String id);

    Order getById(String id);

    List<Order> getAll();
}
