package service;

import memory.OrderRepository;
import model.*;

import java.awt.*;
import java.time.LocalDateTime;

public class OrderService {
    private ItemFactory itemFactory;
    private OrderRepository orderRepository;

    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public void setItemFactory(ItemFactory itemFactory) {
        this.itemFactory = itemFactory;
    }

    public Order createOrder() {
        Order order = new Order();
        orderRepository.insert(order);
        return order;
    }

    public Order orderProduct(Order order, String name, int price, int quantity) {
        order.addSelectedItem(itemFactory.createProduct(name, price, quantity));
        orderRepository.update(order);
        return order;
    }

    public Order orderService(Order order, String name, int persons, int hours) {
        order.addSelectedItem(itemFactory.createService(name, persons, hours));
        orderRepository.update(order);
        return order;
    }

    public Order finishOrder(Order order) {
        order.setCheckoutTimestamp(LocalDateTime.now());
        order.sortItemsByPriceAsc();
        orderRepository.update(order);
        return order;
    }
}
