package shop.factory;

import shop.model.Product;

public abstract class ProductCreator {
    public abstract Product createProduct(String name, double price);
}