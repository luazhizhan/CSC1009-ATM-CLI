package src.main.Enrique.test;

import java.math.BigDecimal;
import java.util.Scanner;

import src.main.Enrique.ATM.Account;
import src.main.Enrique.ATM.MoneyDispenser;
import src.main.Enrique.Exceptions.InsufficientFundsException;

public class MoneyDispenserTest {

    public static void main(String[] args) {
        MoneyDispenser disp = new MoneyDispenser("SINGAPORE");

        Account account1 = new Account("John");

        boolean flag = false;

        Scanner reader = new Scanner(System.in);

        do {
            try {
                flag = false;
                System.out.println("Enter deposit amount: ");
                BigDecimal depositValue = reader.nextBigDecimal();
                account1.deposit(depositValue);

            } catch (NumberFormatException e) {
                reader.next();
                flag = true;
                System.out.println(e.getMessage());
                // e.printStackTrace();
            }
        } while (flag == true);

        do {
            try {
                flag = false;
                System.out.println("Enter deposit amount: ");
                BigDecimal withdrawValue = reader.nextBigDecimal();
                account1.withdraw(withdrawValue);

            } catch (InsufficientFundsException e) {
                reader.next();
                flag = true;
                System.out.println(e.getMessage());
                // e.printStackTrace();
            }
        } while (flag == true);

        disp.dispenseMoney(17636);
        disp.printStatus();
    }
}
