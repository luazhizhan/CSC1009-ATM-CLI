package Data;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import Model.Address.Address;
import Model.Atm.Atm;

/**
 * Atm data source from CSV file
 */
public class AtmData extends Data<Atm> {
    private static final String ATM_CSV_PATH = "Atm/atm.csv";

    public AtmData(CountryData countryDataSource, CurrencyData currencyDataSource)
            throws FileNotFoundException, IOException {
        super();
        setData(parseCSVDataList(readDataFromCSV(ATM_CSV_PATH), countryDataSource, currencyDataSource));
    }

    @Override
    protected List<Atm> parseCSVDataList(List<String[]> dataList) {
        return null;
    }

    private List<Atm> parseCSVDataList(List<String[]> dataList, CountryData countryDataSource,
            CurrencyData currencyDataSource) {
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

    /**
     * Get Atm by Atm id
     */
    @Override
    public Atm getDataById(String id) {
        return this.getData()
                .stream()
                .filter(data -> data.getId().equals(id))
                .findFirst().orElse(null);
    }
}
