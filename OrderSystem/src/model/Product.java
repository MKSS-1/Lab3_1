package model;

public class Product extends Item{
	private final int unitPrice;
	private final int quantity;

	public Product(String name, int unitPrice, int quantity) {
		super(name);
		this.unitPrice = unitPrice;
		this.quantity = quantity;
	}

    @Override
    public int getPrice() {
		return unitPrice * quantity;
	}

	@Override
	public String toString() {
		return quantity + " * " + getName();
	}
}
