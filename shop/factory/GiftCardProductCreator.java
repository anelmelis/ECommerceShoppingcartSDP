package shop.factory;

import shop.model.GiftCardProduct;

public class GiftCardProductCreator extends ProductCreator {
    @Override
    public GiftCardProduct createProduct(String name, double price) {
        return new GiftCardProduct(name, price);
    }
}
