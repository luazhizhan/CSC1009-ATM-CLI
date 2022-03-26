package DataSource;

import org.junit.jupiter.api.Test;

import Atm.Atm;

import java.io.FileNotFoundException;
import java.io.IOException;
import static org.junit.jupiter.api.Assertions.*;

public class ATMDataSourceTest {
    @Test
    public void success() throws FileNotFoundException, IOException {
        DataSource<Atm> atmDataSource = new ATMDataSource();
        Atm atm = atmDataSource.getDataById("6a8145ec3be544879331c0c592e510b6");
        assertEquals(atm.getAddress().getBlkNum(), "4");
        assertEquals(atm.getAddress().getStreetAddress(), "Delta Road");
        assertEquals(atm.getAddress().getPostalCode(), "443738");
        assertEquals(atm.getAddress().getCity(), "SINGAPORE");
        assertEquals(atm.getAddress().getState(), "SINGAPORE");
        assertEquals(atm.getAddress().getCountry(), "SINGAPORE");

    }
}
