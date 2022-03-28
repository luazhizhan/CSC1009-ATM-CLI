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
import Helper.Tuple;
import Screen.AtmList;
import Screen.CardPrompt;
import Screen.Deposit;
import Screen.Greeting;
import Screen.MainOption;
import Screen.PinPrompt;
import Screen.CashTransactionReceipt;
import Screen.ScreenState;
import Screen.ScreenStateContext;
import Screen.TransactionHistory;
import Screen.Withdraw;
import Transaction.Transaction;
import Transaction.CashTransaction;

public class App {
    private static DataSource<Transaction> txnDataSource = null;
    private static DataSource<Card> cardDataSource = null;
    private static DataSource<Account> accountDataSource = null;
    private static Atm atm = null;
    private static Card card = null;
    private static Account account = null;

    public static void main(String[] args) {
        try (Scanner in = new Scanner(System.in)) {

            // Get data from CSV files
            txnDataSource = new TransactionDataSource();
            cardDataSource = new CardsDataSource();
            accountDataSource = new AccountDataSource();

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
            case 1: // Withdraw cash
                // Enter withdrawal amount
                ScreenState withdraw = new Withdraw();
                stateContext.setAndPrintScreen(withdraw);
                Tuple<BigDecimal, int[]> withdrawResult = null;
                while (withdrawResult == null) {
                    withdrawResult = ((Withdraw) withdraw).getWithdrawalAmount(in, atm, account, txnDataSource);
                    in.nextLine(); // Clear scanner int buffer
                }

                // Return to main option screen if it's zero
                if (withdrawResult.x.compareTo(BigDecimal.ZERO) == 0) {
                    optionScreens(stateContext, in);
                }

                // Print receipt or just available balance
                ScreenState withdrawReceipt = new CashTransactionReceipt(CashTransaction.TransactionType.WITHDRAW);
                stateContext.setAndPrintScreen(withdrawReceipt);
                boolean vaildWithdrawOutput = false;
                while (vaildWithdrawOutput == false) {
                    vaildWithdrawOutput = ((CashTransactionReceipt) withdrawReceipt).getSelectedOption(in, account,
                            withdrawResult.y, withdrawResult.x);
                    in.nextLine(); // Clear scanner int buffer
                }
                optionScreens(stateContext, in);
            case 2: // Deposit cash
                // Enter deposit amount
                ScreenState deposit = new Deposit();
                stateContext.setAndPrintScreen(deposit);
                Tuple<BigDecimal, int[]> depositResult = null;
                while (depositResult == null) {
                    depositResult = ((Deposit) deposit).getDepositAmt(in, atm, account, txnDataSource);
                    in.nextLine(); // Clear scanner int buffer
                }

                // Return to main option screen if it's zero
                if (depositResult.x.compareTo(BigDecimal.ZERO) == 0) {
                    optionScreens(stateContext, in);
                }

                // Print receipt or just available balance
                ScreenState depositReceipt = new CashTransactionReceipt(CashTransaction.TransactionType.DEPOSIT);
                stateContext.setAndPrintScreen(depositReceipt);
                boolean vaildDepositOutput = false;
                while (vaildDepositOutput == false) {
                    vaildDepositOutput = ((CashTransactionReceipt) depositReceipt).getSelectedOption(in, account,
                            depositResult.y, depositResult.x);
                    in.nextLine(); // Clear scanner int buffer
                }
                optionScreens(stateContext, in);
            case 3: // Bank Transfer
                System.out.println("Not a valid option.");
                optionScreens(stateContext, in);
            case 4: // Transaction History
                ScreenState txnHistory = new TransactionHistory();
                stateContext.setAndPrintScreen(txnHistory);
                ((TransactionHistory) txnHistory).printTxnHistory(in, account.getId(), txnDataSource);
                optionScreens(stateContext, in);
            case 5: // Manage Account
                System.out.println("Not a valid option.");
                optionScreens(stateContext, in);
            case 6: // Exit
                System.out.println("Exit");
                System.exit(0);
            default:
                System.out.println("Not a valid option.");
                optionScreens(stateContext, in);
        }

    }
}
