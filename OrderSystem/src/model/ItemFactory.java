package model;

public interface ItemFactory {
    Item createProduct(String name, int unitPrice, int quantity);
    Item createService(String name, int persons, int hours);
}
