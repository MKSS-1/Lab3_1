package ui;

import memory.MemoryOrderRepository;
import memory.OrderRepository;
import model.*;
import service.OrderService;

import javax.swing.*;
import java.awt.*;

public class OrderUI extends JFrame {

    private final OrderService orderService;

    public OrderUI() {
        OrderRepository repository = new MemoryOrderRepository();
        ItemFactory factory = new SimpleItemFactory();

        orderService = new OrderService(repository);
        orderService.setItemFactory(factory);

        initUI();
    }

    private void initUI() {
        setTitle("Order System");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        setLayout(new BorderLayout(10, 10));

        JButton btnNewOrder = createButton("Start New Order");
        JButton btnAddProduct = createButton("Add Product");
        JButton btnAddService = createButton("Add Service");
        JButton btnFinish = createButton("Complete Order");

        btnAddProduct.setEnabled(false);
        btnAddService.setEnabled(false);
        btnFinish.setEnabled(false);

        JTextArea outputArea = new JTextArea();
        outputArea.setEditable(false);
        outputArea.setLineWrap(true);
        outputArea.setWrapStyleWord(true);
        outputArea.setFont(new Font("Monospaced", Font.PLAIN, 14));
        JScrollPane scrollPane = new JScrollPane(outputArea);

        JPanel buttonPanel = new JPanel();
        FlowLayout flowLayout = new FlowLayout(FlowLayout.CENTER, 5, 20);
        buttonPanel.setLayout(flowLayout);
        buttonPanel.add(btnNewOrder);
        buttonPanel.add(btnAddProduct);
        buttonPanel.add(btnAddService);
        buttonPanel.add(btnFinish);

        JScrollPane buttonScrollPane = new JScrollPane(buttonPanel);
        buttonScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        buttonScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);
        buttonScrollPane.setBorder(BorderFactory.createEmptyBorder());

        buttonScrollPane.getViewport().addChangeListener(e -> {
            int viewportWidth = buttonScrollPane.getViewport().getWidth();
            int panelWidth = buttonPanel.getPreferredSize().width;

            if (panelWidth <= viewportWidth) {
                flowLayout.setAlignment(FlowLayout.CENTER);
            } else {
                flowLayout.setAlignment(FlowLayout.LEFT);
            }
            buttonPanel.revalidate();
        });

        add(buttonScrollPane, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);

        btnNewOrder.addActionListener(e -> {
            orderService.startNewOrder();
            outputArea.setText("New order started!\n");

            btnAddProduct.setEnabled(true);
            btnAddService.setEnabled(true);
            btnFinish.setEnabled(true);
            btnNewOrder.setEnabled(false);
        });

        btnAddProduct.addActionListener(e -> showAddItemDialog(true, outputArea));
        btnAddService.addActionListener(e -> showAddItemDialog(false, outputArea));

        btnFinish.addActionListener(e -> {
            String summary = orderService.finishOrderAndReturnSummary();
            outputArea.append("\n--- Order completed ---\n" + summary + "\n");

            btnAddProduct.setEnabled(false);
            btnAddService.setEnabled(false);
            btnFinish.setEnabled(false);
            btnNewOrder.setEnabled(true);
        });

        pack();
        Dimension size = getSize();
        size.height = 500;
        setSize(size);

        setLocationRelativeTo(null);
        setVisible(true);
    }

    private JButton createButton(String text) {
        JButton btn = new JButton(text);
        btn.setPreferredSize(new Dimension(180, 35));
        btn.setBackground(new Color(70, 130, 180));
        btn.setForeground(Color.WHITE);
        btn.setFocusPainted(false);
        return btn;
    }

    private void showAddItemDialog(boolean isProduct, JTextArea outputArea) {
        JDialog dialog = new JDialog(this, isProduct ? "Add Product" : "Add Service", true);
        dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);

        JTextField nameField = new JTextField(15);
        JTextField field2 = new JTextField(15);
        JTextField field3 = new JTextField(15);
        JLabel hintLabel = new JLabel();
        hintLabel.setForeground(Color.RED);

        JPanel inputPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(4, 4, 4, 4);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        String[] labels = isProduct
                ? new String[]{"Product Name:", "Price (cents):", "Quantity:"}
                : new String[]{"Service Name:", "Number of People:", "Hours:"};
        JTextField[] fields = new JTextField[]{nameField, field2, field3};

        for (int i = 0; i < labels.length; i++) {
            gbc.gridx = 0;
            gbc.gridy = i;
            gbc.weightx = 0.3;
            inputPanel.add(new JLabel(labels[i]), gbc);

            gbc.gridx = 1;
            gbc.weightx = 0.7;
            inputPanel.add(fields[i], gbc);
        }

        JButton okButton = new JButton("OK");
        JButton cancelButton = new JButton("Cancel");

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(okButton);
        buttonPanel.add(cancelButton);

        JPanel mainPanel = new JPanel(new BorderLayout(0, 10));
        mainPanel.add(hintLabel, BorderLayout.NORTH);
        mainPanel.add(inputPanel, BorderLayout.CENTER);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        dialog.getContentPane().add(mainPanel);
        dialog.pack();
        dialog.setLocationRelativeTo(this);

        okButton.addActionListener(e -> {
            hintLabel.setText("");
            for (JTextField f : fields) f.setBackground(Color.WHITE);

            boolean valid = true;
            StringBuilder sb = new StringBuilder("<html>");
            String name = nameField.getText().trim();
            String text2 = field2.getText().trim();
            String text3 = field3.getText().trim();

            if (name.isEmpty()) {
                nameField.setBackground(new Color(255, 180, 180));
                valid = false;
                sb.append("⦾ ").append(isProduct ? "Product name" : "Service name").append(" cannot be empty.<br>");
            }

            int val2 = 0, val3 = 0;
            try {
                val2 = Integer.parseInt(text2);
                if (val2 <= 0) throw new NumberFormatException();
            } catch (NumberFormatException ex) {
                field2.setBackground(new Color(255, 180, 180));
                valid = false;
                sb.append("⦾ ").append(isProduct ? "Price" : "Number of people").append(" must be positive.<br>");
            }

            try {
                val3 = Integer.parseInt(text3);
                if (val3 <= 0) throw new NumberFormatException();
            } catch (NumberFormatException ex) {
                field3.setBackground(new Color(255, 180, 180));
                valid = false;
                sb.append("⦾ ").append(isProduct ? "Quantity" : "Hours").append(" must be positive.<br>");
            }

            sb.append("</html>");

            if (valid) {
                if (isProduct) {
                    orderService.addProduct(name, val2, val3);
                    outputArea.append("Product added: " + val3 + " × " + name + " (" + val2 + " cents each)\n");
                } else {
                    orderService.addService(name, val2, val3);
                    outputArea.append("Service added: " + val2 + " people × " + val3 + "h " + name + "\n");
                }
                dialog.dispose();
            } else {
                hintLabel.setText(sb.toString());
                dialog.pack();
                dialog.revalidate();
            }
        });

        cancelButton.addActionListener(e -> dialog.dispose());

        dialog.setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(OrderUI::new);
    }
}
