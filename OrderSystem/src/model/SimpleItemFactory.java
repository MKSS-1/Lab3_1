package model;

public class SimpleItemFactory implements ItemFactory{
    @Override
    public Item createProduct(String name, int unitPrice, int quantity) {
        return new Product(name, unitPrice, quantity);
    }

    @Override
    public Item createService(String name, int persons, int hours) {
        return new Service(name, persons, hours);
    }
}
