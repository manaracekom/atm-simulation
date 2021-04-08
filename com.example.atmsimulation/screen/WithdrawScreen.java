package screen;

import service.AccountService;

import java.util.Scanner;

public class WithdrawScreen implements Screen {
    private AccountService accountService;
    private TransactionScreen transactionScreen;

    public WithdrawScreen(AccountService accountService, TransactionScreen transactionScreen) {
        this.accountService = accountService;
        this.transactionScreen = transactionScreen;
    }

    public void show() {
        System.out.println("");
        System.out.println("1. $10");
        System.out.println("2. $50");
        System.out.println("3. $100");
        System.out.println("4. Other");
        System.out.println("5. Back");
        System.out.print("Please choose option[5]: ");

        Scanner in = new Scanner(System.in);
        String option = in.nextLine();

        switch (option) {
            case "1":
                deduct(10);
                break;
            case "2":
                deduct(50);
                break;
            case "3":
                deduct(100);
                break;
            case "4":
                OtherWithdrawScreen otherWithdrawScreen = new OtherWithdrawScreen(accountService, transactionScreen);
                otherWithdrawScreen.show();
                break;
            default:
                transactionScreen.setTrxScreenInvalid(true);
                transactionScreen.show();
        }
    }

    private void deduct(int amount) {
        // Deduct balance from account
        if (amount > accountService.getActiveAccount().getBalance()) {
            System.out.println("Insufficient balance $" + accountService.getActiveAccount().getBalance());
            transactionScreen.setTrxScreenInvalid(true);
            transactionScreen.show();
        } else {
            accountService.deduct(amount);
            SummaryScreen summaryScreen = new SummaryScreen(amount, transactionScreen, accountService);
            summaryScreen.show();
        }
    }
}
