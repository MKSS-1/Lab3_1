package model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.UUID;

import static resources.Constants.FORMATTER_CENT_EURO;
import static resources.Constants.FORMATTER_SMALLER_TEN;
import static resources.Messages.CURRENCY;

public class OrderDTO {
    private UUID id;
    private List<Item> selectedItems;
    private LocalDateTime checkoutTimestamp;

    public OrderDTO(Order order) {
        this.id = order.getId();
        this.selectedItems = order.getSelectedItems();
        this.checkoutTimestamp = order.getCheckoutTimestamp();
    }

    public UUID getId() {
        return id;
    }

    public List<Item> getSelectedItems() {
        return selectedItems;
    }

    public LocalDateTime getCheckoutTimestamp() {
        return checkoutTimestamp;
    }

    public String formatPrice(int priceInCent) {
        return (priceInCent / FORMATTER_CENT_EURO) + "." + (priceInCent % FORMATTER_CENT_EURO < FORMATTER_SMALLER_TEN ? "0" : "")
                + priceInCent % FORMATTER_CENT_EURO + CURRENCY;
    }

    public int getLumpSum() {
        int sum = 0;
        for(Item item: this.selectedItems) {
            sum += item.getPrice();
        }
        return sum;
    }

    public String formatCheckoutTimestamp(LocalDateTime checkoutTimestamp) {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        return checkoutTimestamp.format(dateTimeFormatter);
    }
}
