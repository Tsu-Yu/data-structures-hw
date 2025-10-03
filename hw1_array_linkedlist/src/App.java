import bank.Bank;

public class App {
    public static void main(String[] args) {
        Bank uci = new Bank();
        int a = uci.addUser("Carrie1", "4201 PV", "12345678", 100);
        int b = uci.addUser("Carrie2", "4201 PV", "12345678", 0);
        int c = uci.addUser("Carrie3", "4201 PV", "12345678", 50);

        System.out.println(a);
        System.out.println(b);
        System.out.println(c);

        boolean deleted = uci.deleteUser(2);
        System.out.print(deleted);

        int d = uci.addUser("Carrie4", "4201 PV", "12345678", 0);

        System.out.println(a);
        System.out.println(b);
        System.out.println(c);
        System.out.println(d);

        uci.printUsers();
    }
}
