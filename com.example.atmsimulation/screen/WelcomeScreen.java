package screen;

import service.Account;
import service.AccountService;

import java.util.Optional;
import java.util.Scanner;

public class WelcomeScreen implements Screen {
    private boolean welcomeScreenInvalid = false;
    private AccountService accountService;

    public WelcomeScreen(AccountService accountService) {
        this.accountService = accountService;
    }

    public void show() {
        do {
            Scanner in = new Scanner(System.in);

            String accountNumber;
            String pin;

            // Input account number
            System.out.println("");
            System.out.print("Enter account number: ");
            accountNumber = in.nextLine();

            // Validate account number should have 6 digits length
            if (accountNumber.length() > 6) {
                System.out.println("Account Number should have 6 digits length");
                welcomeScreenInvalid = true;
                continue;
            }

            // Validate account number should only contains number[0-9]
            String regex = "[0-9]+";
            if (!accountNumber.matches(regex)) {
                System.out.println("Account Number should only contains numbers");
                welcomeScreenInvalid = true;
                continue;
            }

            // Input PIN
            System.out.print("Enter PIN: ");
            pin = in.nextLine();

            // Validate pin should have 6 digits length
            if (pin.length() > 6) {
                System.out.println("PIN should have 6 digits length");
                welcomeScreenInvalid = true;
                continue;
            }

            // Validate pin should only contains number[0-9]
            if (!pin.matches(regex)) {
                System.out.println("PIN should only contains numbers");
                welcomeScreenInvalid = true;
                continue;
            }

            // Check valid Acccount Number & PIN with ATM records
            if (!accountService.getAccounts().stream()
                    .anyMatch(account ->
                            account.getAccountNumber().equals(accountNumber)
                                    && account.getPin().equals(pin))) {
                System.out.println("Invalid Account Number/PIN");
                welcomeScreenInvalid = true;
            } else {
                // set active account
                Optional<Account> result = accountService.getAccounts().stream().filter(account ->
                        account.getAccountNumber().equals(accountNumber)
                                && account.getPin().equals(pin)).findAny();
                result.ifPresent(account -> accountService.setActiveAccount(account));
                welcomeScreenInvalid = false;
            }
        } while (welcomeScreenInvalid);
    }

    public boolean isWelcomeScreenInvalid() {
        return welcomeScreenInvalid;
    }

    public void setWelcomeScreenInvalid(boolean welcomeScreenInvalid) {
        this.welcomeScreenInvalid = welcomeScreenInvalid;
    }
}
