package DataSource;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import Account.Account;
import Account.AccountStatus;
import Currency.Currency;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AccountDataSourceTest {
    private DataSource<Currency> currencyDataSource = null;
    private DataSource<Account> accountDataSource = null;

    @BeforeEach
    public void setUp() throws FileNotFoundException, IOException {
        currencyDataSource = new CurrencyDataSource();
        accountDataSource = new AccountDataSource((CurrencyDataSource) currencyDataSource);
    }

    @Test
    public void success() {
        Account account = accountDataSource.getDataById("6454856238");
        assertEquals(account.getCustomerId(), "3314572");
        assertEquals(account.getName(), "None");
        assertEquals(account.getStatus(), AccountStatus.NORMAL);
        assertEquals(account.getAvailableBalance(), new BigDecimal(33000));
        assertEquals(account.getHoldBalance(), new BigDecimal(37.5));
        assertEquals(account.getWithdrawLimit(), new BigDecimal(2000));
        assertEquals(account.getTransferLimit(), new BigDecimal(1000));
        assertEquals(account.getOverseasWithdrawLimit(), new BigDecimal(200));
        assertEquals(account.getOverseasTransferLimit(), new BigDecimal(1000));

    }
}
