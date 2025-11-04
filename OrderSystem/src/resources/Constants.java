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
    int WINDOW_HEIGHT = 500;

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
}
