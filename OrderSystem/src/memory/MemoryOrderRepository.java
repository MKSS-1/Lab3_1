package memory;

import model.Order;
import model.OrderDTO;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class MemoryOrderRepository implements OrderRepository {

    private final List<Order> orders = new ArrayList<>();

    @Override
    public Order insert(Order order) {
        orders.add(order);
        return new Order(order);
    }

    @Override
    public Order update(Order order) {
        int i = orders.indexOf(order);
        orders.set(i, order);
        return new Order(orders.get(i));
    }

    @Override
    public List<Order> findAll() {
        return clone(orders);
    }

    @Override
    public void deleteAll() {
        orders.clear();
    }

    private List<Order> clone(List<Order> orders) {
        List<Order> orderList = new ArrayList<>();
        for (Order order : orders) {
            orderList.add(new Order(order));
        }
        return orderList;
    }
}
