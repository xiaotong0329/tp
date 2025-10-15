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

    public Money getAmount() {
        return amount;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public boolean contains(LocalDate date) {
        requireNonNull(date);
        return (date.isEqual(startDate) || date.isAfter(startDate))
                && (date.isEqual(endDate) || date.isBefore(endDate));
    }

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


