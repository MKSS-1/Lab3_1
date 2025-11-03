package util;

import static resources.Constants.*;
import static resources.Messages.*;

public class Formatter {
    private Formatter() {}

    public static String formatPrice(int priceInCent) {
        return (priceInCent / FORMATTER_CENT_EURO) + "." + (priceInCent % FORMATTER_CENT_EURO < FORMATTER_SMALLER_TEN ? "0" : "")
                + priceInCent % FORMATTER_CENT_EURO + CURRENCY;
    }
}
