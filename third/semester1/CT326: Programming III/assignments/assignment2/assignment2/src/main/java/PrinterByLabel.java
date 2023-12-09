// Name: Andrew Hayes
// ID Number: 21321503
import java.util.ArrayList;
//import java.util.Collections; // for use in commented out algorithm -- see below
//import java.util.Comparator;

public class PrinterByLabel implements ExpensePrinter {
    /**
     * method to print all the expenses, grouped by category
     * @param expenses
     */
    @Override
    public void print(ArrayList<Expense> expenses) {
        ExpenseCategory[] categories = ExpenseCategory.values();

        for (ExpenseCategory category : categories) {
            System.out.println(category + ":");

            for (Expense expense : expenses) {
                // printing out expense only if it's in the current category
                if (expense.getCategory().equals(category)) {
                    System.out.println(expense);
                }
            }
            System.out.printf("%n");
        }

        // below is a commented-out alternative to the above algorithm that doesn't match the output in the design spec
        // it doesn't print out every expense category as a title, just the ones that have an existing expense in the system
        // this allows the sorting to be done nicely using the enum, and allows the enum to be expanded or contracted without changing other code
        // this allows for slightly nicer sorting and does not print out categories which do not have extant expenses in them, which may or may not be desirable

//        // sorting the ArrayList by category
//        Collections.sort(expenses, Comparator.comparing(Expense::getCategory));
//
//        // variable to keep track of what category the last printed expense was so that a heading can be printed once and only once for each category
//        ExpenseCategory lastCategory = null;
//
//        for (Expense expense : expenses) {
//            // if the current expense's category is different to the last, print it out as a heading
//            if (expense.getCategory() != lastCategory) {
//                System.out.println(expense.getCategory());
//                lastCategory = expense.getCategory();
//            }
//            System.out.println(expense);
//        }
    }
}
