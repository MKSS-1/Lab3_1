package memory;

import model.Order;
import model.OrderDTO;

import java.util.List;

public interface OrderRepository {
    Order insert(Order order);
    Order update(Order order);
    List<Order> findAll();
    void deleteAll();
}
