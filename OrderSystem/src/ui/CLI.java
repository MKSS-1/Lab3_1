package ui;

import model.*;
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
    private OrderDTO orderDTO;

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
            orderDTO = orderService.createOrder();
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
                    orderDTO = orderService.orderProduct(orderDTO, ConsoleIO.requestStringInput(ORDER_NAME_PROMPT),
                            ConsoleIO.requestIntInput(ORDER_PRICE_PROMPT),
                            ConsoleIO.requestIntInput(ORDER_QUANTITY_PROMPT));
                    break;
                case MENU_ORDER_SERVICE_OPTION:
                    orderDTO = orderService.orderService(orderDTO, ConsoleIO.requestStringInput(SERVICE_TYPE_PROMPT),
                            ConsoleIO.requestIntInput(SERVICE_PERSONS_PROMPT),
                            ConsoleIO.requestIntInput(SERVICE_HOURS_PROMPT));
                    break;
                default:
                    ConsoleIO.println(INVALID_INPUT);
                    break;
            }
        } while (option != MENU_FINISH_OPTION);

        orderDTO = orderService.finishOrder(orderDTO);
        finishOrderSummary();
    }

    private void finishOrderSummary() {
        ConsoleIO.println(orderDTO.formatCheckoutTimestamp(orderDTO.getCheckoutTimestamp()));
        for (Item item : orderDTO.getSelectedItems()) {
            ConsoleIO.println(item + " = " + orderDTO.formatPrice(item.getPrice()));
        }

        int sum = orderDTO.getLumpSum();
        ConsoleIO.println(SUM_LABEL + orderDTO.formatPrice(sum));
    }

    private int parseToOption(String input) {
        try {
            return Integer.parseInt(input);
        } catch (Exception ex) {
            return -1;
        }
    }
}
