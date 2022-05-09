package automatedtellermachine;
public class Deposit extends Transaction{
    private double amount;
    private Keypad keypad;
    private DepositSlot depositSlot;
    private final static int CANCELED = 0;

    public Deposit( int userAccountNumber, Screen atmScreen, BankDatabase atmBankDatabase, Keypad atmKeypad, DepositSlot atmDepositSlot ) {
        super( userAccountNumber, atmScreen, atmBankDatabase );
        keypad = atmKeypad;
        depositSlot = atmDepositSlot;
    }

    @Override
    public void execute() {
        BankDatabase bankDatabase = getBankDatabase();
        Screen screen = getScreen();
        
        amount = promptForDepositAmount();
        if(amount != CANCELED) {
            screen.displayMessage("\nPlease insert a deposit envelope containing ");
            screen.displayDollarAmount(amount);
            screen.displayMessageLine(".");
            
            boolean envelopeReceived = depositSlot.isEnvelopeReceived();
            if(envelopeReceived) {
                screen.displayMessageLine("\nYour envelope has been " +
                                      "received.\nNOTE: The money just deposited will not " +
                                      "be available until we verify the amount of any " +
                                      "enclosed cash and your checks clear.");
                bankDatabase.credit(getAccountNumber(), amount);
            }
            else {
                screen.displayMessageLine("\nYou did not insert an " + 
                                      "envelope, so the ATM has canceled your transaction.");
            }
        }
        else {
            screen.displayMessageLine("\nCanceling transaction...");
        }
    }
    
    private double promptForDepositAmount() {
        Screen screen = getScreen(); // get reference to screen


        screen.displayMessage("Please enter a deposit amount in CENTS (or 0 to cancel):");
        int input = keypad.getInput();
        if(input == CANCELED)
            return CANCELED;
        else {
            return (double) input / 100;
        }
    }
}
