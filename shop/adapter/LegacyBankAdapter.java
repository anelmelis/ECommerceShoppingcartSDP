package shop.adapter;

import java.math.BigDecimal;
import java.util.Objects;

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

        long minorUnits = amount.movePointRight(2).longValueExact();

        return bank.makeTransaction(
                minorUnits,
                "KZT",
                orderId
        ) == LegacyBankAPI.SUCCESS;
    }
}