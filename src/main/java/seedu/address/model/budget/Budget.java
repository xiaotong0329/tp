package seedu.address.model.budget;

import static java.util.Objects.requireNonNull;

import java.time.LocalDate;
import java.util.Objects;

import seedu.address.model.common.Money;

/**
 * Global budget with inclusive date range.
 */
public final class Budget {
    private final Money amount;
    private final LocalDate startDate;
    private final LocalDate endDate;

    /**
     * Constructs a Budget.
     * @param amount non-negative SGD amount
     * @param startDate inclusive start date
     * @param endDate inclusive end date; must be on/after startDate
     */
    public Budget(Money amount, LocalDate startDate, LocalDate endDate) {
        requireNonNull(amount);
        requireNonNull(startDate);
        requireNonNull(endDate);
        if (endDate.isBefore(startDate)) {
            throw new IllegalArgumentException("End date must be on or after start date");
        }
        this.amount = amount;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    /** Returns the budgeted amount. */
    public Money getAmount() {
        return amount;
    }

    /** Returns the inclusive start date. */
    public LocalDate getStartDate() {
        return startDate;
    }

    /** Returns the inclusive end date. */
    public LocalDate getEndDate() {
        return endDate;
    }

    /** Returns true if the given date is within [startDate, endDate]. */
    public boolean contains(LocalDate date) {
        requireNonNull(date);
        return (date.isEqual(startDate) || date.isAfter(startDate))
                && (date.isEqual(endDate) || date.isBefore(endDate));
    }

    /** Returns remaining amount given total spent. Floors at zero. */
    public Money remaining(Money spent) {
        requireNonNull(spent);
        if (spent.compareTo(amount) >= 0) {
            return Money.zero();
        }
        return amount.minus(spent);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Budget)) {
            return false;
        }
        Budget budget = (Budget) o;
        return Objects.equals(amount, budget.amount)
                && Objects.equals(startDate, budget.startDate)
                && Objects.equals(endDate, budget.endDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(amount, startDate, endDate);
    }
}


