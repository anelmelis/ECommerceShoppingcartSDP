package shop.observer;

import shop.order.OrderStatus;

public class CustomerNotifier implements OrderObserver {
    @Override
    public void update(OrderStatus status) {
        if (status == OrderStatus.CREATED) {
            System.out.println("Customer: order created");
        } else if (status == OrderStatus.PAID) {
            System.out.println("Customer: payment accepted");
        } else if (status == OrderStatus.SHIPPED) {
            System.out.println("Customer: order shipped");
        } else if (status == OrderStatus.DELIVERED) {
            System.out.println("Customer: order delivered");
        }
    }
}
