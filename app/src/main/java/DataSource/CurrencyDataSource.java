package DataSource;

import java.io.FileNotFoundException;
import java.io.IOException;

import java.math.BigDecimal;
import java.math.RoundingMode;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import Currency.Currency;
import Currency.ExchangeRate;

public class CurrencyDataSource extends DataSource<Currency> {
    private static final String CURRENCY_CSV_PATH = "Currency/currency.csv";

    public CurrencyDataSource() throws FileNotFoundException, IOException {
        super();
        setData(parseCSVDataList(readDataFromCSV(CURRENCY_CSV_PATH)));
        initialiseExchangeRates(this.getData());
    }

    @Override
    protected List<Currency> parseCSVDataList(List<String[]> dataList) {
        List<Currency> currencyDataSource = new ArrayList<Currency>();
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
        Currency usd = new Currency(data[0]);
        if (data[1] != "" || data[1] != null)
            usd.setBanknotes(bankNoteParser(data[1]));
        if (data[2] != "" || data[2] != null)
            usd.setWithdrawMinimum(Integer.parseInt(data[2]));
        if (data[3] != "" || data[3] != null)
            usd.setWithdrawMaximum(Integer.parseInt(data[3]));
        currencyDataSource.add(usd);

        for (int i = 1; i < dataList.size(); i++) {
            data = dataList.get(i);
            Currency currency = new Currency(data[0]);
            if (data[1] != "" || data[1] != null)
                currency.setBanknotes(bankNoteParser(data[1]));
            if (data[2] != "" || data[2] != null)
                currency.setWithdrawMinimum(Integer.parseInt(data[2]));
            if (data[3] != "" || data[3] != null)
                currency.setWithdrawMaximum(Integer.parseInt(data[3]));
            if (data[4] != "")
                currency.setExchangeRate(new ExchangeRate(currencyDataSource.get(0), new BigDecimal(data[4])));
            currencyDataSource.add(currency);
        }
        return currencyDataSource;
    }

    @Override
    public Currency getDataById(String id) {
        return this.getData().stream().filter(data -> data.getCurrencyAcronym().equals(id)).findFirst().orElse(null);
    }

    private int[] bankNoteParser(String banknoteString) throws NumberFormatException {
        String[] items = banknoteString.split(" ");

        int[] banknotes = new int[items.length];

        for (int i = 0; i < items.length; i++) {
            try {
                banknotes[i] = Integer.parseInt(items[i]);
            } catch (NumberFormatException nfe) {
                System.out.println("CSV data may be incorrect. Please check the CSV file!");
            }
        }
        return banknotes;
    }

    // Caching all the possible exchange rates is excessive.
    // Better to generate from available data.
    private void initialiseExchangeRates(List<Currency> data) {
        // USD always the first element for ease
        Currency usd = data.get(0);
        ExchangeRate dummyRate;
        // If a currency has an exchange rate at this stage, it is to USD. Give USD the
        // inverse exchange rate.
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
            ExchangeRate rate = usdRatesIterator.next();
            // For each currency except USD
            for (int i = 1; i < data.size(); i++) {
                // DataSource currency isn't the same as the rate currency
                Currency curr = data.get(i);
                // If the current ExchangeRate does not convert into the current currency,
                if (rate.getCurrency() != curr) {
                    // Find the ExchangeRate from USD to the current currency
                    ExchangeRate conversionRate = curr.findExchangeRate(usd);
                    if (conversionRate == null)
                        continue;
                    curr.setExchangeRate(
                            new ExchangeRate(rate.getCurrency(), rate.getRate().multiply(conversionRate.getRate())));
                }
            }
        }
    }

}
