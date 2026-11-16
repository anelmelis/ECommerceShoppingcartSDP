package shop.strategy;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Objects;

public class DiscountCalculator {
    private DiscountStrategy strategy;

    public DiscountCalculator(DiscountStrategy strategy) {
        setStrategy(strategy);
    }

    public void setStrategy(DiscountStrategy strategy) {
        this.strategy = Objects.requireNonNull(strategy);
    }

    public BigDecimal calculate(BigDecimal total) {
        return strategy.applyDiscount(validateTotal(total));
    }

    static BigDecimal validateTotal(BigDecimal total) {
        Objects.requireNonNull(total);

        if (total.signum() < 0) {
            throw new IllegalArgumentException("Total cannot be negative.");
        }

        return total.setScale(2, RoundingMode.UNNECESSARY);
    }
}