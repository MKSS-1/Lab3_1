package resources;

public interface Messages {
    // Menu text
    String MENU_PROMPT = "Your choice?";
    String MENU_FINISH_TEXT = "(0) Finish order";
    String MENU_ORDER_PRODUCT_TEXT = "(1) Order product";
    String MENU_ORDER_SERVICE_TEXT = "(2) Order service";
    String INVALID_INPUT = "invalid";

    // Order process
    String ORDER_AGAIN_PROMPT = "Do you want to place another order? (1) Yes/(0) No";
    String ORDER_NAME_PROMPT = "Name: ";
    String ORDER_PRICE_PROMPT = "Unit price (in cents): ";
    String ORDER_QUANTITY_PROMPT = "Quantity: ";
    String SERVICE_TYPE_PROMPT = "Service type: ";
    String SERVICE_PERSONS_PROMPT = "Number of persons: ";
    String SERVICE_HOURS_PROMPT = "Hours: ";

    String CURRENCY = " EUR";
    String HOURS = "h of ";
    String PERSONS = " persons for ";

    // Output
    String SUM_LABEL = "Sum: ";

    String WINDOW_TITLE = "Order System";

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
    String MSG_PRODUCT_ADDED = "Product added: %s × %s (%s cents each)\n";
    String MSG_SERVICE_ADDED = "Service added: %s people × %sh %s\n";

    String BULLETPOINT = "• ";

    String HINT_PRODUCT_NAME = "Product name";
    String HINT_SERVICE_NAME = "Service name";
    String HINT_QUANTITY = "Quantity";
    String HINT_PRICE = "Price";
    String HINT_HOURS = "Hours";
    String HINT_PEOPLE = "Number of people";

    String ERROR_EMPTY_NAME = " cannot be empty.<br>";
    String ERROR_NOT_POSITIVE = " must be positive.<br>";

    String EQUALS = " = ";
}
