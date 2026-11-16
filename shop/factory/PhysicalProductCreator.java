package shop.factory;

import shop.model.PhysicalProduct;

public class PhysicalProductCreator extends ProductCreator {
    @Override
    public PhysicalProduct createProduct(String name, double price) {
        return new PhysicalProduct(name, price);
    }
}