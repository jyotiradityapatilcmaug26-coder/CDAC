import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

class Transaction {

    int txId;
    LocalDate txDate;
    float txAmount;
    boolean txStatus;
    boolean txArrears;

    Transaction(int txId, LocalDate txDate, float txAmount,
                boolean txStatus, boolean txArrears) {

        this.txId = txId;
        this.txDate = txDate;
        this.txAmount = txAmount;
        this.txStatus = txStatus;
        this.txArrears = txArrears;
    }
}

public class Lambda_TransactionAmountDue {

    public static void main(String[] args) {

        // Create collection
        List<Transaction> transactions = new ArrayList<>();

        // Create 5 Transaction objects
        transactions.add(new Transaction(
                1, LocalDate.of(2026, 9, 1), 3000, true, false));

        transactions.add(new Transaction(
                2, LocalDate.of(2026, 9, 2), 7500, true, true));

        transactions.add(new Transaction(
                3, LocalDate.of(2026, 9, 3), 4500, false, true));

        transactions.add(new Transaction(
                4, LocalDate.of(2026, 9, 4), 9000, true, false));

        transactions.add(new Transaction(
                5, LocalDate.of(2026, 9, 5), 2500, false, false));

        // Lambda expression to calculate amount due
        Function<Transaction, Float> amountDue = transaction -> {

            if (transaction.txArrears) {

                return transaction.txAmount
                        + 500
                        + (18 * transaction.txAmount / 100);

            } else {

                return transaction.txAmount;
            }
        };

        // Display amount due
        for (Transaction transaction : transactions) {

            System.out.println(
                    "Transaction ID: " + transaction.txId
                    + ", Amount: " + transaction.txAmount
                    + ", Arrears: " + transaction.txArrears
                    + ", Amount Due: " + amountDue.apply(transaction)
            );
        }
    }
}