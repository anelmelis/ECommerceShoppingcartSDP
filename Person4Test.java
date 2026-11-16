package shop;

import java.math.BigDecimal;
import shop.discount.*;
import shop.payment.*;

/** Dependency-free checks; run with java -cp out shop.Person4Test. */
public final class Person4Test {
    public static void main(String[] args) {
        DiscountCalculator calculator = new DiscountCalculator(new NoDiscount());
        equal("100.00", calculator.calculate(new BigDecimal("100")));
        calculator.setStrategy(new StudentDiscount());
        equal("90.00", calculator.calculate(new BigDecimal("100")));
        equal("0.05", calculator.calculate(new BigDecimal("0.05")));
        calculator.setStrategy(new VipDiscount());
        equal("80.00", calculator.calculate(new BigDecimal("100")));
        equal("0.00", calculator.calculate(BigDecimal.ZERO));
        rejects(IllegalArgumentException.class, () -> calculator.calculate(new BigDecimal("-1")));
        rejects(NullPointerException.class, () -> calculator.setStrategy(null));

        RecordingBank bank = new RecordingBank();
        PaymentGateway gateway = new LegacyBankAdapter(bank);
        check(gateway.pay("ORDER-1", new BigDecimal("12.34")), "Successful payment");
        check(bank.amount == 1234 && "KZT".equals(bank.currency)
                && "ORDER-1".equals(bank.reference), "Legacy arguments must match exactly");
        bank.result = LegacyBankAPI.REJECTED;
        check(!gateway.pay("ORDER-2", BigDecimal.ONE), "Rejection must propagate");
        rejects(IllegalArgumentException.class, () -> gateway.pay(" ", BigDecimal.ONE));
        rejects(IllegalArgumentException.class, () -> gateway.pay("ID", BigDecimal.ZERO));
        rejects(IllegalArgumentException.class, () -> gateway.pay("ID", new BigDecimal("-1")));
        rejects(ArithmeticException.class, () -> gateway.pay("ID", new BigDecimal("1.001")));
        rejects(ArithmeticException.class, () -> gateway.pay("ID", new BigDecimal("1E30")));
        System.out.println("All Person 4 checks passed.");
    }

    private static void equal(String expected, BigDecimal actual) {
        check(new BigDecimal(expected).equals(actual), expected + " != " + actual);
    }

    private static void check(boolean condition, String message) {
        if (!condition) throw new AssertionError(message);
    }

    private static void rejects(Class<? extends Throwable> type, Runnable action) {
        try {
            action.run();
        } catch (Throwable error) {
            if (type.isInstance(error)) return;
            throw new AssertionError("Unexpected exception", error);
        }
        throw new AssertionError("Expected " + type.getSimpleName());
    }

    private static final class RecordingBank extends LegacyBankAPI {
        long amount;
        String currency;
        String reference;
        int result = SUCCESS;

        @Override
        public int makeTransaction(long amount, String currency, String reference) {
            this.amount = amount;
            this.currency = currency;
            this.reference = reference;
            return result;
        }
    }
}
