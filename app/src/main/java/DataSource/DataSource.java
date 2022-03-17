package DataSource;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

public abstract class DataSource<T> {
    private List<T> data;

    public DataSource() throws FileNotFoundException, IOException {
        setData(readDataFromCSV());
    }

    protected abstract List<T> readDataFromCSV() throws FileNotFoundException, IOException;

    protected List<T> getData() {
        return this.data;
    }

    public abstract T getDataById(String id);

    protected void setData(List<T> data) {
        this.data = data;
    }

    public void add(T object) {
        this.data.add(object);
    }
}
