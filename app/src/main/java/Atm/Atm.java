package Atm;

import java.math.BigDecimal;

import Address.Address;
import Helper.Id;
import Helper.Pair;

/**
 * ATM
 */
public class Atm {
    private String id;
    private Address address;
    private int numOf10DollarsNotes; // Amount of 10 dollars notes in the ATM
    private int numOf50DollarsNotes; // Amount of 10 dollars notes in the ATM
    protected static final int DEFAULT_NUM_10_NOTES = 300;
    protected static final int DEFAULT_NUM_50_NOTES = 300;

    public Atm() {
        setId(Id.generateUUID());
        setDefaultNumOfNotes();
    }

    public Atm(String id) {
        setId(id);
        setDefaultNumOfNotes();
    }

    /**
     * For test case purposes
     * 
     * @param numOf10DollarsNotes
     * @param numOf50DollarsNotes
     */
    public Atm(int numOf10DollarsNotes, int numOf50DollarsNotes) {
        setId(Id.generateUUID());
        setNumOf10DollarsNotes(numOf10DollarsNotes);
        setNumOf50DollarsNotes(numOf50DollarsNotes);
    }

    public Atm(Address address) {
        setId(Id.generateUUID());
        setAddress(address);
        setDefaultNumOfNotes();
    }

    public Atm(String id, Address address) {
        setId(id);
        setAddress(address);
        setDefaultNumOfNotes();
    }

    private void setDefaultNumOfNotes() {
        // Total $18,000
        setNumOf10DollarsNotes(DEFAULT_NUM_10_NOTES);
        setNumOf50DollarsNotes(DEFAULT_NUM_50_NOTES);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public int getNumOf10DollarsNotes() {
        return numOf10DollarsNotes;
    }

    /**
     * Number of 10 dollars notes must be >= 0. Protected so that it can be access
     * by test cases
     * 
     * @param numOf10DollarsNotes
     */
    protected void setNumOf10DollarsNotes(int numOf10DollarsNotes) {
        if (numOf10DollarsNotes < 0) {
            throw new IllegalArgumentException("Number of notes should be >= 0.");
        }
        this.numOf10DollarsNotes = numOf10DollarsNotes;
    }

    public int getNumOf50DollarsNotes() {
        return numOf50DollarsNotes;
    }

    /**
     * Number of 50 dollars notes must be >= 0
     * Protected so that it can be access by test cases
     * 
     * @param numOf50DollarsNotes
     */
    protected void setNumOf50DollarsNotes(int numOf50DollarsNotes) {
        if (numOf50DollarsNotes < 0) {
            throw new IllegalArgumentException("Number of notes should be >= 0.");
        }
        this.numOf50DollarsNotes = numOf50DollarsNotes;
    }

    /**
     * Validate number of 10 and 50 dollars notes deposited and set the new number
     * of 10 and 50 dollars notes in the ATM
     * 
     * @param numOf10DollarsNotes
     * @param numOf50DollarsNotes
     * @return Total amount deposited
     */
    public BigDecimal deposit(int numOf10DollarsNotes, int numOf50DollarsNotes) {
        if (numOf10DollarsNotes < 0 || numOf50DollarsNotes < 0) {
            throw new IllegalArgumentException("Number of notes should be >= 0.");
        }
        if (numOf10DollarsNotes == 0 && numOf50DollarsNotes == 0) {
            throw new IllegalArgumentException("Please deposit at least one note.");
        }

        // Calculate total amount based on number of 10 and 50 dollars note
        BigDecimal totalTenAmt = new BigDecimal(10).multiply(new BigDecimal(numOf10DollarsNotes));
        BigDecimal totalFiftyAmt = new BigDecimal(50).multiply(new BigDecimal(numOf50DollarsNotes));

        // Set number of 10 and 50 dollars note
        setNumOf10DollarsNotes(getNumOf10DollarsNotes() + numOf10DollarsNotes);
        setNumOf50DollarsNotes(getNumOf50DollarsNotes() + numOf50DollarsNotes);

        // Return total amount deposited
        return totalTenAmt.add(totalFiftyAmt);
    }

    /**
     * Withdraw amount must be > 0 and be multiplier of 10
     * 
     * @param amount
     */
    public void checkWithdrawAmount(BigDecimal amount) {
        // Amount must be > 0
        if (amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Amount should be above 0.");
        }

        // Amounts must be multiplier of 10
        if (amount.remainder(new BigDecimal(10)).compareTo(BigDecimal.ZERO) != 0) {
            throw new IllegalArgumentException("Amount must be multiplier of 10.");
        }
    }

    /**
     * Calculate total value of 10 and 50 dollars notes.
     * static method so that it can be access without Atm class instantiation
     * 
     * @param notes Pair(Number of 10 dollars notes, Number of 50 dollars notes)
     * @return Total amount
     */
    public static BigDecimal calculateNotesAmount(Pair<Integer> notes) {
        // Calculate total amount based on number of 10 and 50 dollars note
        BigDecimal totalTenAmt = new BigDecimal(10).multiply(new BigDecimal(notes.first()));
        BigDecimal totalFiftyAmt = new BigDecimal(50).multiply(new BigDecimal(notes.second()));

        return totalTenAmt.add(totalFiftyAmt);
    }

    /**
     * Validate amount to be withdraw against number of 10 and 50 dollars notes
     * in the ATM.
     * Set new balance of 10 and 50 dollars notes in the ATM
     * 
     * @param amount Amount to withdraw
     * @return Pair<Integer>(numOf10DollarsNotes, numOf50DollarsNotes)
     * @throws InsufficientNotesException
     */
    public Pair<Integer> withdraw(BigDecimal amount) throws InsufficientNotesException {
        // ATM must have at least one 10 dollars or 50 dollars note
        if (getNumOf10DollarsNotes() <= 0 && getNumOf50DollarsNotes() <= 0) {
            throw new InsufficientNotesException("No notes left.");
        }

        checkWithdrawAmount(amount);

        BigDecimal fifty = new BigDecimal(50);
        BigDecimal ten = new BigDecimal(10);

        // Get remainder after dividing amount by 50
        BigDecimal remainder = amount.remainder(fifty);

        // Calculate number of 50 dollars notes to withdraw
        int numOf50Notes = 0;
        if (remainder.compareTo(BigDecimal.ZERO) == 0) {
            numOf50Notes = amount.divide(fifty).intValue();
        } else {
            numOf50Notes = amount.subtract(remainder).divide(fifty).intValue();
        }

        // Calculate remaining 50 dollar notes left for ATM
        int numOf50NotesLeft = getNumOf50DollarsNotes() - numOf50Notes;

        // Add back by 50 if there is not enough 50 dollars notes in ATM
        if (numOf50NotesLeft < 0) {
            while (numOf50NotesLeft < 0) {
                numOf50Notes--;
                numOf50NotesLeft++;
                remainder = remainder.add(fifty);
            }
        }

        // Set number of 50 dollars note in ATM if remainder amoumt == 0
        if (remainder.compareTo(BigDecimal.ZERO) == 0) {
            setNumOf50DollarsNotes(numOf50NotesLeft);
            return new Pair<Integer>(0, numOf50Notes);
        }

        // Calculate number of 10 dollars notes to withdraw
        int numOf10Notes = remainder.divide(ten).intValue();

        // Calculate remaining 10 dollar notes left for ATM
        int numOf10NotesLeft = getNumOf10DollarsNotes() - numOf10Notes;
        if (numOf10NotesLeft < 0) {
            throw new InsufficientNotesException("Insufficient notes.");
        }

        // Set number of 10 and 50 dollars note in bank
        setNumOf10DollarsNotes(numOf10NotesLeft);
        setNumOf50DollarsNotes(numOf50NotesLeft);

        return new Pair<Integer>(numOf10Notes, numOf50Notes);
    }

}
