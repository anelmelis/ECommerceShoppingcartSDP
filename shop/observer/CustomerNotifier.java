package shop.observer;

import shop.order.OrderStatus;

public class CustomerNotifier implements OrderObserver {
    @Override
    public void update(OrderStatus status) {
        System.out.println("Customer notification: Your order is " + status);
    }
}
