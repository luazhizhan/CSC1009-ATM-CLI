package Screen;

import Account.Account;
import Currency.Currency;
import Customer.Customer;

import java.text.NumberFormat;
import java.util.NoSuchElementException;
import java.util.Scanner;

/**
 * Manage account limits screen class
 */
public class ManageAccount implements ScreenState {
    private String prompt;

    public ManageAccount() {
        prompt = "\n" + line + "\nYour Personal Information\n" + line;
    }

    @Override
    public void printScreen(ScreenStateContext stateContext) {
        System.out.println(prompt);
    }

    /**
     * Print account info and get user inputs
     * Update account limits if the user wants to
     * 
     * @param in
     * @param customer
     * @param account
     * @return valid or invalid inputs given
     */
    public boolean getUserChoice(Scanner in, Customer customer, Account account) {
        try {
            // Print user and account information
            prompt = String.format("\nName: %s\nContact Number: %s\nEmail: %s\nResidential Address: %s\n",
                    customer.getName(),
                    customer.getPhone(), customer.getEmail(), customer.getAddress().getAddress());
            String acctCurrencyAcro = account.getCurrency().getCurrencyAcronym();

            prompt += String.format(line + "\nAccount Number: %s \n" + line
                    + "\nAvaliable Balance: %.2f %s \nHold Balance: %.2f %s \n---------------\nAccount Limits:\n---------------\n"
                    +
                    "Local Withdrawal Limit: $%.2f\nLocal Transfer Limit: $%.2f\nOverseas Withdrawal Limit: %.2f %s\nOverseas Transfer Limit: %.2f %s\n"
                    + line + "\nEdit Account Limits?\n" +
                    "\n1. Yes\n2. No",
                    account.getId(),
                    account.getAvailableBalance(), acctCurrencyAcro,
                    account.getHoldBalance(), acctCurrencyAcro,
                    account.getWithdrawLimit(), // Get ATM currency
                    account.getTransferLimit(), // Get ATM currency
                    account.getOverseasWithdrawLimit(), acctCurrencyAcro,
                    account.getOverseasTransferLimit(), acctCurrencyAcro);
            System.out.println(prompt);

            // Enter choice
            int choice = in.nextInt();

            // Throw exception if it's 1 or 2
            if (choice != 1 && choice != 2)
                throw new NoSuchElementException();

            // Ask user which account limit he/she like to change
            if (choice == 1) {
                System.out.println(String.format("\n" + line + "\nEdit Limits:\n" + line
                        + "\n1. Local Withdrawal Limit\n2. Local Transfer Limit\n" +
                        "3. Overseas Withdrawal Limit\n4. Overseas Transfer Limit\n" +
                        "5. Back to main option\n\nPlease Enter your choice:"));
                String limit;
                NumberFormat formatter = NumberFormat.getCurrencyInstance();
                switch (in.nextInt()) {
                    case 1: // Change local withdrawal limit
                        limit = String.format("Local Withdrawal Limit: %s",
                                formatter.format(account.getWithdrawLimit()));
                        System.out.println("Current " + limit + "\nPlease enter the new limit: ");
                        account.setWithdrawLimit(in.nextBigDecimal());
                        limit = String.format("Local Withdrawal Limit: %s",
                                formatter.format(account.getWithdrawLimit()));
                        break;
                    case 2: // Change local transfer limit
                        limit = String.format("Local Transfer Limit: %s",
                                formatter.format(account.getTransferLimit()));
                        System.out.println("Current " + limit + "\nPlease enter the new limit: ");
                        account.setTransferLimit(in.nextBigDecimal());
                        limit = String.format("Local Transfer Limit: %s",
                                formatter.format(account.getTransferLimit()));
                        break;
                    case 3: // Change overseas withdrawal limit
                        limit = String.format("Overseas Withdrawal Limit: %s",
                                formatter.format(account.getOverseasWithdrawLimit()));
                        System.out.println("Current " + limit + "\nPlease enter the new limit: ");
                        account.setOverseasWithdrawLimit(in.nextBigDecimal());
                        limit = String.format("Overseas Withdrawal Limit: %s",
                                formatter.format(account.getOverseasWithdrawLimit()));
                        break;
                    case 4: // Change overseas transfer limit
                        limit = String.format("Overseas Transfer Limit: %s",
                                formatter.format(account.getOverseasTransferLimit()));
                        System.out.println("Current " + limit + "\nPlease enter the new limit: ");
                        account.setOverseasTransferLimit(in.nextBigDecimal());
                        limit = String.format("Overseas Transfer Limit: %s",
                                formatter.format(account.getOverseasTransferLimit()));
                        break;
                    case 5: // Back to main
                        return true;
                    default:
                        throw new NoSuchElementException();
                }
                System.out.println("\nNew " + limit);
            }

            // Back to main option
            return true;

        } catch (IllegalArgumentException e) {
            System.out.println("\n" + line + "\n" + e.getMessage() + "\n" + line);
            return false;
        } catch (NoSuchElementException e) {
            System.out.println(ScreenState.invalidInput);
            return false;
        }
    }
}
