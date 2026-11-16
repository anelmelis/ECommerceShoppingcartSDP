package shop.decorator;

import shop.model.Product;

public class WarrantyDecorator extends ProductDecorator {
    private static final double WARRANTY_PRICE = 15000;

    public WarrantyDecorator(Product wrappedProduct) {
        super(wrappedProduct);
    }

    @Override
    public String getName() {
        return wrappedProduct.getName() + " + 1 Year Warranty";
    }

    @Override
    public double getPrice() {
        return wrappedProduct.getPrice() + WARRANTY_PRICE;
    }
}