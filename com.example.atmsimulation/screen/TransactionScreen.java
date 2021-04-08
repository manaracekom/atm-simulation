package screen;

import service.AccountService;

import java.util.Scanner;

public class TransactionScreen implements Screen {
    private boolean trxScreenInvalid = false;
    private AccountService accountService;

    public TransactionScreen(AccountService accountService) {
        this.accountService = accountService;
    }

    public void show() {
        do {
            System.out.println("");
            System.out.println("1. Withdraw");
            System.out.println("2. Fund Transfer");
            System.out.println("3. Exit");
            System.out.print("Please choose option[3]: ");

            Scanner in = new Scanner(System.in);
            String option = in.nextLine();

            switch (option) {
                case "1":
                    WithdrawScreen withdrawScreen = new WithdrawScreen(accountService, this);
                    withdrawScreen.show();
                    break;
                case "2":
                    FundTransferScreen fundTransferScreen = new FundTransferScreen(accountService, this);
                    fundTransferScreen.show();
                    break;
                case "3":
                case "":
                    trxScreenInvalid = false;
                    break;
                default:
                    trxScreenInvalid = true;
            }
        } while (trxScreenInvalid);
    }

    public boolean isTrxScreenInvalid() {
        return trxScreenInvalid;
    }

    public void setTrxScreenInvalid(boolean trxScreenInvalid) {
        this.trxScreenInvalid = trxScreenInvalid;
    }

    public AccountService getAccountService() {
        return accountService;
    }

    public void setAccountService(AccountService accountService) {
        this.accountService = accountService;
    }
}
