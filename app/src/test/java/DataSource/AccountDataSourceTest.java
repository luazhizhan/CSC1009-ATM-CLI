package DataSource;

import Account.Account;
import Account.AccountStatus;
import Account.CurrentAccount;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class AccountDataSourceTest {
    @Test
    public void success() throws FileNotFoundException, IOException {
        DataSource<Account> accountDataSource = new AccountDataSource();
        Account account  = accountDataSource.getDataById("6454856238");
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
