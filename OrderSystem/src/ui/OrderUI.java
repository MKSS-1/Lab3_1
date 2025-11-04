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
        // Repository + Factory erstellen
        OrderRepository repository = new MemoryOrderRepository();
        ItemFactory factory = new SimpleItemFactory();

        // Service initialisieren
        orderService = new OrderService(repository);
        orderService.setItemFactory(factory);

        initUI();
    }

    private void initUI() {
        setTitle("Order System");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 400);
        setLayout(new BorderLayout());

        // Buttons
        JButton btnNewOrder = new JButton("Neue Bestellung starten");
        JButton btnAddProduct = new JButton("Produkt hinzufügen");
        JButton btnAddService = new JButton("Service hinzufügen");
        JButton btnFinish = new JButton("Bestellung abschließen");

        // Buttons initial deaktivieren
        btnAddProduct.setEnabled(false);
        btnAddService.setEnabled(false);
        btnFinish.setEnabled(false);

        JTextArea outputArea = new JTextArea();
        outputArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(outputArea);

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(btnNewOrder);
        buttonPanel.add(btnAddProduct);
        buttonPanel.add(btnAddService);
        buttonPanel.add(btnFinish);

        add(buttonPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);

        // --- Aktionen ---

        // Neue Bestellung starten
        btnNewOrder.addActionListener(e -> {
            orderService.startNewOrder();
            outputArea.setText("Neue Bestellung gestartet!\n");

            btnAddProduct.setEnabled(true);
            btnAddService.setEnabled(true);
            btnFinish.setEnabled(true);
            btnNewOrder.setEnabled(false);
        });

        // Produkt hinzufügen
        btnAddProduct.addActionListener(e -> {
            JTextField nameField = new JTextField(15);
            JTextField priceField = new JTextField(10);
            JTextField qtyField = new JTextField(10);

            JLabel hintLabel = new JLabel();
            hintLabel.setForeground(Color.RED);
            hintLabel.setVisible(false);

            JPanel inputPanel = new JPanel(new GridLayout(0, 2, 8, 8));
            inputPanel.add(new JLabel("Produktname:"));
            inputPanel.add(nameField);
            inputPanel.add(new JLabel("Preis (in Cent):"));
            inputPanel.add(priceField);
            inputPanel.add(new JLabel("Menge:"));
            inputPanel.add(qtyField);

            JPanel panel = new JPanel(new BorderLayout(0, 10));
            panel.add(hintLabel, BorderLayout.NORTH);
            panel.add(inputPanel, BorderLayout.CENTER);

            boolean valid = false;
            while (!valid) {
                int result = JOptionPane.showConfirmDialog(
                        this, panel, "Produkt hinzufügen",
                        JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE
                );

                if (result != JOptionPane.OK_OPTION) return;

                // Farben & Meldungen zurücksetzen
                nameField.setBackground(Color.WHITE);
                priceField.setBackground(Color.WHITE);
                qtyField.setBackground(Color.WHITE);
                hintLabel.setVisible(false);

                valid = true;
                List<String> errors = new ArrayList<>();

                String name = nameField.getText().trim();
                String priceText = priceField.getText().trim();
                String qtyText = qtyField.getText().trim();

                if (name.isEmpty()) {
                    nameField.setBackground(new Color(255, 180, 180));
                    valid = false;
                    errors.add("⦾ Produktname darf nicht leer sein.");
                }

                int price = 0, qty = 0;
                try {
                    price = Integer.parseInt(priceText);
                    if (price <= 0) throw new NumberFormatException();
                } catch (NumberFormatException ex) {
                    priceField.setBackground(new Color(255, 180, 180));
                    valid = false;
                    errors.add("⦾ Preis muss eine positive Zahl sein.");
                }

                try {
                    qty = Integer.parseInt(qtyText);
                    if (qty <= 0) throw new NumberFormatException();
                } catch (NumberFormatException ex) {
                    qtyField.setBackground(new Color(255, 180, 180));
                    valid = false;
                    errors.add("⦾ Menge muss eine positive Zahl sein.");
                }

                if (valid) {
                    orderService.addProduct(name, price, qty);
                    outputArea.append("Produkt hinzugefügt: " + qty + " × " + name + " (" + price + " Cent/Stück)\n");
                } else {
                    hintLabel.setText("<html>" + String.join("<br>", errors) + "</html>");
                    hintLabel.setVisible(true);
                }
            }
        });

        // Service hinzufügen
        btnAddService.addActionListener(e -> {
            JTextField nameField = new JTextField(15);
            JTextField personsField = new JTextField(10);
            JTextField hoursField = new JTextField(10);

            JLabel hintLabel = new JLabel();
            hintLabel.setForeground(Color.RED);
            hintLabel.setVisible(false);

            JPanel inputPanel = new JPanel(new GridLayout(0, 2, 8, 8));
            inputPanel.add(new JLabel("Servicename:"));
            inputPanel.add(nameField);
            inputPanel.add(new JLabel("Anzahl Personen:"));
            inputPanel.add(personsField);
            inputPanel.add(new JLabel("Stunden:"));
            inputPanel.add(hoursField);

            JPanel panel = new JPanel(new BorderLayout(0, 10));
            panel.add(hintLabel, BorderLayout.NORTH);
            panel.add(inputPanel, BorderLayout.CENTER);

            boolean valid = false;
            while (!valid) {
                int result = JOptionPane.showConfirmDialog(
                        this, panel, "Service hinzufügen",
                        JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE
                );

                if (result != JOptionPane.OK_OPTION) return;

                // Farben & Meldungen zurücksetzen
                nameField.setBackground(Color.WHITE);
                personsField.setBackground(Color.WHITE);
                hoursField.setBackground(Color.WHITE);
                hintLabel.setVisible(false);

                valid = true;
                List<String> errors = new ArrayList<>();

                String name = nameField.getText().trim();
                String personsText = personsField.getText().trim();
                String hoursText = hoursField.getText().trim();

                if (name.isEmpty()) {
                    nameField.setBackground(new Color(255, 180, 180));
                    valid = false;
                    errors.add("⦾ Servicename darf nicht leer sein.");
                }

                int persons = 0, hours = 0;
                try {
                    persons = Integer.parseInt(personsText);
                    if (persons <= 0) throw new NumberFormatException();
                } catch (NumberFormatException ex) {
                    personsField.setBackground(new Color(255, 180, 180));
                    valid = false;
                    errors.add("⦾ Personenzahl muss eine positive Zahl sein.");
                }

                try {
                    hours = Integer.parseInt(hoursText);
                    if (hours <= 0) throw new NumberFormatException();
                } catch (NumberFormatException ex) {
                    hoursField.setBackground(new Color(255, 180, 180));
                    valid = false;
                    errors.add("⦾ Stunden müssen eine positive Zahl sein.");
                }

                if (valid) {
                    orderService.addService(name, persons, hours);
                    outputArea.append("Service hinzugefügt: " + persons + " Pers. × " + hours + "h " + name + "\n");
                } else {
                    hintLabel.setText("<html>" + String.join("<br>", errors) + "</html>");
                    hintLabel.setVisible(true);
                }
            }
        });

        // Bestellung abschließen
        btnFinish.addActionListener(e -> {
            String summary = orderService.finishOrderAndReturnSummary();
            outputArea.append("\n--- Bestellung abgeschlossen ---\n" + summary + "\n");

            btnAddProduct.setEnabled(false);
            btnAddService.setEnabled(false);
            btnFinish.setEnabled(false);
            btnNewOrder.setEnabled(true);
        });

        setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(OrderUI::new);
    }
}
