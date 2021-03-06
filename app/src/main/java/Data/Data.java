package Data;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Generic Abstract Data class to DRY code for other Data classes
 */
public abstract class Data<T> {
    private List<T> data;

    public Data() throws FileNotFoundException, IOException {
    }

    /**
     * Read csv file from file path provided
     * 
     * @param filePath
     * @return An arraylist of array of csv data (2D array)
     * @throws FileNotFoundException
     * @throws IOException
     */
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

    /**
     * For subclass to override this method according to their data
     * 
     * @param dataList
     * @return
     */
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
