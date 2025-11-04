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

    public OrderDTO createOrder() {
        Order order = new Order();
        return new OrderDTO(orderRepository.insert(order));
    }

    public OrderDTO orderProduct(OrderDTO orderDTO, String name, int price, int quantity) {
        Order order = new Order(orderDTO);
        order.addSelectedItem(itemFactory.createProduct(name, price, quantity));
        return new OrderDTO(orderRepository.update(order));
    }

    public OrderDTO orderService(OrderDTO orderDTO, String name, int persons, int hours) {
        Order order = new Order(orderDTO);
        order.addSelectedItem(itemFactory.createService(name, persons, hours));
        return new OrderDTO(orderRepository.update(order));
    }

    public OrderDTO finishOrder(OrderDTO orderDTO) {
        Order order = new Order(orderDTO);
        order.setCheckoutTimestamp(LocalDateTime.now());
        order.sortItemsByPriceAsc();
        return new OrderDTO(orderRepository.update(order));
    }
}
