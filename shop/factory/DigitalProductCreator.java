package shop.factory;

import shop.model.DigitalProduct;

public class DigitalProductCreator extends ProductCreator {
    @Override
    public DigitalProduct createProduct(String name, double price) {
        return new DigitalProduct(name, price);
    }
}