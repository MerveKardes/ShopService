package de.mervekardes;

import lombok.RequiredArgsConstructor;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class ShopService {

    private final ProductRepo productRepo;
    private final OrderRepo orderRepo;
    private final IdService idService;


    public Optional<Product> getProduct(int id) {
        return productRepo.getProductById(id);
    }

    public List<Product> getProducts() {
        return productRepo.getAllProducts();
    }

    public Order getOrder(String id) {
        return orderRepo.getById(id);
    }

    public List<Order> getOrders() {
        return orderRepo.getAll();
    }

    public List<Order> getOrdersByStatus(OrderStatus status) {
        return orderRepo.getAll()
                .stream()
                .filter(order -> order.status() == status)
                .toList();
    }

    public String addOrder(Map<Integer, Integer> productQuantities) {

        String orderId = idService.generateId();

        List<OrderItem> items = new ArrayList<>();

        for (Map.Entry<Integer, Integer> entry : productQuantities.entrySet()) {

            int productId = entry.getKey();
            int quantity = entry.getValue();

            Product product = productRepo.getProductById(productId)
                    .orElseThrow(() -> new ProductNotFoundException(
                            "Product with ID " + productId + " does not exist."
                    ));

            if (product.getStock() < quantity) {
                System.out.println(
                        "Not enough stock for product " + product.getName() + "."
                );
                return null;
            }

            OrderItem item = new OrderItem(product, quantity);
            items.add(item);
        }

        for (OrderItem item : items) {
            item.getProduct().reduceStock(item.getQuantity());
        }

        Order order = new Order(
                orderId,
                items,
                OrderStatus.PROCESSING,
                Instant.now()
        );

        orderRepo.add(order);

        return orderId;
    }

    public void updateOrder(String orderId, OrderStatus newStatus) {

        Order order = orderRepo.getById(orderId);

        if (order == null) {
            return;
        }

        Order updatedOrder = order.withStatus(newStatus);

        orderRepo.remove(orderId);
        orderRepo.add(updatedOrder);
    }

    public Map<OrderStatus, Order> getOldestOrderPerStatus() {

        return orderRepo.getAll()
                .stream()
                .collect(Collectors.toMap(
                        Order::status,
                        order -> order,
                        (order1, order2) ->
                                order1.createdAt().isBefore(order2.createdAt())
                                        ? order1
                                        : order2
                ));
    }

    public void changeQuantity(String orderId, int productId, int newQuantity) {

        Order order = orderRepo.getById(orderId);

        if (order == null) {
            System.out.println(
                    "Order with ID " + orderId + " does not exist."
            );
            return;
        }

        for (OrderItem item : order.items()) {

            if (item.getProduct().getId() == productId) {

                int oldQuantity = item.getQuantity();
                int difference = newQuantity - oldQuantity;

                Product product = item.getProduct();

                if (difference > 0) {

                    if (product.getStock() < difference) {
                        System.out.println(
                                "Not enough stock for product " + product.getName() + "."
                        );
                        return;
                    }

                    product.reduceStock(difference);

                } else if (difference < 0) {

                    product.increaseStock(-difference);
                }

                item.setQuantity(newQuantity);
                return;
            }
        }

        System.out.println(
                "Product with ID " + productId + " is not part of this order."
        );
    }

    public void receiveGoods(int productId, int quantity) {

        Optional<Product> optionalProduct = productRepo.getProductById(productId);

        if (optionalProduct.isEmpty()) {
            System.out.println(
                    "Product with ID " + productId + " does not exist."
            );
            return;
        }

        Product product = optionalProduct.get();

        product.increaseStock(quantity);
    }
    public void removeGoods(int productId, int quantity) {

        Optional<Product> optionalProduct = productRepo.getProductById(productId);

        if (optionalProduct.isEmpty()) {
            System.out.println(
                    "Product with ID " + productId + " does not exist."
            );
            return;
        }

        Product product = optionalProduct.get();

        if (product.getStock() < quantity) {
            System.out.println(
                    "Not enough stock for product " + product.getName() + "."
            );
            return;
        }

        product.reduceStock(quantity);
    }
}