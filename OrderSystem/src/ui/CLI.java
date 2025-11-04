package ui;

import model.Item;
import model.ItemFactory;
import model.Order;
import model.Product;
import service.OrderService;
import util.ConsoleIO;

import static resources.Constants.*;
import static resources.Constants.MENU_FINISH_OPTION;
import static resources.Constants.MENU_ORDER_PRODUCT_OPTION;
import static resources.Constants.MENU_ORDER_SERVICE_OPTION;
import static resources.Messages.*;
import static resources.Messages.MENU_ORDER_SERVICE_TEXT;

public class CLI {
    private OrderService orderService;
    private Order order;

    public CLI(OrderService orderService) {
        this.orderService = orderService;
    }

    private void printMenu() {
        ConsoleIO.println(MENU_PROMPT);
        ConsoleIO.println(MENU_FINISH_TEXT);
        ConsoleIO.println(MENU_ORDER_PRODUCT_TEXT);
        ConsoleIO.println(MENU_ORDER_SERVICE_TEXT);
    }

    public void orderloop() {
        do {
            order = orderService.createOrder();
            menuloop();
        } while (placeAnotherOrder());
    }

    private boolean placeAnotherOrder() {
        do {
            ConsoleIO.println(ORDER_AGAIN_PROMPT);
            String input = ConsoleIO.readString();
            int option = parseToOption(input);
            switch (option) {
                case NO_OPTION: return false;
                case YES_OPTION: return true;
                default:
                    ConsoleIO.println(INVALID_INPUT);
                    break;
            }
        } while (true);
    }

    private void menuloop() {
        int option;
        do {
            printMenu();
            String input = ConsoleIO.readString();
            option = parseToOption(input);

            switch (option) {
                case MENU_FINISH_OPTION: break;
                case MENU_ORDER_PRODUCT_OPTION:
                    order = orderService.orderProduct(order, ConsoleIO.requestStringInput(ORDER_NAME_PROMPT),
                            ConsoleIO.requestIntInput(ORDER_PRICE_PROMPT),
                            ConsoleIO.requestIntInput(ORDER_QUANTITY_PROMPT));
                    break;
                case MENU_ORDER_SERVICE_OPTION:
                    order = orderService.orderService(order, ConsoleIO.requestStringInput(SERVICE_TYPE_PROMPT),
                            ConsoleIO.requestIntInput(SERVICE_PERSONS_PROMPT),
                            ConsoleIO.requestIntInput(SERVICE_HOURS_PROMPT));
                    break;
                default:
                    ConsoleIO.println(INVALID_INPUT);
                    break;
            }
        } while (option != MENU_FINISH_OPTION);

        order = orderService.finishOrder(order);
        finishOrderSummary();
    }

    private void finishOrderSummary() {
        ConsoleIO.println(String.valueOf(order.getCheckoutTimestamp()));
        for (Item item : order.getSelectedItems()) {
            ConsoleIO.println(item + " = " + order.formatPrice(item.getPrice()));
        }

        int sum = order.getLumpSum();
        ConsoleIO.println(SUM_LABEL + order.formatPrice(sum));
    }

    private int parseToOption(String input) {
        try {
            return Integer.parseInt(input);
        } catch (Exception ex) {
            return -1;
        }
    }
}
