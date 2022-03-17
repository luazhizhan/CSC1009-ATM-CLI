package Screen;

class MainOption implements ScreenState{
    private String printContents;

    MainOption()
    {
        printContents = line+"\n1:  Cash Withdrawal \n2:  Cash Deposit \n3:  Bank Transfer \n4:  Manage Account \n5:  Exit \n\nPlease enter your choice: ";
    }

    MainOption(int choice)
    {
        printContents = line+"\n1:  View Account Information \n2:  View Transactions \n3:  Back \n\nPlease enter your choice: ";
    }
    @Override
    public void PrintScreen(ScreenStateContext stateContext)
    {
        System.out.println(printContents);
    }
}
