// Name: Andrew Hayes
// ID Number: 21321503
import java.time.LocalDate;

import org.joda.money.CurrencyUnit;
import org.joda.money.Money;

public class Expense {
    private LocalDate date;
    private String description;
    private ExpenseCategory category;
    private Money amount;
    private boolean approved;   // defaults to false

    /**
     * constructor that does not take the boolean "approved" - this should be set later
     * @param date
     * @param description
     * @param category
     * @param amount
     */
    public Expense(LocalDate date, String description, ExpenseCategory category, Money amount) {
        this.date = date;
        this.description = description;
        this.category = category;
        this.amount = amount;
    }

    /**
     * constructor that takes the boolean "approved" in case you want to override it defaulting to false
     * @param date
     * @param description
     * @param category
     * @param amount
     * @param approved
     */
    public Expense(LocalDate date, String description, ExpenseCategory category, Money amount, boolean approved) {
        this.date = date;
        this.description = description;
        this.category = category;
        this.amount = amount;
        this.approved = approved;
    }

    /**
     *
     * @return date
     */
    public LocalDate getDate() {
        return date;
    }

    /**
     *
     * @param date
     */
    public void setDate(LocalDate date) {
        this.date = date;
    }

    /**
     *
     * @return description
     */
    public String getDescription() {
        return description;
    }

    /**
     *
     * @param description
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     *
     * @return category
     */
    public ExpenseCategory getCategory() {
        return category;
    }

    /**
     *
     * @param category
     */
    public void setCategory(ExpenseCategory category) {
        this.category = category;
    }

    /**
     *
     * @return amount
     */
    public Money getAmount() {
        return amount;
    }

    /**
     *
     * @param amount
     */
    public void setAmount(Money amount) {
        this.amount = amount;
    }


    /**
     * method to return the currency unit of the amount
     * @return the CurrencyUnit of the amount
     */
    public CurrencyUnit getAmountCurrency() { return amount.getCurrencyUnit(); }

    /**
     *
     * @return isApproved
     */
    public boolean isApproved() {
        return approved;
    }

    /**
     *
     * @param approved
     */
    public void setApproved(boolean approved) {
        this.approved = approved;
    }

    @Override
    public String toString() {
        return String.format(
                "%s: %s - %s - %s %.2f", date.toString(), description, category, amount.getCurrencyUnit(), amount.getAmount().doubleValue()
        );
    }
}
