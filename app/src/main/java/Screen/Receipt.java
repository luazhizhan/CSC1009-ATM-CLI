package Screen;
import Transaction.*;

import java.math.BigDecimal;

public class Receipt implements ScreenState
{
    private String prompt;
    Receipt()
    {
        prompt = line+"\nDo you want a printed receipt\n1.  Print Receipt\n2.  No";
    }

    Receipt(boolean choice, Transaction transaction, BigDecimal balance)
    {
        if(choice)
        {
            prompt = line + "\nDate: "+ transaction.getDateCreated();
            if(transaction instanceof CashTransaction)
            {
                prompt = "\n"+((CashTransaction) transaction).getType()+"\nAmount:  $"+transaction.getAmount();
                prompt += "\nAccount Balance"+ balance+"\nThank You For Banking With Us\n"+line;

            }
        }
    }

    Receipt(BigDecimal balance)
    {
        prompt = line+"\nAccount Balance"+ balance+"\nThank You For Banking With Us\n"+line;
    }

    @Override
    public void PrintScreen(ScreenStateContext stateContext)
    {
        System.out.println(prompt);
    }
}
