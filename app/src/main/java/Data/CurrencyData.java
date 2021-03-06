package Data;

import java.io.FileNotFoundException;
import java.io.IOException;

import java.math.BigDecimal;
import java.math.RoundingMode;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import Model.Currency.Currency;
import Model.Currency.ExchangeRate;

public class CurrencyData extends Data<Currency> {
    private static final String CURRENCY_CSV_PATH = "Currency/currency.csv";

    public CurrencyData() throws FileNotFoundException, IOException {
        super();
        setData(parseCSVDataList(readDataFromCSV(CURRENCY_CSV_PATH)));
        initialiseExchangeRates(this.getData());
    }

    @Override
    protected List<Currency> parseCSVDataList(List<String[]> dataList) {
        List<Currency> currencyData = new ArrayList<Currency>();
        String[] data;
        /**
         * currencyCode 0
         * Banknotes 1
         * Withdraw Minimum 2
         * Withdraw Maximum 3
         * Currency to USD 4
         **/

        // USD parsed first before rest, so the rest can set exchange rates to USD.
        data = dataList.get(0);
        Currency usd = new Currency(data[0], bankNoteParser(data[1]),
                Integer.parseInt(data[2]), Integer.parseInt(data[3]));
        currencyData.add(usd);

        // Intitialise currencies other than USD.
        // USD used to initialise exchange rates to USD.
        for (int i = 1; i < dataList.size(); i++) {
            data = dataList.get(i);
            Currency currency = new Currency(data[0], bankNoteParser(data[1]),
                    Integer.parseInt(data[2]), Integer.parseInt(data[3]));
            currency.setExchangeRate(new ExchangeRate(currencyData.get(0), new BigDecimal(data[4])));
            currencyData.add(currency);
        }
        return currencyData;
    }

    @Override
    public Currency getDataById(String id) {
        return this.getData().stream().filter(data -> data.getCurrencyAcronym().equals(id)).findFirst().orElse(null);
    }

    private int[] bankNoteParser(String banknoteString) throws NumberFormatException {
        String[] items = banknoteString.split(" ");

        int[] banknotes = new int[items.length];
        for (int i = 0; i < items.length; i++)
            banknotes[i] = Integer.parseInt(items[i]);
        return banknotes;
    }

    // Caching all the possible exchange rates is excessive.
    // Better to generate from available data.
    // Use exchange rates of currencies to USD to generate
    // all other exchange rates to other currencies.
    private void initialiseExchangeRates(List<Currency> data) {
        // USD always the first element for ease
        Currency usd = data.get(0);
        ExchangeRate dummyRate;
        // If a currency has an exchange rate at this stage, it is to USD.
        // Give USD the inverse exchange rate.
        for (int i = 1; i < data.size(); i++) {
            dummyRate = data.get(i).findExchangeRate(usd);
            if (dummyRate != null) {
                usd.setExchangeRate(new ExchangeRate(data.get(i),
                        BigDecimal.ONE.divide(dummyRate.getRate(), 7, RoundingMode.HALF_UP)));
            }
        }

        // For all the generated rates in USD, give other currencies new conversions
        Set<ExchangeRate> usdExchangeRates = usd.getRates();
        Iterator<ExchangeRate> usdRatesIterator = usdExchangeRates.iterator();
        while (usdRatesIterator.hasNext()) {
            // Rate to currency A.
            ExchangeRate rate = usdRatesIterator.next();
            // For each currency except USD (Currency B)
            for (int i = 1; i < data.size(); i++) {
                Currency curr = data.get(i);
                // If Currency B isn't the same as currency A,
                if (rate.getCurrency() != curr) {
                    // Find the ExchangeRate from Currency B to USD
                    ExchangeRate conversionRate = curr.findExchangeRate(usd);
                    // Not found, can't create the rate. Move on
                    if (conversionRate == null)
                        continue;
                    // Set and store the exchange rate from currency B to the USD to Currency A.
                    curr.setExchangeRate(
                            new ExchangeRate(rate.getCurrency(), rate.getRate().multiply(conversionRate.getRate())));
                }
            }
        }
    }

}
