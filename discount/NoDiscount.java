package shop.discount;

import java.math.BigDecimal;

public final class NoDiscount implements DiscountStrategy {
    @Override
    public BigDecimal applyDiscount(BigDecimal total) {
        return DiscountCalculator.validateTotal(total);
    }
}
