import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

import DataSource.DataSource;
import DataSource.TransactionDataSource;
import Screen.AtmList;
import Screen.CardPrompt;
import Screen.Greeting;
import Screen.ScreenState;
import Screen.ScreenStateContext;
import Transaction.Transaction;

public class App {
    public static void main(String[] args) {
        try (Scanner in = new Scanner(System.in)) {
            DataSource<Transaction> txnDataSource = new TransactionDataSource();
            ScreenStateContext stateContext = new ScreenStateContext();

            // Greetings
            ScreenState greetings = new Greeting();
            stateContext.setAndPrintScreen(greetings);

            // Select ATM
            ScreenState atmList = new AtmList();
            stateContext.setAndPrintScreen(atmList);

            String ATM = null; // TODO change to ATM object
            while (ATM == null)
                ATM = ((AtmList) atmList).selectAtm(in);
            in.nextLine(); // Clear scanner buffer

            // Enter credit/debit card
            ScreenState cardPrompt = new CardPrompt();
            stateContext.setAndPrintScreen(cardPrompt);
            String cardNum = null;
            while (cardNum == null)
                cardNum = ((CardPrompt) cardPrompt).getCardNumber(in);

            // Uncomment when another screen gets added below
            // in.nextLine(); // Clear scanner buffer
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
