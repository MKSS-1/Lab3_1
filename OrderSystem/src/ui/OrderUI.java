package ui;

import memory.MemoryOrderRepository;
import memory.OrderRepository;
import model.*;
import resources.Constants;
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
        setTitle(Constants.WINDOW_TITLE);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        setLayout(new BorderLayout(Constants.DIALOG_MARGIN, Constants.DIALOG_VERTICAL_GAP));

        JButton btnNewOrder = createButton(Constants.NEW_ORDER_BUTTON);
        JButton btnAddProduct = createButton(Constants.ADD_PRODUCT_BUTTON);
        JButton btnAddService = createButton(Constants.ADD_SERVICE_BUTTON);
        JButton btnFinish = createButton(Constants.FINISH_BUTTON);

        btnAddProduct.setEnabled(false);
        btnAddService.setEnabled(false);
        btnFinish.setEnabled(false);

        JTextArea outputArea = new JTextArea();
        outputArea.setEditable(false);
        outputArea.setLineWrap(true);
        outputArea.setWrapStyleWord(true);
        outputArea.setFont(new Font(Constants.FONT_NAME, Constants.FONT_STYLE, Constants.FONT_SIZE));
        JScrollPane scrollPane = new JScrollPane(outputArea);

        JPanel buttonPanel = new JPanel();
        FlowLayout flowLayout = new FlowLayout(FlowLayout.CENTER, Constants.FLOW_HORIZONTAL_GAP, Constants.FLOW_VERTICAL_GAP);
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
            outputArea.setText(Constants.MSG_NEW_ORDER_STARTED);

            btnAddProduct.setEnabled(true);
            btnAddService.setEnabled(true);
            btnFinish.setEnabled(true);
            btnNewOrder.setEnabled(false);
        });

        btnAddProduct.addActionListener(e -> showAddItemDialog(true, outputArea));
        btnAddService.addActionListener(e -> showAddItemDialog(false, outputArea));

        btnFinish.addActionListener(e -> {
            String summary = orderService.finishOrderAndReturnSummary();
            outputArea.append(Constants.MSG_ORDER_COMPLETED + summary + "\n");

            btnAddProduct.setEnabled(false);
            btnAddService.setEnabled(false);
            btnFinish.setEnabled(false);
            btnNewOrder.setEnabled(true);
        });

        pack();
        Dimension size = getSize();
        size.height = Constants.WINDOW_HEIGHT;
        setSize(size);

        setLocationRelativeTo(null);
        setVisible(true);
    }

    private JButton createButton(String text) {
        JButton btn = new JButton(text);
        btn.setPreferredSize(new Dimension(Constants.BUTTON_WIDTH, Constants.BUTTON_HEIGHT));
        btn.setBackground(Constants.BUTTON_BACKGROUND);
        btn.setForeground(Constants.BUTTON_FOREGROUND);
        btn.setFocusPainted(false);
        return btn;
    }

    private void showAddItemDialog(boolean isProduct, JTextArea outputArea) {
        JDialog dialog = new JDialog(this, isProduct ? Constants.PRODUCT_DIALOG_TITLE : Constants.SERVICE_DIALOG_TITLE, true);
        dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);

        JTextField nameField = new JTextField(Constants.DIALOG_TEXTFIELD_COLUMNS);
        JTextField field2 = new JTextField(Constants.DIALOG_TEXTFIELD_COLUMNS);
        JTextField field3 = new JTextField(Constants.DIALOG_TEXTFIELD_COLUMNS);
        JLabel hintLabel = new JLabel();
        hintLabel.setForeground(Constants.DIALOG_HINT_COLOR);

        JPanel inputPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(Constants.DIALOG_INSET, Constants.DIALOG_INSET, Constants.DIALOG_INSET, Constants.DIALOG_INSET);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        String[] labels = isProduct
                ? new String[]{Constants.PRODUCT_NAME_LABEL, Constants.PRODUCT_PRICE_LABEL, Constants.PRODUCT_QUANTITY_LABEL}
                : new String[]{Constants.SERVICE_NAME_LABEL, Constants.SERVICE_PERSONS_LABEL, Constants.SERVICE_HOURS_LABEL};
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

        JButton okButton = createButton(Constants.OK_BUTTON);
        JButton cancelButton = createButton(Constants.CANCEL_BUTTON);

        Dimension buttonSize = new Dimension(Constants.BUTTON_WIDTH, Constants.BUTTON_HEIGHT);
        okButton.setPreferredSize(buttonSize);
        cancelButton.setPreferredSize(buttonSize);

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(okButton);
        buttonPanel.add(cancelButton);

        JPanel mainPanel = new JPanel(new BorderLayout(Constants.PANEL_HORIZONTAL_GAP, Constants.PANEL_VERTICAL_GAP));
        mainPanel.add(hintLabel, BorderLayout.NORTH);
        mainPanel.add(inputPanel, BorderLayout.CENTER);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);
        mainPanel.setBorder(BorderFactory.createEmptyBorder(Constants.PANEL_BORDER_SPACE, Constants.PANEL_BORDER_SPACE, Constants.PANEL_BORDER_SPACE, Constants.PANEL_BORDER_SPACE));

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
                nameField.setBackground(Constants.DIALOG_ERROR_COLOR);
                valid = false;
                sb.append(Constants.BULLETPOINT).append(isProduct ? Constants.HINT_PRODUCT_NAME : Constants.HINT_SERVICE_NAME).append(Constants.ERROR_EMPTY_NAME);
            }

            int val2 = 0, val3 = 0;
            try {
                val2 = Integer.parseInt(text2);
                if (val2 <= 0) throw new NumberFormatException();
            } catch (NumberFormatException ex) {
                field2.setBackground(Constants.DIALOG_ERROR_COLOR);
                valid = false;
                sb.append(Constants.BULLETPOINT).append(isProduct ? Constants.HINT_PRICE : Constants.HINT_PEOPLE).append(Constants.ERROR_NOT_POSITIVE);
            }

            try {
                val3 = Integer.parseInt(text3);
                if (val3 <= 0) throw new NumberFormatException();
            } catch (NumberFormatException ex) {
                field3.setBackground(Constants.DIALOG_ERROR_COLOR);
                valid = false;
                sb.append(Constants.BULLETPOINT).append(isProduct ? Constants.HINT_QUANTITY : Constants.HINT_HOURS).append(Constants.ERROR_NOT_POSITIVE);
            }

            sb.append("</html>");

            if (valid) {
                if (isProduct) {
                    orderService.addProduct(name, val2, val3);
                    outputArea.append(String.format(Constants.MSG_PRODUCT_ADDED, val3, name, val2));
                } else {
                    orderService.addService(name, val2, val3);
                    outputArea.append(String.format(Constants.MSG_SERVICE_ADDED, name, val2, val3));
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
