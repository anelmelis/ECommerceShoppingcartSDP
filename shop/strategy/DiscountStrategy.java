package shop.strategy;

import java.math.BigDecimal;

public interface DiscountStrategy {
    BigDecimal applyDiscount(BigDecimal total);
}