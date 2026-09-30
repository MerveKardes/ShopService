package de.mervekardes;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ShopService {

    private ProductRepo productRepo;
    private OrderRepo orderRepo;

    public ShopService(ProductRepo productRepo, OrderRepo orderRepo) {
        this.productRepo = productRepo;
        this.orderRepo = orderRepo;
    }

    public Product getProduct(int id) {
        return productRepo.getProductById(id);
    }

    public List<Product> getProducts() {
        return productRepo.getAllProducts();
    }

    public Order getOrder(int id) {
        return orderRepo.getById(id);
    }

    public List<Order> getOrders() {
        return orderRepo.getAll();
    }

    public void addOrder(int orderId, Map<Integer, Integer> productQuantities) {

        List<OrderItem> items = new ArrayList<>();

        for (Map.Entry<Integer, Integer> entry : productQuantities.entrySet()) {

            int productId = entry.getKey();
            int quantity = entry.getValue();

            Product product = productRepo.getProductById(productId);

            if (product == null) {
                System.out.println(
                        "Product with ID " + productId + " does not exist."
                );
                return;
            }

            if (product.getStock() < quantity) {
                System.out.println(
                        "Not enough stock for product " + product.getName() + "."
                );
                return;
            }

            OrderItem item = new OrderItem(product, quantity);
            items.add(item);
        }

        for (OrderItem item : items) {
            item.getProduct().reduceStock(item.getQuantity());
        }

        Order order = new Order(orderId, items);
        orderRepo.add(order);
    }

    public void changeQuantity(int orderId, int productId, int newQuantity) {

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

        Product product = productRepo.getProductById(productId);

        if (product == null) {
            System.out.println(
                    "Product with ID " + productId + " does not exist."
            );
            return;
        }

        product.increaseStock(quantity);
    }

    public void removeGoods(int productId, int quantity) {

        Product product = productRepo.getProductById(productId);

        if (product == null) {
            System.out.println(
                    "Product with ID " + productId + " does not exist."
            );
            return;
        }

        if (product.getStock() < quantity) {
            System.out.println(
                    "Not enough stock for product " + product.getName() + "."
            );
            return;
        }

        product.reduceStock(quantity);
    }
}