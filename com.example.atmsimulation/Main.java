import screen.TransactionScreen;
import screen.WelcomeScreen;
import service.Account;
import service.AccountService;

import java.util.List;

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
}
