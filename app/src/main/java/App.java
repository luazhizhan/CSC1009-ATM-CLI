import java.io.FileNotFoundException;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.Scanner;

import Account.Account;
import Account.Card;
import Atm.Atm;
import DataSource.AccountDataSource;
import DataSource.CardsDataSource;
import DataSource.DataSource;
import DataSource.TransactionDataSource;
import Helper.Pair;
import Screen.AtmList;
import Screen.CardPrompt;
import Screen.Greeting;
import Screen.MainOption;
import Screen.PinPrompt;
import Screen.ScreenState;
import Screen.ScreenStateContext;
import Screen.Withdraw;
import Transaction.Transaction;

public class App {
    private static Atm atm = null;
    private static Card card = null;
    private static Account account = null;

    public static void main(String[] args) {
        try (Scanner in = new Scanner(System.in)) {
            DataSource<Transaction> txnDataSource = new TransactionDataSource();
            DataSource<Card> cardDataSource = new CardsDataSource();
            DataSource<Account> accountDataSource = new AccountDataSource();

            ScreenStateContext stateContext = new ScreenStateContext();

            // Greetings
            stateContext.setAndPrintScreen(new Greeting());

            // Select ATM
            ScreenState atmList = new AtmList();
            stateContext.setAndPrintScreen(atmList);
            while (atm == null) {
                atm = ((AtmList) atmList).selectAtm(in);
                in.nextLine(); // Clear scanner int buffer
            }

            // Enter credit/debit card
            ScreenState cardPrompt = new CardPrompt();
            stateContext.setAndPrintScreen(cardPrompt);
            while (card == null)
                card = ((CardPrompt) cardPrompt).getCardNumber(in, cardDataSource);

            // Enter card pin
            ScreenState pinPrompt = new PinPrompt();
            stateContext.setAndPrintScreen(pinPrompt);
            while (account == null) {
                account = ((PinPrompt) pinPrompt).getPinNumber(in, card, accountDataSource);
                in.nextLine(); // Clear scanner int buffer
            }

            optionScreens(stateContext, in);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * ATM's options screens will be place here
     * 
     * @param stateContext
     * @param in
     */
    public static void optionScreens(ScreenStateContext stateContext, Scanner in) {
        ScreenState mainOption = new MainOption();
        stateContext.setAndPrintScreen(mainOption);
        int optionNum = -1;
        while (optionNum == -1) {
            optionNum = ((MainOption) mainOption).getSelectedOption(in);
            in.nextLine(); // Clear scanner int buffer
        }

        switch (optionNum) {
            case 1:
                ScreenState withdraw = new Withdraw();
                stateContext.setAndPrintScreen(withdraw);
                Pair<Integer> notesPair = null;
                while (notesPair == null) {
                    notesPair = ((Withdraw) withdraw).getWithdrawalAmount(in, atm, account);
                    in.nextLine(); // Clear scanner int buffer
                }

                // Return to main option screen if it's zero
                if (atm.calculateNotesAmount(notesPair).compareTo(BigDecimal.ZERO) == 0) {
                    optionScreens(stateContext, in);
                }
                break;
            case 2:
                System.out.println("Not a valid option");
                optionScreens(stateContext, in);
            case 3:
                System.out.println("Not a valid option");
                optionScreens(stateContext, in);
            case 4:
                System.out.println("Not a valid option");
                optionScreens(stateContext, in);
            case 5:
                System.out.println("Exit");
                System.exit(0);
            default:
                System.out.println("Not a valid option");
                optionScreens(stateContext, in);
        }

    }
}
