package shop.order;

import java.util.ArrayList;
import java.util.List;
import shop.model.Customer;
import shop.model.Product;
import shop.observer.OrderObserver;

public class Order {
    private final Customer customer;
    private final List<Product> products;
    private final String address;
    private final DeliveryType deliveryType;
    private final double shippingCost;
    private final double subtotal;
    private final double discount;
    private final double finalTotal;
    private OrderStatus status;
    private final List<OrderObserver> observers = new ArrayList<>();

    Order(Customer customer, List<Product> products, String address,
          DeliveryType deliveryType, double shippingCost, double subtotal,
          double discount, double finalTotal) {
        this.customer = customer;
        this.products = List.copyOf(products);
        this.address = address;
        this.deliveryType = deliveryType;
        this.shippingCost = shippingCost;
        this.subtotal = subtotal;
        this.discount = discount;
        this.finalTotal = finalTotal;
        this.status = OrderStatus.CREATED;
    }

    public Customer getCustomer() {
        return customer;
    }

    public List<Product> getProducts() {
        return products;
    }

    public String getAddress() {
        return address;
    }

    public DeliveryType getDeliveryType() {
        return deliveryType;
    }

    public double getShippingCost() {
        return shippingCost;
    }

    public double getSubtotal() {
        return subtotal;
    }

    public double getDiscount() {
        return discount;
    }

    public double getFinalTotal() {
        return finalTotal;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void addObserver(OrderObserver observer) {
        observers.add(observer);
    }

    public void removeObserver(OrderObserver observer) {
        observers.remove(observer);
    }

    private void notifyObservers() {
        for (OrderObserver observer : observers) {
            observer.update(status);
        }
    }

    public void setStatus(OrderStatus newStatus) {
        if (newStatus == null) {
            throw new IllegalArgumentException("Order status cannot be null");
        }

        if (status != newStatus) {
            status = newStatus;
            notifyObservers();
        }
    }
}