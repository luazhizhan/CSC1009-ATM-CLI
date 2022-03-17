package Screen;

import java.text.Format;
import java.text.Normalizer.Form;
import java.util.ArrayList;
import java.util.Arrays;



public class Screen {

    // Figure out how you want to store messages. Array can work, or just make a
    // bunch of named strings
    // private static Format[] printTemplates;
    private static String greeting = "Welcome! Please insert your ATM card.";
    private static String passwordPrompt = "Please enter your ATM PIN:";
    private static String loadingMessage = "Please wait...";
    private static String line = "--------------------------------------------------";

    // private static Format headerFormat = form;

    // Entries in a table. Currently static, and cannot be reordered.
    // This is built for printing accounts ONLY, with some associated information.
    //

    // Requires a header string (name of column)
    // Fixed size for column (int)
    private static ArrayList<Tuple<String, Integer>> entries = new ArrayList<Tuple<String, Integer>>(Arrays.asList(
            new Tuple<String, Integer>("#", 17),
            new Tuple<String, Integer>("ACCNUM", 15),
            new Tuple<String, Integer>("ACCNAME", 20),
            new Tuple<String, Integer>("BANKBAL", 15),
            new Tuple<String, Integer>("BOOKBAL", 15)));

    private static final int ARGS_SPACING = 2;
    // -1 = align-left, 0 = center, 1 = align-right
    private static final int ALIGN = 1;

    private static final char BAR = '|';
    private static final char DASH = '-';
    private static final char FILL = ' ';
    private static final String LINE = "----------------------------------------------------------------------";
    private static final String NEWLINE = "\n";

    private static final int COL2SPACES = 2;

    public static String fixedLengthString(int length, String string) {
        return String.format("%1$" + length + "s", string);
    }

    // 1st entry, numCol for counting (enable/disable?)

    // Output. Take in a message block.
    public void display(String message) {

    }

    // TESTING ONLY. DELETE OR MAKE A PROPER UNIT TEST.
    public static void main(String[] args) {
        String[] headers = { "#", "ACCNUM", "ACCNAME", "BANKBAL", "BOOKBAL" };

        ArrayList<Account> accounts = new ArrayList<Account>(); // array of Accounts
        Account accounts1 = new Account("John");
        Account accounts2 = new Account("Bubinga Oae");
        Account accounts3 = new Account("Zagreus");
        Account accounts4 = new Account("Lim Bo Teck");
        accounts.add(accounts1);
        accounts.add(accounts2);
        accounts.add(accounts3);
        accounts.add(accounts4);

        // for (int i = 0; i < accounts.size(); i++) {
        // System.out.println(accounts.get(i).getAccountHolder());
        // }

        // System.out.println(greeting);
        // System.out.println(passwordPrompt);

        System.out.println("accounts.size()");
        System.out.println(accounts.size());

        // Print table here
        // Make this it's own function
        String table = "";
        table += DASH * 50;
        table += NEWLINE;
        table += BAR;
        // Generate table header row
        for (int i = 0; i < headers.length; i++) {
            String toPad = headers[i];
            switch (headers[i]) {
                case "#":
                    table += fixedLengthString(entries.get(i).y, toPad);
                    // table += new String(new char[entries.get(i).y -
                    // toPad.length()]).replace('\0', fill) + toPad;
                    // table += String.format("%" + entries. + "s", "#");
                    break;
                // Replace static strings with an array somewhere
                case "ACCNUM":
                    table += fixedLengthString(entries.get(i).y, "ACCOUNT NUMBER");
                    break;
                case "ACCNAME":
                    table += fixedLengthString(entries.get(i).y, "ACCOUNT NAME");
                    break;
                case "BANKBAL":
                    table += fixedLengthString(entries.get(i).y, "BANK BALANCE");
                    break;
                case "BOOKBAL":
                    table += fixedLengthString(entries.get(i).y, "BOOK BALANCE");
                    break;
                default:
                    System.out.println("Invalid table argument.");
                    break;
            }
            table += BAR;
        }
        table += NEWLINE;
        // Populate table data
        for (int i = 0; i < accounts.size(); i++) {
            table += BAR;
            for (int j = 0; j < headers.length; j++) {
                //
                switch (j) {
                    case 0:
                        table += fixedLengthString(entries.get(j).y, Integer.toString(i));
                        // table += new String(new char[entries.get(j).y -
                        // toPad.length()]).replace('\0', fill) + toPad;
                        // table += String.format("%" + entries. + "s", "#");
                        break;
                    // Replace static strings with an array somewhere
                    case 1:
                        // Trim? Useful only i
                        table += fixedLengthString(entries.get(j).y,
                                accounts.get(i).getAccountNumber().substring(0, Math.min(entries.get(j).y, 10)));
                        break;
                    case 2:
                        table += fixedLengthString(entries.get(j).y, accounts.get(i).getAccountHolder());
                        break;
                    case 3:
                        table += fixedLengthString(entries.get(j).y,
                                Double.toString(accounts.get(i).getAvailableBalance()));
                        break;
                    case 4:
                        table += fixedLengthString(entries.get(j).y,
                                Double.toString(accounts.get(i).getTotalBalance()));
                        break;
                    default:
                        System.out.println("Invalid table argument.");
                        break;
                }
                table += BAR;
            }
            table += NEWLINE;
            table += LINE;
            table += NEWLINE;
        }

        System.out.println(table);
        Screen test = new Screen();

        test.display(greeting);
    }
}
