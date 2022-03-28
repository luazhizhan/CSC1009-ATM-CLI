package DataSource;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public abstract class DataSource<T> {
    private List<T> data;

    public DataSource() throws FileNotFoundException, IOException {
    }

    protected List<String[]> readDataFromCSV(String filePath) throws FileNotFoundException, IOException {
        List<String[]> dataList = new ArrayList<String[]>();

        String row; // Contains data from each row of the CSV
        InputStream input = this.getClass().getClassLoader().getResourceAsStream(filePath);
        try (InputStreamReader isr = new InputStreamReader(input);
                BufferedReader br = new BufferedReader(isr)) {
            br.readLine(); // Ignore first row
            while ((row = br.readLine()) != null) {
                String[] rowContent = row.split(",");
                dataList.add(rowContent);
            }
        }

        return dataList;
    }

    abstract protected List<T> parseCSVDataList(List<String[]> dataList);

    protected List<T> getData() {
        return this.data;
    }

    /**
     * Get data by primary id
     * 
     * @param id Primary id
     * @return T
     */

    public abstract T getDataById(String id);

    protected void setData(List<T> data) {
        this.data = data;
    }

    public void add(T object) {
        this.data.add(object);
    }
}
