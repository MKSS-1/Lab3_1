package ui;

import model.Item;
import model.ItemFactory;
import model.Order;
import service.OrderService;

import javax.swing.*;
import java.awt.*;

public class GUI {
    private JList<Item> listShoppingCart;
    private OrderService orderService;
    private Order order;
    private JPanel mainPanel;

    public GUI(OrderService orderService) {
        this.orderService = orderService;
    }


    public void run() {
        initLayout();
        // order = orderService.createOrder();
        listShoppingCart = new JList<>();
        // TODO

    }

    private void initLayout() {
        JFrame frame = new JFrame("Test");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        mainPanel = new JPanel(new CardLayout());
        frame.add(mainPanel);

        JPanel buttonPanel = new JPanel();
        JButton buttonNewProduct = new JButton("New product");
        buttonPanel.add(buttonNewProduct);
        buttonNewProduct.addActionListener(e -> onNewProduct());


        JButton buttonNewService = new JButton("New service");
        buttonPanel.add(buttonNewService);
        buttonNewService.addActionListener(e -> onNewService());

        JButton buttonFinishOrder = new JButton("Finish order");
        buttonPanel.add(buttonFinishOrder);
        buttonFinishOrder.addActionListener(e -> onFinishOrder());
        mainPanel.add(buttonPanel, "Menu");

        frame.pack();
        frame.setVisible(true);
    }

    private void onNewProduct() {
        JPanel inputPanel = new JPanel(new GridLayout(0, 2, 10, 10));
        JLabel labelInputName = new JLabel("Name");
        JTextField textFieldInputName = new JTextField();
        inputPanel.add(labelInputName);
        inputPanel.add(textFieldInputName);
        JLabel labelInputPrice = new JLabel("Price");
        JTextField textFieldInputPrice = new JTextField();
        inputPanel.add(labelInputPrice);
        inputPanel.add(textFieldInputPrice);
        JLabel labelInputQuantity = new JLabel("Quantity");
        JTextField textFieldInputQuantity = new JTextField();
        inputPanel.add(labelInputQuantity);
        inputPanel.add(textFieldInputQuantity);

        JButton buttonSubmit = new JButton("Submit");
        inputPanel.add(new JLabel());
        inputPanel.add(buttonSubmit);

        mainPanel.add(inputPanel, "Details");
        CardLayout cardLayout = (CardLayout) mainPanel.getLayout();
        cardLayout.show(mainPanel, "Details");

        buttonSubmit.addActionListener(e -> {
            // orderService.orderProduct(order, textFieldInputName.getText(),
            //         Integer.parseInt(textFieldInputPrice.getText()),
            //         Integer.parseInt(textFieldInputQuantity.getText()));
            textFieldInputName.setText("");
            textFieldInputPrice.setText("");
            textFieldInputQuantity.setText("");

            cardLayout.show(mainPanel, "Menu");
        });
    }

    private void onNewService() {
        // TODO
    }

    private void onFinishOrder() {
        // order = orderService.finishOrder(order);
        // TODO
    }
}
