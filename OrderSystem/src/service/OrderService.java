package service;

import memory.OrderRepository;
import model.*;
import util.ConsoleIO;
import util.Formatter;
import static resources.Messages.*;
import static resources.Constants.*;

import java.awt.*;
import java.time.LocalDateTime;
import java.util.Comparator;

public class OrderService {

    private ItemFactory itemFactory;

    private Order order;

    private OrderRepository orderRepository;

    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public void orderloop() {
        do {
            order = new Order();
            orderRepository.insert(order);
            menuloop();
        } while (placeAnotherOrder());
    }

    public void setItemFactory(ItemFactory itemFactory) {
        this.itemFactory = itemFactory;
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
                    orderProduct();
                    break;
                case MENU_ORDER_SERVICE_OPTION:
                    orderService();
                    break;
                default:
                    ConsoleIO.println(INVALID_INPUT);
                    break;
            }
        } while (option != MENU_FINISH_OPTION);

        sortItemsByPriceAsc();
        finishOrder();
    }

    private void printMenu() {
        ConsoleIO.println(MENU_PROMPT);
        ConsoleIO.println(MENU_FINISH_TEXT);
        ConsoleIO.println(MENU_ORDER_PRODUCT_TEXT);
        ConsoleIO.println(MENU_ORDER_SERVICE_TEXT);
    }

    private void sortItemsByPriceAsc() {
        order.getSelectedItems().sort(Comparator.comparingInt(Item::getPrice));
    }

    private void orderProduct() {
        String name = ConsoleIO.requestStringInput(ORDER_NAME_PROMPT);
        int price = ConsoleIO.requestIntInput(ORDER_PRICE_PROMPT);
        int quantity = ConsoleIO.requestIntInput(ORDER_QUANTITY_PROMPT);
        order.addSelectedItem(itemFactory.createProduct(name, price, quantity));
        orderRepository.update(order);
    }

    private void orderService() {
        String name = ConsoleIO.requestStringInput(SERVICE_TYPE_PROMPT);
        int persons = ConsoleIO.requestIntInput(SERVICE_PERSONS_PROMPT);
        int hours = ConsoleIO.requestIntInput(SERVICE_HOURS_PROMPT);
        order.addSelectedItem(itemFactory.createService(name, persons, hours));
        orderRepository.update(order);
    }

    public void startNewOrder() {
        order = new Order();
        orderRepository.insert(order);
    }

    public void addProduct(String name, int price, int quantity) {
        order.addSelectedItem(itemFactory.createProduct(name, price, quantity));
        orderRepository.update(order);
    }

    public void addService(String name, int persons, int hours) {
        order.addSelectedItem(itemFactory.createService(name, persons, hours));
        orderRepository.update(order);
    }

    public String finishOrderAndReturnSummary() {
        order.setCheckoutTimestamp(LocalDateTime.now());
        orderRepository.update(order);

        StringBuilder sb = new StringBuilder();
        for (Item item : order.getSelectedItems()) {
            // einfach Preis aus Item verwenden, er ist schon korrekt berechnet
            sb.append(item).append(" = ").append(Formatter.formatPrice(item.getPrice())).append("\n");
        }

        sb.append("\nTotal sum: ")
                .append(Formatter.formatPrice(order.getLumpSum()))
                .append("\n");

        return sb.toString();
    }

    private void finishOrder() {
        order.setCheckoutTimestamp(LocalDateTime.now());

        orderRepository.update(order);

        for (Item item : order.getSelectedItems()) {
            ConsoleIO.println(item + " = " + Formatter.formatPrice(item.getPrice()));
        }

        int sum = order.getLumpSum();
        ConsoleIO.println(SUM_LABEL + Formatter.formatPrice(sum));


        //for test purposes
        //orderRepository.findAll().forEach( v -> v.getSelectedItems().forEach(item -> ConsoleIO.println(item + " = " + Formatter.formatPrice(item.getPrice()))));
    }

    private int parseToOption(String input) {
        try {
            return Integer.parseInt(input);
        } catch (Exception ex) {
            return -1;
        }
    }
}
