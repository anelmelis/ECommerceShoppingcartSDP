package shop.adapter;

import java.math.BigDecimal;

public interface PaymentGateway {
    boolean pay(String orderId, BigDecimal amount);
}
