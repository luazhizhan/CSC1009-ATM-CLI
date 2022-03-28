package Screen;

import Account.Account;
import Customer.Customer;

import java.util.NoSuchElementException;
import java.util.Scanner;

public class AccountScreen implements ScreenState {
    private String prompt;

    public AccountScreen(Customer customer, Account account) {
        prompt = String.format(
                "\n" + line + "\nYour Personal Information\n" + line
                        + "\nName: %s\nContact Number: %s\nEmail: %s\nResidential Address: %s\n",
                customer.getName(),
                customer.getPhone(), customer.getEmail(), customer.getAddress().getAddress());
        prompt += String.format(line + "\nAccount Number: %s \n" + line
                + "\nAvaliable Balance: $%.2f \nHold Balance: $%.2f \n---------------\nAccount Limits:\n---------------\n"
                +
                "Local Withdrawal Limit: $%.2f\nLocal Transfer Limit: $%.2f\nOverseas Withdrawal Limit: $%.2f\nOverseas Transfer Limit: $%.2f\n"
                + line + "\nEdit Account Limits?\n" +
                "\n1. Yes\n2. No",
                account.getId(), account.getAvailableBalance(), account.getHoldBalance(), account.getWithdrawLimit(),
                account.getTransferLimit(),
                account.getOverseasWithdrawLimit(), account.getOverseasTransferLimit());
    }

    public void changeLimits(Scanner in, Account account) {
        try {
            System.out.println(String.format("\n" + line + "\nEdit Limits:\n" + line
                    + "\n1. Local Withdrawal Limit\n2. Local Transfer Limit\n3. Overseas Withdrawal Limit\n4. Overseas Transfer Limit\n\nPlease Enter your choice:"));
            String limit;
            switch (in.nextInt()) {
                case 1:
                    limit = String.format("Local Withdrawal Limit: $%.2f", account.getWithdrawLimit());
                    System.out.println("Current " + limit + "\nPlease enter the new limit: ");
                    account.setWithdrawLimit(in.nextBigDecimal());
                    limit = String.format("Local Withdrawal Limit: $%.2f", account.getWithdrawLimit());
                    break;
                case 2:
                    limit = String.format("Local Transfer Limit: $%.2f", account.getTransferLimit());
                    System.out.println("Current " + limit + "\nPlease enter the new limit: ");
                    account.setTransferLimit(in.nextBigDecimal());
                    limit = String.format("Local Transfer Limit: $%.2f", account.getTransferLimit());
                    break;
                case 3:
                    limit = String.format("Overseas Withdrawal Limit: $%.2f", account.getOverseasWithdrawLimit());
                    System.out.println("Current " + limit + "\nPlease enter the new limit: ");
                    account.setOverseasWithdrawLimit(in.nextBigDecimal());
                    limit = String.format("Overseas Withdrawal Limit: $%.2f", account.getOverseasWithdrawLimit());
                    break;
                case 4:
                    limit = String.format("Overseas Transfer Limit: $%.2f", account.getOverseasTransferLimit());
                    System.out.println("Current " + limit + "\nPlease enter the new limit: ");
                    account.setOverseasTransferLimit(in.nextBigDecimal());
                    limit = String.format("Overseas Transfer Limit: $%.2f", account.getOverseasTransferLimit());

                    break;
                default:
                    throw new IllegalStateException("Unexpected value: " + in.nextInt());
            }
            System.out.println("\nNew " + limit);

        } catch (IllegalArgumentException e) {
            System.out.println("\n" + line + "\n" + e.getMessage() + "\n" + line);
        } catch (NoSuchElementException e) {
            System.out.println(ScreenState.invalidInput);
        }
    }

    @Override
    public void printScreen(ScreenStateContext stateContext) {
        System.out.println(prompt);
    }
}
