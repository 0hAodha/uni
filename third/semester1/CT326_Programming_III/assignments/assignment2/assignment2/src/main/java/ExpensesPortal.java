// Name: Andrew Hayes
// ID Number: 21321503
import org.joda.money.CurrencyUnit;
import org.joda.money.IllegalCurrencyException;
import org.joda.money.Money;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.ArrayList;

public class ExpensesPortal {
    private ArrayList<Expense> expenses = new ArrayList<Expense>();

    /**
     * main method
     * @param args
     */
    public static void main(String[] args) {
        ExpensesPortal ep = new ExpensesPortal();

        // create sample expenses
        ep.addExpense(new Expense(LocalDate.of(2022, 9, 23), "FLight to Glasgow", ExpenseCategory.TRAVEL_AND_SUBSISTENCE, Money.parse("EUR 270.59")));
        ep.addExpense(new Expense(LocalDate.of(2022, 9, 23), "FLight to Dublin", ExpenseCategory.TRAVEL_AND_SUBSISTENCE, Money.parse("EUR 271.59")));
        ep.addExpense(new Expense(LocalDate.of(2022, 9, 20), "Dell 17-inch monitor", ExpenseCategory.EQUIPMENT, Money.parse("USD 540.00")));
        ep.addExpense(new Expense(LocalDate.of(2022, 9, 20), "Logitech Mouse", ExpenseCategory.EQUIPMENT, Money.parse("USD 40.00")));
        ep.addExpense(new Expense(LocalDate.of(2022, 9, 21), "Java for Dummies", ExpenseCategory.OTHER, Money.parse("EUR 17.99")));
        ep.addExpense(new Expense(LocalDate.of(2022, 9, 21), "Dinner", ExpenseCategory.OTHER, Money.parse("EUR 20.99")));
        ep.addExpense(new Expense(LocalDate.of(2022, 9, 21), "Whiteboard Marker", ExpenseCategory.SUPPLIES, Money.parse("EUR 4.99")));

        // call the printExpenses method using a lambda expression to implement the ExpensePrinter parameter
        ep.printExpenses(expenses -> {
            for (Expense expense : expenses) {
                System.out.println(expense);
            }
        });
        System.out.printf("%n");

        // call the printExpenses method using an anonymous inner class to implement the ExpensePrinter parameter
        ep.printExpenses(new ExpensePrinter() {
            /**
             * method to print a summary of the expenses
             * @param expenses
             */
            @Override
            public void print(ArrayList<Expense> expenses) {
                System.out.println("There are " + expenses.toArray().length + " expenses in the system totalling to a value of " + sumExpenses(expenses));
            }
        });
        System.out.printf("%n");

        // call the printExpense method using a PrinterByLabel instance as a parameter
        ep.printExpenses(new PrinterByLabel());
    }

    /**
     * method to submit a new expense
     * @param expense
     */
    public void addExpense(Expense expense) {
        expenses.add(expense);
    }

    /**
     * method to print expenses
     * @param printer
     */
    public void printExpenses(ExpensePrinter printer) {
        printer.print(expenses);
    }


    /**
     * method for summing the expenses that supports both EUR & USD
     * @param expenses
     * @return
     * @throws IllegalCurrencyException
     */
    public static Money sumExpenses(ArrayList<Expense> expenses) throws IllegalCurrencyException {
        Money total = Money.parse("EUR 0");
        BigDecimal USDToEURConversionRate = BigDecimal.valueOf(0.95);   // hardcoded conversion rate as of 2023-10-04 - clearly not ideal, but as a proof of concept

        for (Expense expense : expenses) {
            // if currency is usd, converting to euro and adding to total
            if (expense.getAmountCurrency().equals(CurrencyUnit.of("USD"))) {
                total = total.plus(expense.getAmount().convertedTo(CurrencyUnit.EUR, USDToEURConversionRate, RoundingMode.HALF_UP));
            }
            // checking that the currency is of type euro before adding to total
            else if (expense.getAmountCurrency().equals(CurrencyUnit.of("EUR"))) {
                total = total.plus(expense.getAmount());
            }
            else {
                throw new IllegalCurrencyException("CurrencyUnity " + expense.getAmountCurrency() + " is not supported! USD or EUR only");
            }
        }

        return total;
    }
}
