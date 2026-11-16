package shop.adapter;

public class LegacyBankAPI {
    public static final int SUCCESS = 0;
    public static final int REJECTED = 1;

    public int makeTransaction(long amountInMinorUnits, String currency, String reference) {
        if (amountInMinorUnits <= 0 || !"KZT".equals(currency)
                || reference == null || reference.isBlank()) {
            return REJECTED;
        }

        System.out.printf("Legacy bank: %d minor units %s, order %s%n",
                amountInMinorUnits, currency, reference);

        return SUCCESS;
    }
}
