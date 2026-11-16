package shop.decorator;

import shop.model.Product;
import shop.visitor.ProductVisitor;

public abstract class ProductDecorator implements Product {
    protected final Product wrappedProduct;

    public ProductDecorator(Product wrappedProduct) {
        this.wrappedProduct = wrappedProduct;
    }

    @Override
    public String getName() {
        return wrappedProduct.getName();
    }

    @Override
    public double getPrice() {
        return wrappedProduct.getPrice();
    }

    @Override
    public void accept(ProductVisitor visitor) {
        wrappedProduct.accept(visitor);
    }
}