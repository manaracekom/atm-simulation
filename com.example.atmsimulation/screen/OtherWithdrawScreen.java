package screen;

import service.AccountService;

import java.util.InputMismatchException;
import java.util.Scanner;

public class OtherWithdrawScreen implements Screen {
    private TransactionScreen transactionScreen;
    private AccountService accountService;

    public OtherWithdrawScreen(AccountService accountService, TransactionScreen transactionScreen) {
        this.accountService = accountService;
        this.transactionScreen = transactionScreen;
    }

    public void show() {
        System.out.println("");
        System.out.println("Other Withdraw");
        System.out.print("Enter amount to withdraw: ");

        Scanner in = new Scanner(System.in);
        try {
            int amount = in.nextInt();

            if (amount > 1000) {
                System.out.println("Maximum amount to withdraw is $1000");
                transactionScreen.setTrxScreenInvalid(true);
                transactionScreen.show();
            } else if (amount % 10 != 0) {
                System.out.println("Invalid amount");
                transactionScreen.setTrxScreenInvalid(true);
                transactionScreen.show();
            } else if (amount > accountService.getActiveAccount().getBalance()) {
                System.out.println("Insufficient balance $" + amount);
                transactionScreen.setTrxScreenInvalid(true);
                transactionScreen.show();
            } else {
                accountService.deduct(amount);
                SummaryScreen summaryScreen = new SummaryScreen(amount, transactionScreen, accountService);
                summaryScreen.show();
            }
        } catch (InputMismatchException e) {
            System.out.println("Invalid amount");
            transactionScreen.setTrxScreenInvalid(true);
            transactionScreen.show();
        }
    }
}
