// Name: Andrew Hayes
// ID: 21321503
import org.joda.money.Money;

/**
 * Thread class to process Transactions of a Bank
 * @author andrew
 */
public class TransactionProcessor extends Thread {
    private String name;
    private Bank bank;

    // explicitly setting these values to 0 for readability, although they would default to 0 regardless
    // variables to keep a tally of how many withdrawals and deposits the object has made
    private int numWithdrawals = 0;
    private int numDeposits = 0;

    /**
     * TransactionProcessor Constructor
     * @param name
     * @param bank
     */
    public TransactionProcessor(String name, Bank bank) {
        this.name = name;
        this.bank = bank;
    }

    /**
     * Thread's run() method
     */
    public void run() {
        // loop infinitely
        while (!Thread.currentThread().isInterrupted()) {
            try {
                // sleep for a random amount of time between 0 and 1 seconds before processing the next transaction
                sleep((long) (Math.random() * 1000));

                // get transaction to process
                Transaction transaction = bank.getNextTransaction();

                // finish executing if the thread has been closed (poison pill received) or 5 seconds has elapsed without a new transaction being received
                // if the transaction is `null` this is because bank.getNextTransaction() waited for 5 seconds without receiving a new transaction
                if (transaction == null || transaction.equals(RandomTransactionGenerator.poisonPill)) {
                    break;
                }
                // otherwise, process the transaction
                else {
                    Account account = bank.getAccountByNumber(transaction.getAccountNumber());
                    float amount = transaction.getAmount();

                    // if the amount is positive, then it is a deposit
                    // made the design choice to treat a transaction of amount 0 as a deposit because it still needs to be processed and 0 is a positive number
                    if (amount >= 0) {
                        System.out.printf("%s is processing a deposit of EUR %.2f to %d\n", name, amount, transaction.getAccountNumber());
                        // only dealing in euros as specified
                        account.makeDeposit(Money.parse(String.format("EUR %.2f", amount)));    // ignoring decimals after the second place
                        numDeposits++;
                    }
                    // if the amount is negative, then it is a deposit
                    else {
                        try {
                            System.out.printf("%s is processing a withdrawal of EUR %.2f from %d\n", name, amount, transaction.getAccountNumber());
                            account.makeWithdrawal(Money.parse(String.format("EUR %.2f", -amount)));

                            // if the above line fails due to an exception, the control flow will jump to the catch block and the following line will not be executed, and the failed withdrawal will not be counted
                            numWithdrawals++;
                        }
                        catch (InsufficientFundsException e) {  // abort transaction if funds are insufficient
                            System.out.println("Transaction could not be processed due to insufficient funds");
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
                // if the thread has been interrupted, just exit loop and return
                break;
            }
        }

        // print the TransactionProcessor's name and number of deposits and withdrawals processed
        System.out.printf("%s finished processing %d transactions, including %d deposits, and %d withdrawals\n", name, numWithdrawals + numDeposits, numDeposits, numWithdrawals);
    }
}
