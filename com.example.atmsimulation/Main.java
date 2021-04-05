import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        // populate dummy records
        List<Account> accounts = populateRecords();

        Scanner in = new Scanner(System.in);

        String regex = "[0-9]+";
        String accountNumber;
        String pin;

        boolean accountIncorrect = false;
        do {
            // Input account number
            System.out.print("Enter account number: ");
            accountNumber = in.nextLine();

            // Validate account number should have 6 digits length
            if (accountNumber.length() > 6) {
                accountIncorrect = true;
                System.out.println("Account Number should have 6 digits length");
            }

            // Validate account number should only contains number[0-9]
            if (!accountNumber.matches(regex)) {
                accountIncorrect = true;
                System.out.println("Account Number should only contains numbers");
            }
        } while (accountIncorrect);

        boolean pinIncorrect = false;
        do {
            // Input PIN
            System.out.print("Enter PIN: ");
            pin = in.nextLine();

            // Validate pin should have 6 digits length
            if (pin.length() > 6) {
                pinIncorrect = true;
                System.out.println("PIN should have 6 digits length");
            }

            // Validate pin should only contains number[0-9]
            if (!pin.matches(regex)) {
                pinIncorrect = true;
                System.out.println("PIN should only contains numbers");
            }
        } while (pinIncorrect);

        // Check valid Acccount Number & PIN with ATM records
        String finalAccountNumber = accountNumber;
        String finalPin = pin;
        if (!accounts.stream()
                .anyMatch(account ->
                        account.getAccountNumber().equals(finalAccountNumber)
                        && account.getPin().equals(finalPin))) {
            System.out.println("Invalid Account Number/PIN");
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
