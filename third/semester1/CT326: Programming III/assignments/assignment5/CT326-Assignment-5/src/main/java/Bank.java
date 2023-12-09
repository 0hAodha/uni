// Name: Andrew Hayes
// ID: 21321503
import org.joda.money.*;
import java.util.*;
import java.util.concurrent.*;

/**
 * Class to represent a Bank and its Accounts
 * @author andrew
 */
public class Bank {
    private static Map<Integer, Account> accounts = new HashMap<>();   // key is the account's number
    private LinkedBlockingQueue<Transaction> transactions = new LinkedBlockingQueue<>();

    /**
     * method to add an Account to the Bank
     * @param account the Account object to be added to the Bank
     */
    public void addAccount(Account account) {
        accounts.put(account.getAccountNumber(), account);
    }

    /**
     * method to return the Account identified by the supplied account number
     * @param accountNumber the number of the account to be returned
     * @return the account with the supplied accountNumber
     */
    public Account getAccountByNumber(int accountNumber) {
        return accounts.get(accountNumber);
    }

    /**
     * method to submit a Transaction to the queue to be processed
     * @param transaction the Transaction to be submitted to the queue
     */
    public void submitTransaction(Transaction transaction) {
        try {
            transactions.put(transaction);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt(); // restore interrupted status
            e.printStackTrace();                // if a transaction fails to be submitted, fail gracefully so that the transaction can be tried again later
        }
    }

    /**
     * method to get the next Transaction from the queue for processing
     * @return the next Transaction in the queue
     */
    public Transaction getNextTransaction() throws InterruptedException {
        // retrieve the head of the queue, waiting up to 5 seconds for an element to become available, otherwise returning null
        return transactions.poll(5, TimeUnit.SECONDS); // not catching InterruptedExceptions here, should be caught in the calling method
    }

    /**
     * method to print out the details of each account in the bank (account number and balance)
     */
    public static void printAccountsDetails() {
        for (Account account : accounts.values()) {
            System.out.println(account);    // using given toString() method of the Account class
        }
    }

    /**
     * method to return a collection of all the Account numbers in the Bank
     * @return a Set of all the Integer account numbers in the Bank
     */
    public ArrayList<Integer> getAccountNumbers() {
        return new ArrayList<>(accounts.keySet());
    }


    /**
     * main method of the Bank class
     * @param args No arguments used
     */
    public static void main(String[] args) throws NegativeBalanceException {
        // declare and instantiate a bank object
        Bank bank = new Bank();

        // create and add three account instances to the bank with different starting balances
        bank.addAccount(new Account(1, Money.of(CurrencyUnit.EUR, 20_000.00)));
        bank.addAccount(new Account(2, Money.of(CurrencyUnit.EUR, 25_000.00)));
        bank.addAccount(new Account(3, Money.of(CurrencyUnit.EUR, 33_333.33)));

        // declare and instantiate two TransactionProcessors
        TransactionProcessor TP1 = new TransactionProcessor("TP1", bank);
        TransactionProcessor TP2 = new TransactionProcessor("TP2", bank);

        // declare and instantiate one RandomTransactionGenerator
        RandomTransactionGenerator rtg = new RandomTransactionGenerator(bank);

        // execute the threads using a thread pool
        ScheduledExecutorService executorService = Executors.newScheduledThreadPool(4);
        executorService.execute(rtg);
        executorService.execute(TP1);
        executorService.execute(TP2);

        // shut down the transaction generator after 10 seconds
        executorService.schedule(
            () -> {
                rtg.stopGenerating();
                executorService.shutdown();
            }, 10, TimeUnit.SECONDS
        );

        // waiting until all threads have finished to print account details
        try {
            if (executorService.awaitTermination(10, TimeUnit.MINUTES)) {   // giving some arbitrarily long timeout to wait for threads to finish
                printAccountsDetails();
            }
        } catch (InterruptedException e) {
            // just printing stacktrace if exception thrown, no handling to be done
            e.printStackTrace();
        }
    }
}