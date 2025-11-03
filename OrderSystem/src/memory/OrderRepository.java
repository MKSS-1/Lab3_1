package memory;

import model.Order;

import java.util.List;

public interface OrderRepository {
    void insert(Order order);
    void update(Order order);
    List<Order> findAll();
    void deleteAll();
}
