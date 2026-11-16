package shop.observer;

import shop.order.OrderStatus;

public interface OrderObserver {
    void update(OrderStatus status);
}