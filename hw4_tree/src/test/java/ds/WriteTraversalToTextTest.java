package ds;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

class WriteTraversalToTextTest {

    /** 從 src/test/resources/tree-input.txt 載入所有非空行 */
    private List<String> loadLines() throws Exception {
        var in = getClass().getResourceAsStream("/tree-input.txt");
        assertNotNull(in, "Missing /tree-input.txt in src/test/resources");
        try (var br = new BufferedReader(new InputStreamReader(in, StandardCharsets.UTF_8))) {
            return br.lines().filter(s -> !s.isBlank()).toList();
        }
    }

    /** 小工具：把清單逐行寫到檔案 */
    private void writeLines(List<StudentRecord> list, Path out) throws Exception {
        Files.createDirectories(out.getParent() == null ? Path.of(".") : out.getParent());
        try (BufferedWriter bw = Files.newBufferedWriter(out, StandardCharsets.UTF_8)) {
            for (StudentRecord r : list) {
                bw.write(r.toString());  // 目前是 CSV: studentNo,lastName,dept,program,year
                bw.newLine();
            }
        }
    }

    @Test
    void build_tree_then_write_inorder_and_levelorder(@TempDir Path tmp) throws Exception {
        // 1) 讀檔並建樹
        BST tree = new BST();
        for (String line : loadLines()) {
            var p = RecordParser.parseLine(line);
            assertEquals('I', Character.toUpperCase(p.op), "Sample file expected only 'I' ops");
            assertTrue(tree.insert(p.rec), "Duplicate key? " + p.rec);
        }

        // 2) 目標輸出檔案（放在 JUnit 暫存資料夾）
        Path inorderOut  = tmp.resolve("inorder.txt");
        Path levelOut    = tmp.resolve("levelorder.txt");

        // 3) 真的寫檔（Task-2 / Task-3）
        writeLines(tree.inOrderTraversal(),   inorderOut);
        writeLines(tree.levelOrderTraversal(), levelOut);

        // 4) 驗證檔案存在 & 內容合理
        assertTrue(Files.exists(inorderOut), "inorder.txt should be created");
        assertTrue(Files.exists(levelOut),   "levelorder.txt should be created");

        var inorderLines = Files.readAllLines(inorderOut, StandardCharsets.UTF_8);
        var levelLines   = Files.readAllLines(levelOut,   StandardCharsets.UTF_8);

        // 節點數一致（兩個輸出應包含同一批節點）
        assertEquals(inorderLines.size(), levelLines.size());

        // 內容集合相等（順序不同沒關係，集合要一樣）
        Set<String> setIn = Set.copyOf(inorderLines);
        Set<String> setLv = Set.copyOf(levelLines);
        assertEquals(setIn, setLv, "Both traversals must contain the same nodes");

        // in-order 應等於用 KEY_COMPARATOR 排序後的結果
        var parsedInorder = inorderLines.stream()
                .map(s -> { // 反向解析回 StudentRecord 做排序比對（簡化：用逗號切）
                    String[] arr = s.split(",", -1);
                    return new StudentRecord(arr[0], arr[1], arr[2], arr[3], arr[4]);
                })
                .collect(Collectors.toList());
        var sorted = parsedInorder.stream()
                .sorted(StudentRecord.KEY_COMPARATOR)
                .toList();
        assertEquals(sorted, parsedInorder, "In-order must match comparator-sorted order");

        // BFS 第一個應是第一筆插入（檔案第一行是 McKay）
        assertTrue(levelLines.get(0).contains(",McKay,"), "First BFS node should be McKay (root after insert sequence)");
    }
}
