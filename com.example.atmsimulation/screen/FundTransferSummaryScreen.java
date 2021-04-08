package screen;

import service.AccountService;

import java.util.Scanner;

public class FundTransferSummaryScreen implements Screen {
    private AccountService accountService;
    private TransactionScreen transactionScreen;
    private String destinationAccount;
    private int transferAmount;
    private int referenceNumber;

    public FundTransferSummaryScreen(AccountService accountService, TransactionScreen transactionScreen,
                                     String destinationAccount, int transferAmount, int referenceNumber) {
        this.accountService = accountService;
        this.transactionScreen = transactionScreen;
        this.destinationAccount = destinationAccount;
        this.transferAmount = transferAmount;
        this.referenceNumber = referenceNumber;
    }

    public void show() {
        System.out.println("");
        System.out.println("Fund Transfer Summary");
        System.out.println("Destination Account : " + destinationAccount);
        System.out.println("Transfer Amount     : $" + transferAmount);
        System.out.println("Reference Number    : " + referenceNumber);
        System.out.println("Balance             : $" + accountService.getActiveAccount().getBalance());
        System.out.println("");
        System.out.println("1. Transaction");
        System.out.println("2. Exit");
        System.out.print("Choose option[2]: ");

        Scanner in = new Scanner(System.in);
        String option = in.nextLine();

        if ("1".equals(option)) {
            transactionScreen.setTrxScreenInvalid(true);
            transactionScreen.show();
        } else {
            WelcomeScreen welcomeScreen = new WelcomeScreen(accountService);
            welcomeScreen.show();
        }
    }
}
