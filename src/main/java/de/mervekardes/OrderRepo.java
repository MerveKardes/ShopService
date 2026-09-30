package de.mervekardes;

import java.util.List;

public interface OrderRepo {
    void add(Order order);

    void remove(int id);

    Order getById(int id);

    List<Order> getAll();
}
