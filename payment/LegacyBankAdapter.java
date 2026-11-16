package shop.payment;

import java.math.BigDecimal;
import java.util.Objects;

/** Translates PaymentGateway calls to the legacy bank's format. */
public final class LegacyBankAdapter implements PaymentGateway {
    private final LegacyBankAPI bank;

    public LegacyBankAdapter(LegacyBankAPI bank) {
        this.bank = Objects.requireNonNull(bank, "Bank API is required");
    }

    @Override
    public boolean pay(String orderId, BigDecimal amount) {
        Objects.requireNonNull(orderId, "Order ID is required");
        Objects.requireNonNull(amount, "Payment amount is required");
        if (orderId.isBlank() || amount.signum() <= 0) {
            throw new IllegalArgumentException("Order ID must be nonblank and amount positive");
        }
        // Exact conversion rejects fractional minor units and long overflow.
        long minorUnits = amount.movePointRight(2).longValueExact();
        return bank.makeTransaction(minorUnits, "KZT", orderId) == LegacyBankAPI.SUCCESS;
    }
}
