package DataSource;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import Address.Address;
import Customer.Customer;

public class CustomerDataSource extends DataSource<Customer> {
    private static final String CUSTOMER_CSV_PATH = "Customer/customer_details.csv";

    public CustomerDataSource() throws FileNotFoundException, IOException {
        super();
        setData(parseCSVDataList(readDataFromCSV(CUSTOMER_CSV_PATH)));
    }

    @Override
    protected List<Customer> parseCSVDataList(List<String[]> dataList) {
        List<Customer> customerDataSource = new ArrayList<Customer>();
        String[] data;
        /**
         * customerID 0
         * name 1
         * email 2
         * phone 3
         * blkNum 4
         * streetAddress 5
         * unitNumber 6
         * postalCode 7
         * city 8
         * state 9
         * country 10
         **/
        for (int i = 0; i < dataList.size(); i++) {
            data = dataList.get(i);
            Customer customer = new Customer(data[0], data[1], data[2], data[3],
                    new Address(data[4], data[5], data[6], data[7], data[8], data[9], data[10]));
            customerDataSource.add(customer);
        }

        return customerDataSource;

    }

    @Override
    public Customer getDataById(String id) {
        return this.getData().stream().filter(data -> data.getId().equals(id)).findFirst().orElse(null);
    }
}
