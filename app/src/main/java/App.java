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
 * Controls which view to be printed on the terminal
 */
public class App {
    private static Data<Transaction> txnData = null;
    private static Data<Card> cardData = null;
    private static Data<Customer> customerData = null;
    private static Data<Account> accountData = null;
    private static Data<Atm> atmData = null;
    private static Data<Country> countryData = null;
    private static Data<Currency> currencyData = null;
    private static Atm atm = null;
    private static Card card = null;
    private static Account account = null;
    private static Customer customer = null;

    public static void main(String[] args) {
        try (Scanner in = new Scanner(System.in)) {

            // Get data from CSV files
            txnData = new TransactionData();
            cardData = new CardsData();
            customerData = new CustomerData();
            countryData = new CountryData();
            currencyData = new CurrencyData();
            accountData = new AccountData((CurrencyData) currencyData);
            atmData = new AtmData((CountryData) countryData,
                    (CurrencyData) currencyData);

            ViewStateContext stateContext = new ViewStateContext();

            // Greetings
            stateContext.setAndPrint(new Greeting());

            // Select ATM
            ViewState atmList = new AtmList();
            stateContext.setAndPrint(atmList);
            while (atm == null) {
                atm = ((AtmList) atmList).selectAtm(in, atmData);
                in.nextLine(); // Clear scanner int buffer
            }

            // Enter credit/debit card
            ViewState cardPrompt = new CardPrompt();
            stateContext.setAndPrint(cardPrompt);
            while (card == null)
                card = ((CardPrompt) cardPrompt).getCardNumber(in, cardData);

            // Enter card pin
            ViewState pinPrompt = new PinPrompt();
            stateContext.setAndPrint(pinPrompt);
            while (account == null) {
                account = ((PinPrompt) pinPrompt).getPinNumber(in, card, accountData);
                in.nextLine(); // Clear scanner int buffer
            }

            // Main options view
            optionViews(stateContext, in);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * ATM's main option views will be place here
     * 
     * @param stateContext
     * @param in
     */
    public static void optionViews(ViewStateContext stateContext, Scanner in) {
        ViewState mainOption = new MainOption();
        stateContext.setAndPrint(mainOption);
        int optionNum = -1;
        while (optionNum == -1) {
            optionNum = ((MainOption) mainOption).getSelectedOption(in);
            in.nextLine(); // Clear scanner int buffer
        }

        switch (optionNum) {
            case 1: // Withdraw cash
                // Enter withdrawal amount
                ViewState withdraw = new Withdraw();
                stateContext.setAndPrint(withdraw);
                Tuple<BigDecimal, int[]> withdrawResult = null;
                while (withdrawResult == null) {
                    withdrawResult = ((Withdraw) withdraw).getWithdrawalAmount(in, atm, account, txnData);
                    in.nextLine(); // Clear scanner int buffer
                }

                // Return to main option view if it's zero
                if (withdrawResult.x.compareTo(BigDecimal.ZERO) == 0) {
                    optionViews(stateContext, in);
                }

                // Print receipt or just available balance
                ViewState withdrawReceipt = new CashTransactionReceipt(CashTransaction.TransactionType.WITHDRAW);
                stateContext.setAndPrint(withdrawReceipt);
                boolean vaildWithdrawOutput = false;
                while (vaildWithdrawOutput == false) {
                    vaildWithdrawOutput = ((CashTransactionReceipt) withdrawReceipt).getSelectedOption(in, account,
                            withdrawResult.y, withdrawResult.x);
                    in.nextLine(); // Clear scanner int buffer
                }
                optionViews(stateContext, in);
            case 2: // Deposit cash
                // Enter deposit amount
                ViewState deposit = new Deposit();
                stateContext.setAndPrint(deposit);
                Tuple<BigDecimal, int[]> depositResult = null;
                while (depositResult == null) {
                    depositResult = ((Deposit) deposit).getDepositAmt(in, atm, account, txnData);
                    in.nextLine(); // Clear scanner int buffer
                }

                // Return to main option view if it's zero
                if (depositResult.x.compareTo(BigDecimal.ZERO) == 0) {
                    optionViews(stateContext, in);
                }

                // Print receipt or just available balance
                ViewState depositReceipt = new CashTransactionReceipt(CashTransaction.TransactionType.DEPOSIT);
                stateContext.setAndPrint(depositReceipt);
                boolean vaildDepositOutput = false;
                while (vaildDepositOutput == false) {
                    vaildDepositOutput = ((CashTransactionReceipt) depositReceipt).getSelectedOption(in, account,
                            depositResult.y, depositResult.x);
                    in.nextLine(); // Clear scanner int buffer
                }
                optionViews(stateContext, in);
            case 3: // Bank Transfer

                // Enter transfer amount
                ViewState transfer = new Transfer();
                stateContext.setAndPrint(transfer);
                BigDecimal transferAmt = null;
                while (transferAmt == null) {
                    transferAmt = ((Transfer) transfer).getTransferAmt(in, account, accountData,
                            txnData);
                }

                // Return to main option view if it's zero
                if (transferAmt.compareTo(BigDecimal.ZERO) == 0) {
                    optionViews(stateContext, in);
                }

                // Print receipt or just available balance
                ViewState transferReceipt = new TransferTransactionReceipt();
                stateContext.setAndPrint(transferReceipt);
                boolean vaildTransferOutput = false;
                while (vaildTransferOutput == false) {
                    vaildTransferOutput = ((TransferTransactionReceipt) transferReceipt).getSelectedOption(in, account,
                            txnData, transferAmt);
                    in.nextLine(); // Clear scanner int buffer
                }

                optionViews(stateContext, in);
            case 4: // Transaction History
                ViewState txnHistory = new TransactionHistory();
                stateContext.setAndPrint(txnHistory);
                ((TransactionHistory) txnHistory).printTxnHistory(in, account.getId(), txnData);
                optionViews(stateContext, in);
            case 5: // Manage Account
                customer = customerData.getDataById(account.getCustomerId());
                ManageAccount accountView = new ManageAccount();
                stateContext.setAndPrint(accountView);
                boolean vaildChoice = false;
                while (vaildChoice == false) {
                    vaildChoice = accountView.getUserChoice(in, customer, account);
                    in.nextLine(); // Clear scanner int buffer
                }
                optionViews(stateContext, in);
            case 6: // Exit
                System.out.println("Exit");
                System.exit(0);
            default:
                System.out.println("Not a valid option.");
                optionViews(stateContext, in);
        }

    }
}
