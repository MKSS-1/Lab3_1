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

        // Buttons initial deaktivieren (außer Neue Bestellung)
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

        // --- Hilfsmethoden für Validierung ---
        java.util.function.Function<String, String> askForNonEmptyInput = message -> {
            JTextField textField = new JTextField(15);
            JLabel label = new JLabel(message);
            JPanel panel = new JPanel(new BorderLayout());
            panel.add(label, BorderLayout.NORTH);
            panel.add(textField, BorderLayout.CENTER);

            while (true) {
                int result = JOptionPane.showConfirmDialog(
                        this, panel, "Eingabe", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

                if (result != JOptionPane.OK_OPTION) return null;

                if (textField.getText().trim().isEmpty()) {
                    textField.setBackground(Color.PINK);
                    label.setText(message + " (nicht leer!)");
                } else {
                    textField.setBackground(Color.WHITE);
                    label.setText(message);
                    return textField.getText().trim();
                }
            }
        };

        java.util.function.Function<String, Integer> askForIntegerInput = message -> {
            JTextField textField = new JTextField(15);
            JLabel label = new JLabel(message);
            JPanel panel = new JPanel(new BorderLayout());
            panel.add(label, BorderLayout.NORTH);
            panel.add(textField, BorderLayout.CENTER);

            while (true) {
                int result = JOptionPane.showConfirmDialog(
                        this, panel, "Eingabe", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

                if (result != JOptionPane.OK_OPTION) return null;

                try {
                    int value = Integer.parseInt(textField.getText().trim());
                    textField.setBackground(Color.WHITE);
                    label.setText(message);
                    return value;
                } catch (NumberFormatException e) {
                    textField.setBackground(Color.PINK);
                    label.setText(message + " (ungültig, Zahl erforderlich!)");
                }
            }
        };

        // Produkt hinzufügen
        btnAddProduct.addActionListener(e -> {
            String name = askForNonEmptyInput.apply("Produktname:");
            if (name == null) return;

            Integer price = askForIntegerInput.apply("Preis:");
            if (price == null) return;

            Integer qty = askForIntegerInput.apply("Menge:");
            if (qty == null) return;

            orderService.addProduct(name, price, qty);
            outputArea.append("Produkt hinzugefügt: " + name + "\n");
        });

        // Service hinzufügen
        btnAddService.addActionListener(e -> {
            String name = askForNonEmptyInput.apply("Servicename:");
            if (name == null) return;

            Integer persons = askForIntegerInput.apply("Personen:");
            if (persons == null) return;

            Integer hours = askForIntegerInput.apply("Stunden:");
            if (hours == null) return;

            orderService.addService(name, persons, hours);
            outputArea.append("Service hinzugefügt: " + name + "\n");
        });

        // Bestellung abschließen
        btnFinish.addActionListener(e -> {
            // Bestellung abschließen und Zusammenfassung aus Service holen
            String summary = orderService.finishOrderAndReturnSummary();

            // Ausgabe in das Textfeld
            outputArea.append("\n--- Bestellung abgeschlossen ---\n" + summary + "\n");

            // Buttons deaktivieren
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
