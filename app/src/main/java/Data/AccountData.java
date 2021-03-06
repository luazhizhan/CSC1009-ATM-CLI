package Data;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import Model.Account.Account;
import Model.Account.AccountStatus;
import Model.Account.CurrentAccount;
import Model.Account.SavingsAccount;

/**
 * Account data from CSV file
 */
public class AccountData extends Data<Account> {
    private static final String ACCOUNT_CSV_PATH = "Account/accounts.csv";

    public AccountData(CurrencyData currencyData)
            throws FileNotFoundException, IOException {
        super();
        setData(parseCSVDataList(readDataFromCSV(ACCOUNT_CSV_PATH), currencyData));
    }

    @Override
    protected List<Account> parseCSVDataList(List<String[]> dataList) {
        return null;
    }

    protected List<Account> parseCSVDataList(List<String[]> dataList, CurrencyData currencyData) {
        List<Account> accountData = new ArrayList<Account>();
        String[] data;
        for (int i = 0; i < dataList.size(); i++) {
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
             * Currency_Code - 13
             */
            data = dataList.get(i);

            // Parse string status to AccountStatusEnum
            AccountStatus status;
            if (data[3].equals("NORMAL")) {
                status = AccountStatus.NORMAL;
            } else if (data[3].equals("FROZEN")) {
                status = AccountStatus.FROZEN;
            } else {
                status = AccountStatus.CLOSED;
            }

            Account acc;
            if (data[10].equals("Current")) {
                acc = new CurrentAccount(data[0], data[1], data[2], status, currencyData.getDataById(data[13]));
                ((CurrentAccount) acc).setOverDraftLimit(new BigDecimal(data[11]));
            } else {
                acc = new SavingsAccount(data[0], data[1], data[2], status, currencyData.getDataById(data[13]));
                ((SavingsAccount) acc).setInterestRate(new BigDecimal(data[12]));
            }

            acc.setAvailableBalance(new BigDecimal(data[4]));
            acc.setHoldBalance(new BigDecimal(data[5]));
            acc.setWithdrawLimit(new BigDecimal(data[6]));
            acc.setTransferLimit(new BigDecimal(data[7]));
            acc.setOverseasWithdrawLimit(new BigDecimal(data[8]));
            acc.setOverseasTransferLimit(new BigDecimal(data[9]));
            accountData.add(acc);
        }

        return accountData;
    }

    /**
     * Get Account by Account Id
     */
    @Override
    public Account getDataById(String id) {
        return this.getData()
                .stream()
                .filter(data -> data.getId().equals(id))
                .findFirst().orElse(null);
    }

}
