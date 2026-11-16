package shop.observer;

import shop.order.OrderStatus;

public class WarehouseNotifier implements OrderObserver {
    @Override
    public void update(OrderStatus status) {
        System.out.println("Warehouse notification: Order status changed to " + status);
    }
}
