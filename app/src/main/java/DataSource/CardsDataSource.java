package DataSource;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;

import Account.Card;


public class CardsDataSource extends DataSource<Card> {
    private static final String Cards_CSV_PATH = "Account/cards.csv";

    public CardsDataSource() throws FileNotFoundException, IOException {
        super();
        setData(readDataFromCSV(Cards_CSV_PATH));
    }

    @Override
    protected List<Card> readDataFromCSV(String filePath) throws FileNotFoundException, IOException
    {
        List<Card> cardDataSource = new ArrayList<Card>();
        List<String[]> dataList = super.readDataFromCSV(filePath);
        String[] data;
        for (int i = 0; i<dataList.size(); i++)
        {
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

    public Card getDataById(String cardNumber) {
        return this.getData().stream().filter(data -> data.getCardNumber().equals(cardNumber)).findFirst().orElse(null);
    }

}
