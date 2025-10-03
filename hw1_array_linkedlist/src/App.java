import bank.Bank;

public class App {
    public static void main(String[] args) {
        Bank uci = new Bank();
        int a = uci.addUser("Carrie", "4201 PV", "12345678", 100);
        int b = uci.addUser("Carrie", "4201 PV", "12345678", 0);
        int c = uci.addUser("Carrie", "4201 PV", "12345678", 50);

    }
}
