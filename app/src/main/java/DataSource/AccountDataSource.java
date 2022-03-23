package DataSource;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import Account.Account;
import Account.CurrentAccount;
import Account.SavingsAccount;
import Account.AccountStatus;

public class AccountDataSource extends DataSource<Account> {
    private static final String Account_CSV_PATH = "Account/accounts.csv";

    public AccountDataSource() throws FileNotFoundException, IOException {
        super();
    }

    @Override
    protected List<Account> readDataFromCSV() throws FileNotFoundException, IOException {
        List<Account> accountDataSource = new ArrayList<Account>();

        InputStream is = this.getClass().getClassLoader().getResourceAsStream(Account_CSV_PATH);
        try (InputStreamReader isr = new InputStreamReader(is);
                BufferedReader br = new BufferedReader(isr)) {
            br.readLine(); // Ignore first row
            String line;

            // Keep reading until last line
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                /**
                 * Account_ID - 0
                 * Customer_ID - 1
                 * Account_Name - 2
                 * Status - 3
                 * Available_Balance - 4
                 * Hold_Balance - 5
                 * Withdrawal_Limit - 6
                 * Transfer_Limit - 7
                 * Overseas_Withdraw_Limit - 8
                 * Overseas_Transfer_Limit -9
                 * Account_Type - 10
                 * Overdraft_Limit - 11
                 * Interest_Rate - 12
                 */
                AccountStatus status;
                if (data[3] == "NORMAL") {
                    status = AccountStatus.NORMAL;
                } else if (data[3] == "FROZEN") {
                    status = AccountStatus.FROZEN;
                } else {
                    status = AccountStatus.CLOSED;
                }
                Account acc;
                if (data[10] == "Current") {
                    acc = new CurrentAccount(data[0], data[1], data[2], status);
                    ((CurrentAccount) acc).setOverDraftLimit(new BigDecimal(data[11]));
                } else {
                    acc = new SavingsAccount(data[0], data[1], data[2], status);
                    ((SavingsAccount) acc).setInterestRate(new BigDecimal(data[12]));
                }
                acc.setAvailableBalance(new BigDecimal(data[4]));
                acc.setHoldBalance(new BigDecimal(data[5]));
                acc.setWithdrawLimit(new BigDecimal(data[6]));
                acc.setTransferLimit(new BigDecimal(data[7]));
                acc.setOverseasWithdrawLimit(new BigDecimal(data[8]));
                acc.setOverseasTransferLimit(new BigDecimal(data[9]));
                accountDataSource.add(acc);
            }
        }

        return accountDataSource;

    }

    public Account getDataById(String id) {
        return this.getData().stream().filter(data -> data.getId().equals(id)).findFirst().orElse(null);
    }

}
