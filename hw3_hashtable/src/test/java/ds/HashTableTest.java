package ds;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

import static org.junit.jupiter.api.Assertions.*;

class HashTableTest {

    private HashTable htSmall;  // 小表，方便製造碰撞
    private HashTable htLarge;  // 大表，做一般性測試

    @BeforeEach
    void setup() {
        htSmall = new HashTable(5);    // 小容量方便驗證碰撞
        htLarge = new HashTable(101);  // 隨意的質數容量
    }

    // ---------- 建構子與基本狀態 ----------

    @Test
    void constructor_shouldRejectNonPositiveCapacity() {
        assertThrows(IllegalArgumentException.class, () -> new HashTable(0));
        assertThrows(IllegalArgumentException.class, () -> new HashTable(-7));
    }

    @Test
    void size_initiallyZero() {
        assertEquals(0, htSmall.size());
    }

    // ---------- hash：輸入驗證、範圍、決定性 ----------

    @Test
    void hash_null_shouldThrow() {
        assertThrows(IllegalArgumentException.class, () -> htSmall.hash(null));
    }

    @Test
    void hash_resultAlwaysWithinBounds() {
        String[] keys = {"a", "b", "apple", "banana", "Z9", "", "xxxYYYzzz"};
        for (String k : keys) {
            int idx = htLarge.hash(k);
            assertTrue(0 <= idx && idx < 101, "index out of bounds: " + idx);
        }
    }

    @Test
    void hash_isDeterministicForSameKey() {
        String key = "mango";
        int idx1 = htSmall.hash(key);
        int idx2 = htSmall.hash(key);
        assertEquals(idx1, idx2);
    }

    // ---------- insert：新增、避免重複 ----------

    @Test
    void insert_singleKey_increasesSizeToOne() {
        htSmall.insert("apple");
        assertEquals(1, htSmall.size());
    }

    @Test
    void insert_duplicateKey_doesNotIncreaseSize() {
        htSmall.insert("apple");
        htSmall.insert("apple");
        assertEquals(1, htSmall.size());
    }

    @Test
    void insert_emptyString_allowedAndCounted() {
        htSmall.insert("");
        assertEquals(1, htSmall.size());
        // 再插一次空字串，仍不應增加
        htSmall.insert("");
        assertEquals(1, htSmall.size());
    }

    // ---------- 碰撞測試（分離鏈結應可容納不同鍵於同一桶） ----------

    @Test
    void insert_twoDifferentKeysSameBucket_bothCounted() {
        // 用小容量（5）嘗試找兩個不同字串雜湊到同一格
        String a = null, b = null;
        outer:
        for (int i = 0; i < 200; i++) {
            String s1 = "k" + i;
            int idx1 = htSmall.hash(s1);
            for (int j = i + 1; j < 300; j++) {
                String s2 = "k" + j;
                if (s1.equals(s2)) continue;
                int idx2 = htSmall.hash(s2);
                if (idx1 == idx2) {
                    a = s1; b = s2;
                    break outer;
                }
            }
        }
        assertNotNull(a, "failed to find a colliding pair for capacity=5");
        assertNotNull(b, "failed to find a colliding pair for capacity=5");

        htSmall.insert(a);
        htSmall.insert(b);
        assertEquals(2, htSmall.size(), "collision pair should both be counted");
    }

    // ---------- 壓力一點點：含重複的多次插入 ----------

    @Test
    void insert_manyWithDuplicates_sizeEqualsUniqueCount() {
        // 插入一些字串並包含重複；最後檢查 size 是否等於獨特鍵數
        String[] keys = {
                "aa","bb","cc","dd","ee",
                "aa","bb","cc","xx","yy",
                "zz","xx","aa","cc","ee"
        };
        int unique = 0;
        java.util.HashSet<String> set = new java.util.HashSet<>();
        for (String k : keys) {
            htLarge.insert(k);
            set.add(k);
        }
        unique = set.size();
        assertEquals(unique, htLarge.size());
    }
}
