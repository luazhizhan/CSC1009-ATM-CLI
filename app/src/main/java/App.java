import java.io.FileNotFoundException;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.Scanner;

import Data.AccountData;
import Data.AtmData;
import Data.CardsData;
import Data.CountryData;
import Data.CurrencyData;
import Data.CustomerData;
import Data.Data;
import Data.TransactionData;
import Helper.Tuple;
import Model.Account.Account;
import Model.Account.Card;
import Model.Atm.Atm;
import Model.Country.Country;
import Model.Currency.Currency;
import Model.Customer.Customer;
import Model.Transaction.CashTransaction;
import Model.Transaction.Transaction;
import View.AtmList;
import View.CardPrompt;
import View.CashTransactionReceipt;
import View.Deposit;
import View.Greeting;
import View.MainOption;
import View.ManageAccount;
import View.PinPrompt;
import View.ViewState;
import View.ViewStateContext;
import View.TransactionHistory;
import View.Transfer;
import View.TransferTransactionReceipt;
import View.Withdraw;

/**
 * Main program.
 * Controls which screen to be printed on the terminal
 */
public class App {
    private static Data<Transaction> txnDataSource = null;
    private static Data<Card> cardDataSource = null;
    private static Data<Customer> customerDataSource = null;
    private static Data<Account> accountDataSource = null;
    private static Data<Atm> atmDataSource = null;
    private static Data<Country> countryDataSource = null;
    private static Data<Currency> currencyDataSource = null;
    private static Atm atm = null;
    private static Card card = null;
    private static Account account = null;
    private static Customer customer = null;

    public static void main(String[] args) {
        try (Scanner in = new Scanner(System.in)) {

            // Get data from CSV files
            txnDataSource = new TransactionData();
            cardDataSource = new CardsData();
            customerDataSource = new CustomerData();
            countryDataSource = new CountryData();
            currencyDataSource = new CurrencyData();
            accountDataSource = new AccountData((CurrencyData) currencyDataSource);
            atmDataSource = new AtmData((CountryData) countryDataSource,
                    (CurrencyData) currencyDataSource);

            ViewStateContext stateContext = new ViewStateContext();

            // Greetings
            stateContext.setAndPrintScreen(new Greeting());

            // Select ATM
            ViewState atmList = new AtmList();
            stateContext.setAndPrintScreen(atmList);
            while (atm == null) {
                atm = ((AtmList) atmList).selectAtm(in, atmDataSource);
                in.nextLine(); // Clear scanner int buffer
            }

            // Enter credit/debit card
            ViewState cardPrompt = new CardPrompt();
            stateContext.setAndPrintScreen(cardPrompt);
            while (card == null)
                card = ((CardPrompt) cardPrompt).getCardNumber(in, cardDataSource);

            // Enter card pin
            ViewState pinPrompt = new PinPrompt();
            stateContext.setAndPrintScreen(pinPrompt);
            while (account == null) {
                account = ((PinPrompt) pinPrompt).getPinNumber(in, card, accountDataSource);
                in.nextLine(); // Clear scanner int buffer
            }

            // Main options screen
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
     * ATM's main option screens will be place here
     * 
     * @param stateContext
     * @param in
     */
    public static void optionScreens(ViewStateContext stateContext, Scanner in) {
        ViewState mainOption = new MainOption();
        stateContext.setAndPrintScreen(mainOption);
        int optionNum = -1;
        while (optionNum == -1) {
            optionNum = ((MainOption) mainOption).getSelectedOption(in);
            in.nextLine(); // Clear scanner int buffer
        }

        switch (optionNum) {
            case 1: // Withdraw cash
                // Enter withdrawal amount
                ViewState withdraw = new Withdraw();
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
                ViewState withdrawReceipt = new CashTransactionReceipt(CashTransaction.TransactionType.WITHDRAW);
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
                ViewState deposit = new Deposit();
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
                ViewState depositReceipt = new CashTransactionReceipt(CashTransaction.TransactionType.DEPOSIT);
                stateContext.setAndPrintScreen(depositReceipt);
                boolean vaildDepositOutput = false;
                while (vaildDepositOutput == false) {
                    vaildDepositOutput = ((CashTransactionReceipt) depositReceipt).getSelectedOption(in, account,
                            depositResult.y, depositResult.x);
                    in.nextLine(); // Clear scanner int buffer
                }
                optionScreens(stateContext, in);
            case 3: // Bank Transfer

                // Enter transfer amount
                ViewState transfer = new Transfer();
                stateContext.setAndPrintScreen(transfer);
                BigDecimal transferAmt = null;
                while (transferAmt == null) {
                    transferAmt = ((Transfer) transfer).getTransferAmt(in, account, accountDataSource,
                            txnDataSource);
                }

                // Return to main option screen if it's zero
                if (transferAmt.compareTo(BigDecimal.ZERO) == 0) {
                    optionScreens(stateContext, in);
                }

                // Print receipt or just available balance
                ViewState transferReceipt = new TransferTransactionReceipt();
                stateContext.setAndPrintScreen(transferReceipt);
                boolean vaildTransferOutput = false;
                while (vaildTransferOutput == false) {
                    vaildTransferOutput = ((TransferTransactionReceipt) transferReceipt).getSelectedOption(in, account,
                            txnDataSource, transferAmt);
                    in.nextLine(); // Clear scanner int buffer
                }

                optionScreens(stateContext, in);
            case 4: // Transaction History
                ViewState txnHistory = new TransactionHistory();
                stateContext.setAndPrintScreen(txnHistory);
                ((TransactionHistory) txnHistory).printTxnHistory(in, account.getId(), txnDataSource);
                optionScreens(stateContext, in);
            case 5: // Manage Account
                customer = customerDataSource.getDataById(account.getCustomerId());
                ManageAccount accountScreen = new ManageAccount();
                stateContext.setAndPrintScreen(accountScreen);
                boolean vaildChoice = false;
                while (vaildChoice == false) {
                    vaildChoice = accountScreen.getUserChoice(in, customer, account);
                    in.nextLine(); // Clear scanner int buffer
                }
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
