package shop.discount;

import java.math.BigDecimal;
import java.math.RoundingMode;

/** VIP discount: 20 percent. */
public final class VipDiscount implements DiscountStrategy {
    @Override
    public BigDecimal applyDiscount(BigDecimal total) {
        return DiscountCalculator.validateTotal(total)
                .multiply(new BigDecimal("0.80"))
                .setScale(2, RoundingMode.HALF_UP);
    }
}
