package shop.model;

import shop.visitor.ProductVisitor;

public interface Product {
    String getName();
    double getPrice();
    void accept(ProductVisitor visitor);
}