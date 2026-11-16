package shop.visitor;

import java.util.ArrayList;
import java.util.List;
import shop.model.DigitalProduct;
import shop.model.GiftCardProduct;
import shop.model.PhysicalProduct;
import shop.model.Product;

public class FulfillmentVisitor implements ProductVisitor {
    private final List<String> instructions = new ArrayList<>();

    public List<String> process(List<Product> products) {
        instructions.clear();

        for (Product product : products) {
            product.accept(this);
        }

        return List.copyOf(instructions);
    }

    @Override
    public void visit(PhysicalProduct product) {
        instructions.add(product.getName() + ": warehouse packing and courier delivery");
    }

    @Override
    public void visit(DigitalProduct product) {
        instructions.add(product.getName() + ": digital fulfillment");
    }

    @Override
    public void visit(GiftCardProduct product) {
        instructions.add(product.getName() + ": gift card activation");
    }
}
