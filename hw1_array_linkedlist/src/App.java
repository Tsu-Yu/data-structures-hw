import java.util.NoSuchElementException;

import bank.Bank;

public class App {
    public static void main(String[] args) {
        Bank bank = new Bank();
        int a = bank.addUser("Amy","Addr","111",120.5);
        int b = bank.addUser("Amy","Addr","111", 79.5);
        bank.mergeAccounts(a, b);
        bank.printUsers();
    }
}
