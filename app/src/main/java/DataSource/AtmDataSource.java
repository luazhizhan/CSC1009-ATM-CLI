package DataSource;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import Address.Address;
import Atm.Atm;

public class AtmDataSource extends DataSource<Atm> {
    private static final String ATM_CSV_PATH = "Atm/atm.csv";

    public AtmDataSource(CountryDataSource countryDataSource, CurrencyDataSource currencyDataSource)
            throws FileNotFoundException, IOException {
        super();
        setData(parseCSVDataList(readDataFromCSV(ATM_CSV_PATH), countryDataSource, currencyDataSource));
    }

    @Override
    protected List<Atm> parseCSVDataList(List<String[]> dataList) {
        return null;
    }

    private List<Atm> parseCSVDataList(List<String[]> dataList, CountryDataSource countryDataSource,
            CurrencyDataSource currencyDataSource) {
        List<Atm> atmDataSource = new ArrayList<Atm>();
        String[] data;
        /**
         * AtmID 0
         * countryCode 1
         * blkNum 2
         * streetAddress 3
         * unitNumber 4
         * postalCode 5
         * city 6
         * state 7
         **/

        for (int i = 0; i < dataList.size(); i++) {
            data = dataList.get(i);
            // System.out.print(currencyDataSource.getDataById(countryDataSource.getDataById(data[1]).getCurrencyAcronym())
            // .getBanknotes().length);
            Atm atm = new Atm(data[0], countryDataSource.getDataById(data[1]),
                    currencyDataSource.getDataById(countryDataSource.getDataById(data[1]).getCurrencyAcronym()),
                    new Address(data[2], data[3], data[4], data[5], data[6], data[7],
                            countryDataSource.getDataById(data[1]).getCountryName().toUpperCase()));
            atmDataSource.add(atm);
        }

        return atmDataSource;
    }

    public List<Atm> getAtmList() {
        return this.getData();
    }

    @Override
    public Atm getDataById(String id) {
        return this.getData().stream().filter(data -> data.getId().equals(id)).findFirst().orElse(null);
    }
}
