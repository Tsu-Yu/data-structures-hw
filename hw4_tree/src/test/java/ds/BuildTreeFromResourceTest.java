package ds;

import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

class BuildTreeFromResourceTest {

    /** 從 src/test/resources 載入 tree-input.txt 並逐行回傳 */
    private List<String> loadLines() throws Exception {
        var in = getClass().getResourceAsStream("/tree-input.txt");
        assertNotNull(in, "Did not find /tree-input.txt in src/test/resources");
        try (var br = new BufferedReader(new InputStreamReader(in, StandardCharsets.UTF_8))) {
            return br.lines().filter(s -> !s.isBlank()).collect(Collectors.toList());
        }
    }

    @Test
    void build_bst_from_resource_and_verify_traversals() throws Exception {
        // 1) 讀檔並建樹
        List<String> lines = loadLines();
        BST tree = new BST();

        int inserted = 0;
        for (String line : lines) {
            var p = RecordParser.parseLine(line);
            assertEquals('I', Character.toUpperCase(p.op), "Expected only insert ops (I) in the sample file");
            assertTrue(tree.insert(p.rec), "Unexpected duplicate key while inserting: " + p.rec);
            inserted++;
        }
        assertTrue(inserted > 0, "No records inserted");
        assertEquals(inserted, tree.levelOrderTraversal().size(), "Tree size mismatch after inserts");

        // 2) 驗證 In-order：應等於用比較器排序後的結果
        List<StudentRecord> inorder = tree.inOrderTraversal();
        List<StudentRecord> sorted  = inorder.stream()
                .sorted(StudentRecord.KEY_COMPARATOR)
                .collect(Collectors.toList());
        assertEquals(sorted, inorder, "In-order traversal must equal comparator-sorted order");

        // 額外檢查：LastName 是非遞減（忽略大小寫），同姓以學號打平
        for (int i = 1; i < inorder.size(); i++) {
            StudentRecord a = inorder.get(i - 1);
            StudentRecord b = inorder.get(i);
            int cmp = StudentRecord.KEY_COMPARATOR.compare(a, b);
            assertTrue(cmp <= 0, "In-order not non-decreasing at index " + i + " : " + a + " > " + b);
        }

        // 3) 驗證 Level-order（BFS）
        // 根節點應為第一筆插入（檔案第一行） → lastName = McKay
        List<StudentRecord> bfs = tree.levelOrderTraversal();
        assertFalse(bfs.isEmpty());
        assertEquals("McKay", bfs.get(0).getLastname(), "Root (first in BFS) should be the first inserted record's last name");

        // BFS 只有一種層序順序；我們不硬寫整串期望，改做幾個 sanity checks：
        // - 節點數一致
        // - 所有節點都存在（集合相等）
        Set<String> inorderSet = inorder.stream().map(StudentRecord::toString).collect(Collectors.toSet());
        Set<String> bfsSet     = bfs.stream().map(StudentRecord::toString).collect(Collectors.toSet());
        assertEquals(inorderSet, bfsSet, "BFS should contain exactly the same nodes as the tree (order differs)");

        // 4) spot-check 幾個關鍵姓氏是否存在（避免 parser/trim 問題）
        var mustHave = List.of("McKay", "LaPorte", "Black", "Green", "Johnston", "White", "Smith", "Zot");
        var names = inorder.stream().map(StudentRecord::getLastname).collect(Collectors.toSet());
        for (String ln : mustHave) {
            assertTrue(names.contains(ln), "Missing last name in tree: " + ln);
        }
    }
}
