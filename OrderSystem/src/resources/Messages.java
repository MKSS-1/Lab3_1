package resources;

public interface Messages {
    // menu text
    String MENU_PROMPT = "Your choice?";
    String MENU_FINISH_TEXT = "(0) Finish order";
    String MENU_ORDER_PRODUCT_TEXT = "(1) Order product";
    String MENU_ORDER_SERVICE_TEXT = "(2) Order service";
    String INVALID_INPUT = "invalid";

    // order process
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
    // output
    String SUM_LABEL = "Sum: ";
}
