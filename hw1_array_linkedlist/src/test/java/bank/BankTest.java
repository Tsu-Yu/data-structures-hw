package bank;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;


public class BankTest {
    @Test
    void addUser_and_reuseFreedIds() {
        Bank bank = new Bank();
        int a = bank.addUser("Amy","A","111",100);
        int b = bank.addUser("Bob","B","222",0);
        int c = bank.addUser("Cat","C","333",10.5);
        assertEquals(1, a); assertEquals(2, b); assertEquals(3, c);

        assertTrue(bank.deleteUser(2));        // 回收 2
        int d = bank.addUser("Dan","D","444",1);
        assertEquals(2, d);                    // 先用最小回收 ID
        int e = bank.addUser("Eve","E","555",1);
        assertEquals(4, e);                    // 再用遞增
    }

    @Test
    void payUserToUser_successAndFailures() {
        Bank bank = new Bank();
        int p = bank.addUser("P","A","111",500);
        int q = bank.addUser("Q","B","222",100);

        assertTrue(bank.payUserToUser(p, q, 200));
        assertEquals(300.0, bank.findUser(p).initialDeposit, 1e-6);
        assertEquals(300.0, bank.findUser(q).initialDeposit, 1e-6);

        assertFalse(bank.payUserToUser(p, q, -1));     // 金額<=0
        assertFalse(bank.payUserToUser(999, q, 10));   // 找不到
        assertFalse(bank.payUserToUser(p, q, 1000));   // 餘額不足
    }

    @Test
    void mergeAccounts_samePerson_keepsSmallerId_andReclaimsBigger() {
        Bank bank = new Bank();
        int i1 = bank.addUser("Amy","Addr","111",120.5);
        int i2 = bank.addUser("Amy","Addr","111", 79.5);

        assertTrue(bank.mergeAccounts(i1, i2));
        int keep = Math.min(i1, i2);
        int drop = Math.max(i1, i2);

        assertEquals(200.0, bank.findUser(keep).initialDeposit, 1e-6);
        assertNull(bank.findUser(drop));           // 被刪除
        // 下一個 addUser 會優先拿到 drop（被回收的最小ID）
        int j = bank.addUser("X","Y","999",1);
        assertEquals(drop, j);
    }

    @Test
    void mergedBank_handlesDuplicates_withFreeHolesThenNextId() {
        // OC: 1,4    LA: 1,4   （空號 2,3 應被優先填）
        Bank oc = new Bank();
        oc.addUser("O1","X","s1",10); // 1
        oc.addUser("O4","X","s4",10); // 2 (回收池可能用到，但清單是 1,2…別混淆 — 這裡重點是清單ID)
        // 讓 ID 變成 1,4：做法是先加到 1,2,3,4 然後刪 2,3；或直接手動構建清單測邏輯。
        // 簡化：我們這裡直接構造另一組更直覺的例子：

        Bank b1 = new Bank();
        int a1 = b1.addUser("A1","X","s",1); // 1
        int a2 = b1.addUser("A2","X","s",1); // 2
        int a3 = b1.addUser("A3","X","s",1); // 3
        b1.deleteUser(2); // 釋放 2，清單成 1,3

        Bank b2 = new Bank();
        int c1 = b2.addUser("C1","Y","t",1); // 1 (衝突)
        int c2 = b2.addUser("C2","Y","t",1); // 2

        Bank nb = Bank.mergedBank(b1, b2);
        // 已用：{1,3,1,2} → max=3 → 空號池={2}；衝突 id=1 → 另一筆用 2
        assertNotNull(nb.findUser(1)); // 保留一個 1
        assertNotNull(nb.findUser(2)); // 衝突時填入最小空號 2
        assertNotNull(nb.findUser(3)); // 原本的 3
    }

    @Test
    void getMedianId_delegatesToList() {
        Bank bank = new Bank();
        bank.addUser("A","x","1",0); // 1
        bank.addUser("B","x","2",0); // 2
        bank.addUser("C","x","3",0); // 3
        assertEquals(2.0f, bank.getMedianId(), 1e-6);
        bank.addUser("D","x","4",0); // 4
        assertEquals(2.5f, bank.getMedianId(), 1e-6);
    }
}
