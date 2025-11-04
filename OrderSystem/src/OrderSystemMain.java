import memory.MemoryOrderRepository;
import memory.OrderRepository;
import model.ItemFactory;
import model.SimpleItemFactory;
import service.OrderService;
import ui.CLI;
import ui.GUI;

import java.util.Objects;

public class OrderSystemMain {
    // TODO: Wollen wir besser immer eine Kopie der Order verwenden? Z.B. auch bei orderRepository.insert()? Wieso dort nicht?
    // Kopie eventuell besser, da dann die CLI / GUI nicht direkt das Objekt aus dem Repository verändert
    static void main(String[] args) {
        OrderRepository orderRepository = new MemoryOrderRepository();
		OrderService orderService = new OrderService(orderRepository);
        ItemFactory itemFactory = new SimpleItemFactory();
        orderService.setItemFactory(itemFactory);

        if (Objects.equals(args[0], "cli")) {
            CLI cli = new CLI(orderService);
            cli.orderloop();
        } else if (Objects.equals(args[0], "gui")) {
            GUI gui = new GUI(orderService);
            gui.initUI();
        }
	}
}
