package shop.strategy;

import java.math.BigDecimal;

public class NoDiscount implements DiscountStrategy {
    @Override
    public BigDecimal applyDiscount(BigDecimal total) {
        return DiscountCalculator.validateTotal(total);
    }
}
