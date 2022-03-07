package src.main.Enrique.ATM;

import java.util.Scanner;

import src.main.Enrique.Helpers.RandHelpers;

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
        int CORRECT_PIN = RandHelpers.RandomIntInRange(100000, 999999);
        
        System.out.println("correct pin " +  CORRECT_PIN);

        Scanner keyboard = new Scanner(System.in);

        do{
            System.out.print("Enter Pin Code: ");
            PIN = keyboard.nextInt();
            attempts++;
            if (PIN != CORRECT_PIN) { 
               System.out.println("PIN is incorrect! Try again!");
            }
            else {
                System.out.println("Welcome!");
                keyboard.close();
                return 0;
            }
        }
        while(attempts < 3 && PIN != CORRECT_PIN);

        System.out.println("Too many incorrect attempts!");
        
        keyboard.close();
        return PIN;
    }

    public static void main(String[] args) {
        KeyPad test = new KeyPad();
        test.getPIN();
    }

}

