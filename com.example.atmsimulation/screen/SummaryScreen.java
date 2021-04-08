package screen;

import service.AccountService;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class SummaryScreen implements Screen {

    private int amount;
    private TransactionScreen transactionScreen;
    private AccountService accountService;

    public SummaryScreen(int amount, TransactionScreen transactionScreen, AccountService accountService) {
        this.amount = amount;
        this.transactionScreen = transactionScreen;
        this.accountService = accountService;
    }

    public void show() {
        System.out.println("");
        System.out.println("Summary");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm a");
        LocalDateTime today = LocalDateTime.now();
        System.out.println("Date: " + formatter.format(today));
        System.out.println("Withdraw: $" + amount);
        System.out.println("Balance: $" + accountService.getActiveAccount().getBalance());
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
