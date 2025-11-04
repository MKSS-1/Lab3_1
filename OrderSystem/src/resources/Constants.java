package resources;

import java.awt.*;

public interface Constants {
    // Menu options
    int MENU_FINISH_OPTION = 0;
    int MENU_ORDER_PRODUCT_OPTION = 1;
    int MENU_ORDER_SERVICE_OPTION = 2;
    int COSTS_PER_HOUR = 1242;
    int FORMATTER_CENT_EURO = 100;
    int FORMATTER_SMALLER_TEN = 10;
    
    // Another order options
    int YES_OPTION = 1;
    int NO_OPTION = 0;

    // Window
    String WINDOW_TITLE = "Order System";
    int WINDOW_HEIGHT = 500;
    int WINDOW_WIDTH = 800;

    // Buttons
    int BUTTON_WIDTH = 180;
    int BUTTON_HEIGHT = 35;
    Color BUTTON_BACKGROUND = new Color(70, 130, 180);
    Color BUTTON_FOREGROUND = Color.WHITE;

    // FlowLayout
    int FLOW_HORIZONTAL_GAP = 5;
    int FLOW_VERTICAL_GAP = 20;

    // TextArea
    String FONT_NAME = "Monospaced";
    int FONT_STYLE = Font.PLAIN;
    int FONT_SIZE = 14;

    // Dialog
    int DIALOG_TEXTFIELD_COLUMNS = 15;
    int DIALOG_INSET = 4;
    int DIALOG_MARGIN = 10;
    int DIALOG_VERTICAL_GAP = 10;
    Color DIALOG_HINT_COLOR = Color.RED;
    Color DIALOG_ERROR_COLOR = new Color(255, 180, 180);

    //MainPanel
    int PANEL_BORDER_SPACE = 10;
    int PANEL_HORIZONTAL_GAP = 10;
    int PANEL_VERTICAL_GAP = 10;

    // Labels
    String NEW_ORDER_BUTTON = "Start New Order";
    String ADD_PRODUCT_BUTTON = "Add Product";
    String ADD_SERVICE_BUTTON = "Add Service";
    String FINISH_BUTTON = "Complete Order";

    String OK_BUTTON = "OK";
    String CANCEL_BUTTON = "Cancel";

    String PRODUCT_DIALOG_TITLE = "Add Product";
    String SERVICE_DIALOG_TITLE = "Add Service";

    String PRODUCT_NAME_LABEL = "Product Name:";
    String PRODUCT_PRICE_LABEL = "Price (cents):";
    String PRODUCT_QUANTITY_LABEL = "Quantity:";

    String SERVICE_NAME_LABEL = "Service Name:";
    String SERVICE_PERSONS_LABEL = "Number of People:";
    String SERVICE_HOURS_LABEL = "Hours:";

    // Messages
    String MSG_NEW_ORDER_STARTED = "New order started!\n";
    String MSG_ORDER_COMPLETED = "--- Order completed ---\n";
    String MSG_PRODUCT_ADDED = "Product added: %d × %s (%d cents each)\n";
    String MSG_SERVICE_ADDED = "Service added: %d people × %dh %s\n";

    String BULLETPOINT = "⦾ ";
    
    String HINT_PRODUCT_NAME = "Product name";
    String HINT_SERVICE_NAME = "Service name";
    String HINT_QUANTITY = "Quantity";
    String HINT_PRICE = "Price";
    String HINT_HOURS = "Hours";
    String HINT_PEOPLE = "Number of people";
    
    String ERROR_EMPTY_NAME = " cannot be empty.<br>";
    String ERROR_NOT_POSITIVE = " must be positive.<br>";

    String TOTAL_SUM = "\nTotal sum: ";
    String EQUALS = " = ";
}
