package ds;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BSTInsertSearchDeleteTest {

    private StudentRecord s(String no, String ln) {
        return new StudentRecord(no, ln, "CS", "SE", "1");
    }

    @Test
    void insert_and_search_and_delete_variants() {
        BST t = new BST();

        // 插入：同姓不同學號要允許（用 studentNo 當次鍵）
        assertTrue(t.insert(s("1000001", "Smith")));
        assertTrue(t.insert(s("1000002", "smith"))); // case-insensitive, 但不同學號可存在
        assertTrue(t.insert(s("1000003", "Anderson")));
        assertTrue(t.insert(s("1000004", "Zimmer")));

        // 搜尋（大小寫不敏感 + 學號精確）
        assertNotNull(t.search("Smith", "1000001"));
        assertNotNull(t.search("smith", "1000002"));
        assertNull(t.search("Brown", "9999999"));

        // 刪除：leaf
        assertTrue(t.delete("Zimmer", "1000004"));
        assertNull(t.search("Zimmer", "1000004"));

        // 刪除：兩子節點（會用中序後繼替換）
        assertTrue(t.delete("Smith", "1000001"));
        assertNull(t.search("Smith", "1000001"));
        assertNotNull(t.search("smith", "1000002")); // 另一筆 Smith 仍在
    }
}
