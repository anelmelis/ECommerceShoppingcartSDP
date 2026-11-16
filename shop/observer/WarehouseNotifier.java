package shop.observer;

import shop.order.OrderStatus;

public class WarehouseNotifier implements OrderObserver {
    @Override
    public void update(OrderStatus status) {
        if (status == OrderStatus.CREATED) {
            System.out.println("Warehouse: new order received");
        } else if (status == OrderStatus.PAID) {
            System.out.println("Warehouse: prepare order");
        } else if (status == OrderStatus.SHIPPED) {
            System.out.println("Warehouse: order sent");
        } else if (status == OrderStatus.DELIVERED) {
            System.out.println("Warehouse: order completed");
        }
    }
}
