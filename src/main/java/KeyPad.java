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

        int PUK = 000000;
        int PIN = 000000;

        int CORRECT_PIN = 0123;
        int CORRECT_PUK = 01234;

        Scanner keyboard = new Scanner(System.in);
        System.out.print("Enter Pin Code: ");
        while(attempts < 3) {
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



        
        if (PIN == CORRECT_PIN && attempts <= 3) {  
            System.out.println("Welcome!");
        }
        else {
            System.out.println("PIN is incorrect! Try again with PUK");
            attempts = 0;
            while(PUK != CORRECT_PUK && attempts < 3)
            {
                PUK  = keyboard.nextInt();
                attempts++;
                if (PUK != CORRECT_PUK && attempts < 3) { 
                System.out.println("PUK is incorrect! Try again!"); // This is the 1st time the wrong password has been entered.
                }
            }
            if (PUK == CORRECT_PUK && attempts <= 3) {  
                System.out.println("Welcome!");  
            }
            else
            {
            System.out.println("PUK is incorrect! SIM Blocked! See you!");
            }
        }

        keyboard.close();
        return PIN;
    }

}

