package model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import static resources.Constants.FORMATTER_CENT_EURO;
import static resources.Constants.FORMATTER_SMALLER_TEN;
import static resources.Messages.CURRENCY;

public class Order {
    private List<Item> selectedItems;
    private LocalDateTime checkoutTimestamp;

    public Order() {
        this.selectedItems = new ArrayList<>();
    }

    public void setCheckoutTimestamp(LocalDateTime checkoutTimestamp) {
        this.checkoutTimestamp = checkoutTimestamp;
    }

    public LocalDateTime getCheckoutTimestamp() {
        return checkoutTimestamp;
    }

    public List<Item> getSelectedItems() {
        return selectedItems;
    }

    public void addSelectedItem(Item selectedItem) {
        this.selectedItems.add(selectedItem);
    }

    public int getLumpSum() {
        int sum = 0;
        for(Item item: this.selectedItems) {
            sum += item.getPrice();
        }
        return sum;
    }

    public Order(Order order) {
        this.selectedItems = order.selectedItems;
        this.checkoutTimestamp = order.checkoutTimestamp;
    }

    public void sortItemsByPriceAsc() {
        selectedItems.sort(Comparator.comparingInt(Item::getPrice));
    }

    public String formatPrice(int priceInCent) {
        return (priceInCent / FORMATTER_CENT_EURO) + "." + (priceInCent % FORMATTER_CENT_EURO < FORMATTER_SMALLER_TEN ? "0" : "")
                + priceInCent % FORMATTER_CENT_EURO + CURRENCY;
    }
}
