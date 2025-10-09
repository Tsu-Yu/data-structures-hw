package ds;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

import static org.junit.jupiter.api.Assertions.*;

class HashTableTest {

    private HashTable htSmall;  
    private HashTable htLarge;  

    @BeforeEach
    void setup() {
        htSmall = new HashTable(5);    
        htLarge = new HashTable(101);  
    }


    @Test
    void constructor_shouldRejectNonPositiveCapacity() {
        assertThrows(IllegalArgumentException.class, () -> new HashTable(0));
        assertThrows(IllegalArgumentException.class, () -> new HashTable(-7));
    }

    @Test
    void size_initiallyZero() {
        assertEquals(0, htSmall.size());
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
        assertEquals(1, htSmall.size()); // because empty string is considered a valid key
        htSmall.insert("");
        assertEquals(1, htSmall.size());
    }


    @Test
    void insert_twoDifferentKeysSameBucket_bothCounted() {
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


    @Test
    void insert_manyWithDuplicates_sizeEqualsUniqueCount() {
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
