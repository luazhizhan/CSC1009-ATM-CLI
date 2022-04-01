package Data;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;

import Model.Account.Card;

/**
 * Cards data source from CSV file
 */
public class CardsData extends Data<Card> {
    private static final String CARDS_CSV_PATH = "Account/cards.csv";

    public CardsData() throws FileNotFoundException, IOException {
        super();
        setData(parseCSVDataList(readDataFromCSV(CARDS_CSV_PATH)));
    }

    @Override
    protected List<Card> parseCSVDataList(List<String[]> dataList) {
        List<Card> cardDataSource = new ArrayList<Card>();
        String[] data;
        for (int i = 0; i < dataList.size(); i++) {
            /**
             * Issuing_Network - 0
             * Card_Number - 1
             * Customer_Name - 2
             * Expiry_Date - 3
             * CVV - 4
             * Account_ID - 5
             * Card_Status - 6
             * PIN_Number - 7
             */
            data = dataList.get(i);

            // Parse string status to CardStatus Enum
            Card.CardStatus status;
            if (data[6].compareTo("VALID") == 0) {
                status = Card.CardStatus.VALID;
            } else if (data[6].compareTo("CANCELLED") == 0) {
                status = Card.CardStatus.CANCELLED;
            } else {
                status = Card.CardStatus.EXPIRED;
            }

            Card card = new Card(data[1], data[2], data[5], status);
            card.setExpiryDateWithoutCheck(YearMonth.parse(data[3]));
            card.setCvv(Integer.parseInt(data[4]));
            card.setPinNumber(Integer.parseInt(data[7]));
            cardDataSource.add(card);
        }

        return cardDataSource;

    }

    /**
     * Get card by card number
     */
    public Card getDataById(String cardNumber) {
        return this.getData()
                .stream()
                .filter(data -> data.getCardNumber().equals(cardNumber))
                .findFirst().orElse(null);
    }

}
