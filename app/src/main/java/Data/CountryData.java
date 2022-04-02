package Data;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import Model.Country.Country;

public class CountryData extends Data<Country> {
    private static final String Country_CSV_PATH = "Country/country.csv";

    public CountryData() throws FileNotFoundException, IOException {
        super();
        setData(parseCSVDataList(readDataFromCSV(Country_CSV_PATH)));
    }

    @Override
    protected List<Country> parseCSVDataList(List<String[]> dataList) {
        List<Country> countryData = new ArrayList<Country>();
        String[] data;
        /**
         * countryID 0
         * currencyID 1
         * countryName 2
         **/
        for (int i = 0; i < dataList.size(); i++) {
            data = dataList.get(i);
            Country country = new Country(data[0], data[1], data[2]);
            countryData.add(country);
        }
        return countryData;
    }

    @Override
    public Country getDataById(String id) {
        return this.getData().stream().filter(data -> data.getCountryAcronym().equals(id)).findFirst().orElse(null);
    }
}
