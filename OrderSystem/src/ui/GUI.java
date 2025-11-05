package ui;

import memory.MemoryOrderRepository;
import memory.OrderRepository;
import model.*;
import resources.Constants;
import resources.Messages;
import service.OrderService;

import javax.swing.*;
import java.awt.*;

public class GUI extends JFrame {
    private final OrderService orderService;
    private OrderDTO orderDTO;

    public GUI(OrderService orderService) {
        this.orderService = orderService;
    }

    public void initUI() {
        setTitle(Messages.WINDOW_TITLE);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        setLayout(new BorderLayout(Constants.DIALOG_MARGIN, Constants.DIALOG_VERTICAL_GAP));

        JButton btnNewOrder = createButton(Messages.NEW_ORDER_BUTTON);
        JButton btnAddProduct = createButton(Messages.ADD_PRODUCT_BUTTON);
        JButton btnAddService = createButton(Messages.ADD_SERVICE_BUTTON);
        JButton btnFinish = createButton(Messages.FINISH_BUTTON);

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
            orderDTO = orderService.createOrder();
            outputArea.setText(Messages.MSG_NEW_ORDER_STARTED);

            btnAddProduct.setEnabled(true);
            btnAddService.setEnabled(true);
            btnFinish.setEnabled(true);
            btnNewOrder.setEnabled(false);
        });

        btnAddProduct.addActionListener(e -> showAddItemDialog(true, outputArea));
        btnAddService.addActionListener(e -> showAddItemDialog(false, outputArea));

        btnFinish.addActionListener(e -> {
            orderDTO = orderService.finishOrder(orderDTO);
            outputArea.append(Messages.MSG_ORDER_COMPLETED + finishOrderSummary());

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
        JDialog dialog = new JDialog(this, isProduct ? Messages.PRODUCT_DIALOG_TITLE : Messages.SERVICE_DIALOG_TITLE, true);
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
                ? new String[]{Messages.PRODUCT_NAME_LABEL, Messages.PRODUCT_PRICE_LABEL, Messages.PRODUCT_QUANTITY_LABEL}
                : new String[]{Messages.SERVICE_NAME_LABEL, Messages.SERVICE_PERSONS_LABEL, Messages.SERVICE_HOURS_LABEL};
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

        JButton okButton = createButton(Messages.OK_BUTTON);
        JButton cancelButton = createButton(Messages.CANCEL_BUTTON);

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
                sb.append(Messages.BULLETPOINT).append(isProduct ? Messages.HINT_PRODUCT_NAME : Messages.HINT_SERVICE_NAME).append(Messages.ERROR_EMPTY_NAME);
            }

            int val2 = 0, val3 = 0;
            try {
                val2 = Integer.parseInt(text2);
                if (val2 <= 0) throw new NumberFormatException();
            } catch (NumberFormatException ex) {
                field2.setBackground(Constants.DIALOG_ERROR_COLOR);
                valid = false;
                sb.append(Messages.BULLETPOINT).append(isProduct ? Messages.HINT_PRICE : Messages.HINT_PEOPLE).append(Messages.ERROR_NOT_POSITIVE);
            }

            try {
                val3 = Integer.parseInt(text3);
                if (val3 <= 0) throw new NumberFormatException();
            } catch (NumberFormatException ex) {
                field3.setBackground(Constants.DIALOG_ERROR_COLOR);
                valid = false;
                sb.append(Messages.BULLETPOINT).append(isProduct ? Messages.HINT_QUANTITY : Messages.HINT_HOURS).append(Messages.ERROR_NOT_POSITIVE);
            }

            sb.append("</html>");

            if (valid) {
                if (isProduct) {
                    orderDTO = orderService.orderProduct(orderDTO, name, val2, val3);
                    outputArea.append(String.format(Messages.MSG_PRODUCT_ADDED, val3, name, val2));
                } else {
                    orderDTO = orderService.orderService(orderDTO, name, val2, val3);
                    outputArea.append(String.format(Messages.MSG_SERVICE_ADDED, name, val2, val3));
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

    private String finishOrderSummary() {
        StringBuilder summary = new StringBuilder(orderDTO.formatCheckoutTimestamp(orderDTO.getCheckoutTimestamp()) + "\n");
        for (Item item : orderDTO.getSelectedItems()) {
            summary.append(item).append(Messages.EQUALS).append(orderDTO.formatPrice(item.getPrice())).append("\n");
        }

        int sum = orderDTO.getLumpSum();
        summary.append(Messages.SUM_LABEL).append(orderDTO.formatPrice(sum));
        return String.valueOf(summary);
    }
}
