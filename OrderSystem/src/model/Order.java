package model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Order {
    private List<Item> selectedItems;
    private LocalDateTime checkoutTimestamp;

    public Order() {
        this.selectedItems = new ArrayList<>();
    }

    public void setCheckoutTimestamp(LocalDateTime checkoutTimestamp) {
        this.checkoutTimestamp = checkoutTimestamp;
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
}
