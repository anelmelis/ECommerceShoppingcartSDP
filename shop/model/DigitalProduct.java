package shop.model;
import shop.visitor.ProductVisitor;

public class DigitalProduct implements Product {
    private final String name;
    private final double price;

    public DigitalProduct(String name, double price) {
        this.name = name;
        this.price = price;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public double getPrice() {
        return price;
    }

    @Override
    public void accept(ProductVisitor visitor) {
        visitor.visit(this);
    }
}