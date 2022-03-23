import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

import Account.Card;
import Atm.Atm;
import DataSource.DataSource;
import DataSource.TransactionDataSource;
import Screen.AtmList;
import Screen.CardPrompt;
import Screen.Greeting;
import Screen.PinPrompt;
import Screen.ScreenState;
import Screen.ScreenStateContext;
import Transaction.Transaction;

public class App {
    public static void main(String[] args) {
        try (Scanner in = new Scanner(System.in)) {
            DataSource<Transaction> txnDataSource = new TransactionDataSource();
            ScreenStateContext stateContext = new ScreenStateContext();

            // Greetings
            stateContext.setAndPrintScreen(new Greeting());

            // Select ATM
            ScreenState atmList = new AtmList();
            stateContext.setAndPrintScreen(atmList);
            Atm ATM = null;
            while (ATM == null) {
                ATM = ((AtmList) atmList).selectAtm(in);
                in.nextLine(); // Clear scanner buffer
            }

            // Enter credit/debit card
            Card card = new Card();
            ScreenState cardPrompt = new CardPrompt();
            stateContext.setAndPrintScreen(cardPrompt);
            boolean isValidCardNum = false;
            while (isValidCardNum == false)
                isValidCardNum = ((CardPrompt) cardPrompt).getCardNumber(in, card);

            // Enter card pin
            ScreenState pinPrompt = new PinPrompt();
            stateContext.setAndPrintScreen(pinPrompt);
            boolean isCorrectPin = false;
            while (isCorrectPin == false) {
                isCorrectPin = ((PinPrompt) pinPrompt).getPinNumber(in, card);
                in.nextLine(); // Clear scanner buffer
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
