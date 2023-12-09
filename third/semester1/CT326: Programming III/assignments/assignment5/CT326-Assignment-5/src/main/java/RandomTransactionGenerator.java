// Name: Andrew Hayes
// ID: 21321503
import java.util.ArrayList;

/**
 * Thread to randomly generate deposit and withdrawal transactions for random accounts
 * @author andrew
 */
public class RandomTransactionGenerator extends Thread {
    private Bank bank;
    private ArrayList<Integer> accountNumbers;
    public static final Transaction poisonPill = new Transaction(-1, 0);
    private boolean keepGenerating = true;  // flag used to stop the thread

    /**
     * Constructor for RandomTransactionGenerator objects
     * @param bank the Bank object with which the RandomTransactionGenerator is associated
     */
    public RandomTransactionGenerator(Bank bank) {
        this.bank = bank;
        accountNumbers = bank.getAccountNumbers();
    }

    /**
     * Thread's run() method
     */
    @Override
    public void run() {
        // loop until stopped
        while (keepGenerating) {
            // sleep for a random amount of time between 0 and 1 seconds
            try {
                sleep((long) (Math.random() * 1000));
            } catch (InterruptedException e) {
                // if thread is interrupted, just break out of loop
                break;
            }

            // pick account and transaction amount randomly
            int accountNumber = accountNumbers.get((int) (Math.random() * accountNumbers.size()));
            float amount = (float) ((Math.random() * 20_000) - 10_000);

            // submit random transaction
            bank.submitTransaction(new Transaction(accountNumber, amount));
        }

        // submit poison pill before exiting
        bank.submitTransaction(poisonPill);
        System.out.println("Transaction generator terminated");
    }

    /**
     * method to stop the thread
     */
    public void stopGenerating() {
        keepGenerating = false;
    }
}
