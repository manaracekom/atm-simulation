package screen;

import service.AccountService;

import java.util.InputMismatchException;
import java.util.Scanner;

public class FundTransferScreen implements Screen {
    private AccountService accountService;
    private TransactionScreen transactionScreen;

    public FundTransferScreen(AccountService accountService, TransactionScreen transactionScreen) {
        this.accountService = accountService;
        this.transactionScreen = transactionScreen;
    }

    public void show() {
        System.out.println("");
        System.out.print("Please enter destination account and press enter to continue or\n" +
                "press enter to go back to Transaction: ");

        Scanner in = new Scanner(System.in);
        String destinationAccount = in.nextLine();

        if (destinationAccount.isEmpty()) {
            transactionScreen.setTrxScreenInvalid(true);
            transactionScreen.show();
        }

        // validate account
        String regex = "[0-9]+";
        if (!destinationAccount.matches(regex)
                || accountService.getAccounts().stream().noneMatch(account -> account.getAccountNumber().equals(destinationAccount))) {
            System.out.println("Invalid account");
            transactionScreen.setTrxScreenInvalid(true);
            transactionScreen.show();
        }

        int transferAmount = 0;
        try {
            System.out.println("");
            System.out.print("Please enter transfer amount and\n" +
                    "press enter to continue or\n" +
                    "press enter to go back to Transaction: ");

            in = new Scanner(System.in);
            transferAmount = in.nextInt();

            if (transferAmount > 1000) {
                System.out.println("Maximum amount to withdraw is $1000");
                transactionScreen.setTrxScreenInvalid(true);
                transactionScreen.show();
            }
            if (transferAmount < 1) {
                System.out.println("Minimum amount to withdraw is $1");
                transactionScreen.setTrxScreenInvalid(true);
                transactionScreen.show();
            }
            if (transferAmount > accountService.getActiveAccount().getBalance()) {
                System.out.println("Insufficient balance $" + transferAmount);
            }
        } catch (InputMismatchException e) {
            System.out.println("Invalid amount");
            transactionScreen.setTrxScreenInvalid(true);
            transactionScreen.show();
        }

        int referenceNumber = (int) (Math.random() * 1000000);
        System.out.println("");
        System.out.println("Reference Number: " + referenceNumber);
        System.out.print("press enter to continue ");

        in = new Scanner(System.in);
        String option = in.nextLine();

        System.out.println("");
        System.out.println("Transfer Confirmation");
        System.out.println("Destination service.Account : " + destinationAccount);
        System.out.println("Transfer Amount     : $" + transferAmount);
        System.out.println("Reference Number    : " + referenceNumber);
        System.out.println("");
        System.out.println("1. Confirm Trx");
        System.out.println("2. Cancel Trx");
        System.out.print("Choose option[2]: ");

        in = new Scanner(System.in);
        option = in.nextLine();

        if (!option.isEmpty() && "1".equals(option)) {
            // Deduct balance from the user
            accountService.deduct(transferAmount);

            // Add balance to destination account
            accountService.transfer(transferAmount, destinationAccount);

            FundTransferSummaryScreen fundTransferSummaryScreen = new FundTransferSummaryScreen(accountService, transactionScreen,
                    destinationAccount, transferAmount, referenceNumber);
            fundTransferSummaryScreen.show();
        } else {
            transactionScreen.setTrxScreenInvalid(true);
            transactionScreen.show();
        }
    }
}
