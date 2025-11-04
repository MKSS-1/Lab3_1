package ui;

import memory.MemoryOrderRepository;
import memory.OrderRepository;
import model.*;
import service.OrderService;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

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

        JButton btnNewOrder = createButton("Neue Bestellung starten");
        JButton btnAddProduct = createButton("Produkt hinzufügen");
        JButton btnAddService = createButton("Service hinzufügen");
        JButton btnFinish = createButton("Bestellung abschließen");

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
            outputArea.setText("Neue Bestellung gestartet!\n");

            btnAddProduct.setEnabled(true);
            btnAddService.setEnabled(true);
            btnFinish.setEnabled(true);
            btnNewOrder.setEnabled(false);
        });

        btnAddProduct.addActionListener(e -> handleAddItem(true, outputArea));
        btnAddService.addActionListener(e -> handleAddItem(false, outputArea));

        btnFinish.addActionListener(e -> {
            String summary = orderService.finishOrderAndReturnSummary();
            outputArea.append("\n--- Bestellung abgeschlossen ---\n" + summary + "\n");

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

    private void handleAddItem(boolean isProduct, JTextArea outputArea) {
        JTextField nameField = new JTextField();
        JTextField field2 = new JTextField();
        JTextField field3 = new JTextField();

        Dimension fieldSize = new Dimension(120, 25);
        nameField.setPreferredSize(fieldSize);
        field2.setPreferredSize(fieldSize);
        field3.setPreferredSize(fieldSize);

        JLabel hintLabel = new JLabel();
        hintLabel.setForeground(Color.RED);
        hintLabel.setVisible(false);

        JPanel inputPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(4, 4, 4, 4);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        String[] labels = isProduct
                ? new String[]{"Produktname:", "Preis (in Cent):", "Menge:"}
                : new String[]{"Servicename:", "Anzahl Personen:", "Stunden:"};

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

        JPanel panel = new JPanel(new BorderLayout(0, 10));
        panel.add(hintLabel, BorderLayout.NORTH);
        panel.add(inputPanel, BorderLayout.CENTER);
        panel.setPreferredSize(new Dimension(400, 150));

        boolean valid = false;
        while (!valid) {
            int result = JOptionPane.showConfirmDialog(
                    this, panel, isProduct ? "Produkt hinzufügen" : "Service hinzufügen",
                    JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE
            );

            if (result != JOptionPane.OK_OPTION) return;

            nameField.setBackground(Color.WHITE);
            field2.setBackground(Color.WHITE);
            field3.setBackground(Color.WHITE);
            hintLabel.setVisible(false);

            valid = true;
            List<String> errors = new ArrayList<>();

            String name = nameField.getText().trim();
            String text2 = field2.getText().trim();
            String text3 = field3.getText().trim();

            if (name.isEmpty()) {
                nameField.setBackground(new Color(255, 180, 180));
                valid = false;
                errors.add("⦾ " + (isProduct ? "Produktname" : "Servicename") + " darf nicht leer sein.");
            }

            int val2 = 0, val3 = 0;
            try {
                val2 = Integer.parseInt(text2);
                if (val2 <= 0) throw new NumberFormatException();
            } catch (NumberFormatException ex) {
                field2.setBackground(new Color(255, 180, 180));
                valid = false;
                errors.add("⦾ " + (isProduct ? "Preis" : "Personenzahl") + " muss eine positive Zahl sein.");
            }

            try {
                val3 = Integer.parseInt(text3);
                if (val3 <= 0) throw new NumberFormatException();
            } catch (NumberFormatException ex) {
                field3.setBackground(new Color(255, 180, 180));
                valid = false;
                errors.add("⦾ " + (isProduct ? "Menge" : "Stunden") + " muss eine positive Zahl sein.");
            }

            if (valid) {
                if (isProduct) {
                    orderService.addProduct(name, val2, val3);
                    outputArea.append("Produkt hinzugefügt: " + val3 + " × " + name + " (" + val2 + " Cent/Stück)\n");
                } else {
                    orderService.addService(name, val2, val3);
                    outputArea.append("Service hinzugefügt: " + val2 + " Pers. × " + val3 + "h " + name + "\n");
                }
            } else {
                StringBuilder sb = new StringBuilder("<html>");
                for (String err : errors) sb.append(err).append("<br>");
                sb.append("</html>");
                hintLabel.setText(sb.toString());
                hintLabel.setVisible(true);
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(OrderUI::new);
    }
}
