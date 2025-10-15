package seedu.address.storage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import seedu.address.model.budget.Budget;
import seedu.address.model.common.Money;

import java.time.LocalDate;

/**
 * Jackson-friendly version of {@link Budget}.
 */
class JsonAdaptedBudget {

    private final String amount;
    private final String startDate;
    private final String endDate;

    @JsonCreator
    public JsonAdaptedBudget(@JsonProperty("amount") String amount,
                             @JsonProperty("startDate") String startDate,
                             @JsonProperty("endDate") String endDate) {
        this.amount = amount;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public JsonAdaptedBudget(Budget source) {
        this.amount = source.getAmount().toString();
        this.startDate = source.getStartDate().toString();
        this.endDate = source.getEndDate().toString();
    }

    public Budget toModelType() {
        Money money = Money.parse(amount);
        LocalDate start = LocalDate.parse(startDate);
        LocalDate end = LocalDate.parse(endDate);
        return new Budget(money, start, end);
    }
}


