package shop.discount;

import java.math.BigDecimal;
import java.math.RoundingMode;

/** Student discount: 10 percent. */
public final class StudentDiscount implements DiscountStrategy {
    @Override
    public BigDecimal applyDiscount(BigDecimal total) {
        return DiscountCalculator.validateTotal(total)
                .multiply(new BigDecimal("0.90"))
                .setScale(2, RoundingMode.HALF_UP);
    }
}
