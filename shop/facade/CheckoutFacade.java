package shop.facade;

import java.util.List;
import shop.model.Customer;
import shop.model.Product;
import shop.order.DeliveryType;
import shop.order.Order;
import shop.order.OrderBuilder;
import shop.visitor.FulfillmentVisitor;
import shop.visitor.ShippingVisitor;

public class CheckoutFacade {
    public Order placeOrder(Customer customer,
                            List<Product> products,
                            String address,
                            DeliveryType deliveryType,
                            double discount) {

        System.out.println("\n--- CHECKOUT STARTED ---");

        ShippingVisitor shippingVisitor = new ShippingVisitor();
        double shippingCost = shippingVisitor.calculate(products);

        FulfillmentVisitor fulfillmentVisitor = new FulfillmentVisitor();
        List<String> instructions = fulfillmentVisitor.process(products);

        System.out.println("\nFulfillment:");
        for (String instruction : instructions) {
            System.out.println(instruction);
        }

        OrderBuilder builder = new OrderBuilder()
                .setCustomer(customer)
                .setAddress(address)
                .setDeliveryType(deliveryType)
                .setShippingCost(shippingCost)
                .setDiscount(discount);

        for (Product product : products) {
            builder.addProduct(product);
        }

        Order order = builder.build();

        System.out.println("\nOrder created successfully.");
        System.out.println("Final total: " + order.getFinalTotal() + " KZT");
        System.out.println("--- CHECKOUT COMPLETED ---");

        return order;
    }
}