package src.main.java;

import java.util.Scanner;

// Get input. Primarily PIN. Sometimes used for navigation in older models without touchscreen.
public class KeyPad {
    
    // Pass a state or smth to recognise what input is expected?
    public int getInput(KeyPadInputType type) {
        // Get PIN

        switch (type) {
            case PIN:
                return getPIN();
                
            default:
                System.out.println("Invalid keypad input type detected");
                System.out.println("Or this is navigation which im ignoring for rn");
                return 0;
        }
    }

    // Ensure PIN format is localised
    // Ensure PIN matches PIN on a Card. Card must belong to a Customer.
    private int getPIN() {
        int attempts = 0;

        int PIN = 000000;

        int CORRECT_PIN = 0123;

        Scanner keyboard = new Scanner(System.in);

        do{
            System.out.print("Enter Pin Code: ");
            PIN = keyboard.nextInt();
            attempts++;
            if (PIN != CORRECT_PIN) { 
               System.out.println("PIN is incorrect! Try again!" ); // This is the 1st time the wrong password has been entered.
            }
            else {
                System.out.println("Welcome!");
                return 0;
            }
        }
        while(attempts < 3 && PIN != CORRECT_PIN);

        keyboard.close();
        return PIN;
    }

}

