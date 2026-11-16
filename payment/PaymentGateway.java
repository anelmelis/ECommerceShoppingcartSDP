package shop.payment;

import java.math.BigDecimal;

/** Common payment interface for checkout; amounts are in KZT. */
public interface PaymentGateway {
    boolean pay(String orderId, BigDecimal amount);
}
