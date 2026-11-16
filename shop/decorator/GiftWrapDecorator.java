package shop.decorator;

import shop.model.Product;

public class GiftWrapDecorator extends ProductDecorator {
    private static final double WRAP_PRICE = 2000;

    public GiftWrapDecorator(Product wrappedProduct) {
        super(wrappedProduct);
    }

    @Override
    public String getName() {
        return wrappedProduct.getName() + " + Gift Wrap";
    }

    @Override
    public double getPrice() {
        return wrappedProduct.getPrice() + WRAP_PRICE;
    }
}
