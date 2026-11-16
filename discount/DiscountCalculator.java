package shop.discount;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Objects;

/** Strategy context; the strategy can be replaced at runtime. */
public final class DiscountCalculator {
    private DiscountStrategy strategy;

    public DiscountCalculator(DiscountStrategy strategy) {
        setStrategy(strategy);
    }

    public void setStrategy(DiscountStrategy strategy) {
        this.strategy = Objects.requireNonNull(strategy, "Discount strategy is required");
    }

    public BigDecimal calculate(BigDecimal total) {
        return strategy.applyDiscount(validateTotal(total));
    }

    static BigDecimal validateTotal(BigDecimal total) {
        Objects.requireNonNull(total, "Order total is required");
        if (total.signum() < 0) {
            throw new IllegalArgumentException("Order total must not be negative");
        }
        return total.setScale(2, RoundingMode.UNNECESSARY);
    }
}
