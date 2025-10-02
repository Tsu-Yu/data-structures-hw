import bank.Account;
import bank.MyLinkedList;

public class App {
    public static void main(String[] args) {
        MyLinkedList list = new MyLinkedList();
        list.addSorted(new Account(3, "Joe", "4201 pv rd", "1234567", 0));
        list.addSorted(new Account(2, "Amy", "4202 pv rd", "1237654", 200));
        list.addSorted(new Account(7, "Bob", "4203 pv rd", "1239876", 30.56));
        list.addSorted(new Account(5, "Sam", "4204 pv rd", "1230098", 100.7));

        list.printList();
        
    }
}
