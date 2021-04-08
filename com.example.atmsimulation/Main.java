import screen.TransactionScreen;
import screen.WelcomeScreen;
import service.Account;
import service.AccountService;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class Main {
    private static Account accountActive;
    private static boolean welcomeScreenInvalid = false;
    private static boolean trxScreenInvalid = false;
    private static String regex = "[0-9]+";
    private static List<Account> accounts;

    public static void main(String[] args) {

        // populate dummy records
        AccountService accountService = new AccountService();

        while (1 == 1) {
            WelcomeScreen welcomeScreen = new WelcomeScreen(accountService);
            welcomeScreen.show();

            TransactionScreen transactionScreen = new TransactionScreen(accountService);
            transactionScreen.show();
        }
    }

    private static void transactionScreen() {
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
                    withdraw();
                    break;
                case "2":
                    transfer();
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

    private static void welcomeScreen() {
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
                System.out.println("service.Account Number should have 6 digits length");
                welcomeScreenInvalid = true;
                continue;
            }

            // Validate account number should only contains number[0-9]
            if (!accountNumber.matches(regex)) {
                System.out.println("service.Account Number should only contains numbers");
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
            if (!accounts.stream()
                    .anyMatch(account ->
                            account.getAccountNumber().equals(accountNumber)
                                    && account.getPin().equals(pin))) {
                System.out.println("Invalid service.Account Number/PIN");
                welcomeScreenInvalid = true;
            } else {
                // set active account
                Optional<Account> result = accounts.stream().filter(account ->
                        account.getAccountNumber().equals(accountNumber)
                                && account.getPin().equals(pin)).findAny();
                result.ifPresent(account -> accountActive = account);
            }
        } while (welcomeScreenInvalid);
    }

    private static void transfer() {
        System.out.println("");
        System.out.print("Please enter destination account and press enter to continue or\n" +
                "press enter to go back to Transaction: ");

        Scanner in = new Scanner(System.in);
        String destinationAccount = in.nextLine();

        if (destinationAccount.isEmpty()) {
            trxScreenInvalid = true;
            return;
        }

        // validate account
        if (!destinationAccount.matches(regex)
                || accounts.stream().noneMatch(account -> account.getAccountNumber().equals(destinationAccount))) {
            System.out.println("Invalid account");
            trxScreenInvalid = true;
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
                trxScreenInvalid = true;
            }
            if (transferAmount < 1) {
                System.out.println("Minimum amount to withdraw is $1");
                trxScreenInvalid = true;
            }
            if (transferAmount > accountActive.getBalance()) {
                System.out.println("Insufficient balance $" + transferAmount);
            }
        } catch (InputMismatchException e) {
            System.out.println("Invalid amount");
            trxScreenInvalid = true;
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
            accountActive.setBalance(accountActive.getBalance() - transferAmount);

            // Add balance to destination account
            Account desAccount = accounts.stream().filter(account ->
                    account.getAccountNumber().equals(destinationAccount)).findAny().get();
            desAccount.setBalance(desAccount.getBalance() + transferAmount);

            System.out.println("");
            System.out.println("Fund Transfer Summary");
            System.out.println("Destination service.Account : " + destinationAccount);
            System.out.println("Transfer Amount     : $" + transferAmount);
            System.out.println("Reference Number    : " + referenceNumber);
            System.out.println("Balance             : $" + accountActive.getBalance());
            System.out.println("");
            System.out.println("1. Transaction");
            System.out.println("2. Exit");
            System.out.print("Choose option[2]: ");

            in = new Scanner(System.in);
            option = in.nextLine();

            if ("1".equals(option)) {
                trxScreenInvalid = true;
            } else {
                trxScreenInvalid = false;
            }
        } else {
            trxScreenInvalid = true;
        }
    }

    public static void withdraw() {
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
                otherScreen();
                break;
            default:
        }
    }

    public static void deduct(int amount) {
        // Deduct balance from account
        if (amount > accountActive.getBalance()) {
            System.out.println("Insufficient balance $" + accountActive.getBalance());
            trxScreenInvalid = true;
        } else {
            accountActive.setBalance(accountActive.getBalance() - amount);
            summary(amount);
        }
    }

    public static void summary(int amount) {
        System.out.println("");
        System.out.println("Summary");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm a");
        LocalDateTime today = LocalDateTime.now();
        System.out.println("Date: " + formatter.format(today));
        System.out.println("Withdraw: $" + amount);
        System.out.println("Balance: $" + accountActive.getBalance());
        System.out.println("");
        System.out.println("1. Transaction");
        System.out.println("2. Exit");
        System.out.print("Choose option[2]: ");

        Scanner in = new Scanner(System.in);
        String option = in.nextLine();

        if ("1".equals(option)) {
            trxScreenInvalid = true;
        } else {
            trxScreenInvalid = false;
        }
    }

    public static void otherScreen() {
        System.out.println("");
        System.out.println("Other Withdraw");
        System.out.print("Enter amount to withdraw: ");

        Scanner in = new Scanner(System.in);
        try {
            int amount = in.nextInt();

            if (amount > 1000) {
                System.out.println("Maximum amount to withdraw is $1000");
                trxScreenInvalid = true;
            } else if (amount % 10 != 0) {
                System.out.println("Invalid amount");
                trxScreenInvalid = true;
            } else if (amount > accountActive.getBalance()) {
                System.out.println("Insufficient balance $" + amount);
                trxScreenInvalid = true;
            } else {
                accountActive.setBalance(accountActive.getBalance() - amount);
                summary(amount);
            }
        } catch (InputMismatchException e) {
            System.out.println("Invalid amount");
            trxScreenInvalid = true;
        }

    }

    public static List<Account> populateRecords() {
        List<Account> accounts = new ArrayList<>();
        Account account = new Account("John Doe", "012108", 100, "112233");
        accounts.add(account);
        account = new Account("Jane Doe", "932012", 30, "112244");
        accounts.add(account);
        return accounts;
    }
}
