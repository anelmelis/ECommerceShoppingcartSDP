package shop.order;

import java.util.ArrayList;
import java.util.List;
import shop.model.Customer;
import shop.model.Product;

public class OrderBuilder {
    private Customer customer;
    private final List<Product> products = new ArrayList<>();
    private String address;
    private DeliveryType deliveryType = DeliveryType.STANDARD;
    private double shippingCost;
    private double discount;

    public OrderBuilder setCustomer(Customer customer) {
        this.customer = customer;
        return this;
    }

    public OrderBuilder addProduct(Product product) {
        products.add(product);
        return this;
    }

    public OrderBuilder setAddress(String address) {
        this.address = address;
        return this;
    }

    public OrderBuilder setDeliveryType(DeliveryType deliveryType) {
        this.deliveryType = deliveryType;
        return this;
    }

    public OrderBuilder setShippingCost(double shippingCost) {
        this.shippingCost = shippingCost;
        return this;
    }

    public OrderBuilder setDiscount(double discount) {
        this.discount = discount;
        return this;
    }

    public Order build() {
        if (customer == null) {
            throw new IllegalStateException("Customer is required.");
        }

        if (products.isEmpty()) {
            throw new IllegalStateException("Order must contain at least one product.");
        }

        if (address == null || address.isBlank()) {
            throw new IllegalStateException("Address is required.");
        }

        if (deliveryType == null) {
            throw new IllegalStateException("Delivery type is required.");
        }

        if (shippingCost < 0) {
            throw new IllegalStateException("Shipping cost cannot be negative.");
        }

        if (discount < 0) {
            throw new IllegalStateException("Discount cannot be negative.");
        }

        double subtotal = 0;

        for (Product product : products) {
            if (product == null) {
                throw new IllegalStateException("Product cannot be null.");
            }

            if (product.getPrice() < 0) {
                throw new IllegalStateException("Product price cannot be negative.");
            }

            subtotal += product.getPrice();
        }

        if (discount > subtotal) {
            throw new IllegalStateException("Discount cannot be greater than subtotal.");
        }

        double finalTotal = subtotal - discount + shippingCost;

        return new Order(
                customer,
                products,
                address,
                deliveryType,
                shippingCost,
                subtotal,
                discount,
                finalTotal
        );
    }
}