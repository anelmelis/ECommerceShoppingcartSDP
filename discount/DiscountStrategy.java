package shop.discount;

import java.math.BigDecimal;

/** Returns the order total after applying a discount. */
public interface DiscountStrategy {
    BigDecimal applyDiscount(BigDecimal total);
}
