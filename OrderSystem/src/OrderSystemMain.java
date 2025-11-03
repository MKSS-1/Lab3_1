import memory.MemoryOrderRepository;
import memory.OrderRepository;
import model.ItemFactory;
import model.SimpleItemFactory;
import service.OrderService;

public class OrderSystemMain {
    static void main() {
        OrderRepository orderRepository = new MemoryOrderRepository();
        ItemFactory itemFactory = new SimpleItemFactory();
		OrderService orderService = new OrderService(orderRepository);
        orderService.setItemFactory(itemFactory);
		orderService.orderloop();
	}
}
