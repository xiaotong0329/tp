package seedu.address.model.common;

import static java.util.Objects.requireNonNull;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Objects;

/**
 * Immutable monetary amount in SGD with scale=2. Non-negative only.
 */
public final class Money implements Comparable<Money> {

    private static final int SCALE = 2;

    private final BigDecimal amount; // always >= 0, scale=2

    private Money(BigDecimal amount) {
        requireNonNull(amount);
        if (amount.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Money cannot be negative");
        }
        this.amount = amount.setScale(SCALE, RoundingMode.UNNECESSARY);
    }

    public static Money of(BigDecimal amount) {
        return new Money(amount.setScale(SCALE, RoundingMode.HALF_UP));
    }

    public static Money parse(String value) {
        try {
            BigDecimal bd = new BigDecimal(value).setScale(SCALE, RoundingMode.HALF_UP);
            return of(bd);
        } catch (NumberFormatException ex) {
            throw new IllegalArgumentException("Invalid money amount: " + value, ex);
        }
    }

    public static Money zero() {
        return of(BigDecimal.ZERO);
    }

    public BigDecimal toBigDecimal() {
        return amount;
    }

    public Money plus(Money other) {
        requireNonNull(other);
        return of(this.amount.add(other.amount));
    }

    public Money minus(Money other) {
        requireNonNull(other);
        BigDecimal result = this.amount.subtract(other.amount);
        if (result.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Resulting money would be negative");
        }
        return of(result);
    }

    @Override
    public int compareTo(Money o) {
        return this.amount.compareTo(o.amount);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Money)) {
            return false;
        }
        Money money = (Money) o;
        return Objects.equals(amount, money.amount);
    }

    @Override
    public int hashCode() {
        return amount.hashCode();
    }

    @Override
    public String toString() {
        return amount.setScale(SCALE, RoundingMode.UNNECESSARY).toPlainString();
    }
}


