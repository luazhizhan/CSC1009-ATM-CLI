package DataSource;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import Address.Address;
import Atm.Atm;

public class ATMDataSource extends DataSource<Atm> {
    private static final String ATM_CSV_PATH = "Atm/atm.csv";

    public ATMDataSource() throws FileNotFoundException, IOException {
        super();
        setData(parseCSVDataList(readDataFromCSV(ATM_CSV_PATH)));
    }

    @Override
    protected List<Atm> parseCSVDataList(List<String[]> dataList) {
        List<Atm> ATMDataSource = new ArrayList<Atm>();
        String[] data;
        /**
         * AtmID 0
         * blkNum 1
         * streetAddress 2
         * unitNumber 3
         * postalCode 4
         * city 5
         * state 6
         * country 7
         **/
        for (int i = 0; i < dataList.size(); i++) {
            data = dataList.get(i);
            Atm atm = new Atm(data[0],
                    new Address(data[1], data[2], data[3], data[4], data[5], data[6], data[7]));
            ATMDataSource.add(atm);
        }

        return ATMDataSource;
    }

    @Override
    public Atm getDataById(String id) {
        return this.getData().stream().filter(data -> data.getId().equals(id)).findFirst().orElse(null);
    }
}
