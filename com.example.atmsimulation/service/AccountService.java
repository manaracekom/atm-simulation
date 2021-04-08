package service;

import java.util.ArrayList;
import java.util.List;

public class AccountService {
    private Account activeAccount;
    private List<Account> accounts;

    public AccountService() {
        // populate dummy records everytime create new instance
        populateRecords();
    }

    private void populateRecords() {
        accounts = new ArrayList<>();
        Account account = new Account("John Doe", "012108", 100, "112233");
        accounts.add(account);
        account = new Account("Jane Doe", "932012", 30, "112244");
        accounts.add(account);
    }

    public void deduct(int amount) {
        this.activeAccount.setBalance(this.activeAccount.getBalance() - amount);
    }

    public void transfer(int transferAmount, String destinationAccount) {
        Account desAccount = accounts.stream().filter(account ->
                account.getAccountNumber().equals(destinationAccount)).findAny().get();
        desAccount.setBalance(desAccount.getBalance() + transferAmount);
    }

    public Account getActiveAccount() {
        return activeAccount;
    }

    public void setActiveAccount(Account activeAccount) {
        this.activeAccount = activeAccount;
    }

    public List<Account> getAccounts() {
        return accounts;
    }

    public void setAccounts(List<Account> accounts) {
        this.accounts = accounts;
    }
}
