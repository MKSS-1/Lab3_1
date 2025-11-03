import model.ItemFactory;
import model.SimpleItemFactory;
import service.OrderService;

public class OrderSystemMain {
    static void main() {
        ItemFactory itemFactory = new SimpleItemFactory();
		OrderService orderService = new OrderService();
        orderService.setItemFactory(itemFactory);
		orderService.orderloop();
	}
}
