import bank.Bank;

public class App {
    public static void main(String[] args) {
        Bank uci = new Bank();
        int a = uci.addUser("Carrie1", "4201 PV", "12345678", 100);
        int b = uci.addUser("Carrie2", "4201 PV", "12345678", 0);
        int c = uci.addUser("Carrie3", "4201 PV", "12345678", 50);

        uci.payUserToUser(a, b, 100);

        uci.printUsers();
    }
}
