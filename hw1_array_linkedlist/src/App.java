import java.util.NoSuchElementException;

import bank.Bank;

public class App {
    public static void main(String[] args) {
        // Bank of Orange County
        Bank oc = new Bank();
        int oc1 = oc.addUser("A","X","111",10); // 1
        int oc2 = oc.addUser("B","Y","222",10); // 2
        int oc3 = oc.addUser("C","Z","333",10); // 3

        // Bank of Los Angeles
        Bank la = new Bank();
        int la1 = la.addUser("L1","LX","444",5); // 1
        int la2 = la.addUser("L2","LY","555",5); // 2
        int la3 = la.addUser("L3","LZ","666",5); // 3
        int la4 = la.addUser("L4","LW","777",5); // 4

        // 為了「沒有重複」，我們先把 OC 的 3 刪掉，留下 1,2；LA 留 3,4
        oc.deleteUser(oc3); // 釋放 3，但不在清單內了：OC 清單只有 1,2

        Bank sc = Bank.mergedBank(oc, la);
        sc.printUsers();
    }
}
