package shop.visitor;

import java.util.List;
import shop.model.DigitalProduct;
import shop.model.GiftCardProduct;
import shop.model.PhysicalProduct;
import shop.model.Product;

public class ShippingVisitor implements ProductVisitor {
    private double totalShipping;

    public double calculate(List<Product> products) {
        totalShipping = 0;

        for (Product product : products) {
            product.accept(this);
        }

        return totalShipping;
    }

    @Override
    public void visit(PhysicalProduct product) {
        totalShipping += 2000;
        System.out.println(product.getName() + ": standard delivery - 2000 KZT");
    }

    @Override
    public void visit(DigitalProduct product) {
        totalShipping += 0;
    }

    @Override
    public void visit(GiftCardProduct product) {
        totalShipping += 500;
        System.out.println(product.getName() + ": envelope delivery - 500 KZT");
    }
}