package model;

import java.time.LocalDateTime;
import java.util.*;

public class Order {
    private UUID id;
    private List<Item> selectedItems;
    private LocalDateTime checkoutTimestamp;

    public Order() {
        this.selectedItems = new ArrayList<>();
        this.id = UUID.randomUUID();
    }

    public Order(Order order) {
        this.id = order.id;
        this.selectedItems = order.selectedItems;
        this.checkoutTimestamp = order.checkoutTimestamp;
    }

    public Order(OrderDTO orderDTO) {
        this.id = orderDTO.getId();
        this.selectedItems = orderDTO.getSelectedItems();
        this.checkoutTimestamp = orderDTO.getCheckoutTimestamp();
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

    public UUID getId() {
        return id;
    }

    public void sortItemsByPriceAsc() {
        selectedItems.sort(Comparator.comparingInt(Item::getPrice));
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Order order = (Order) o;
        return id == order.id;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
