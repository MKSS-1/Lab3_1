package memory;

import model.Order;

import java.util.ArrayList;
import java.util.List;

public class MemoryOrderRepository implements OrderRepository {

    private final List<Order> orders = new ArrayList<>();

    @Override
    public void insert(Order order) {
        orders.add(order);
    }

    @Override
    public void update(Order order) {
        int i = orders.indexOf(order);
        orders.set(i, order);
    }

    @Override
    public List<Order> findAll() {
       return new ArrayList<>(orders);
    }

    @Override
    public void deleteAll() {
        orders.clear();
    }
}
