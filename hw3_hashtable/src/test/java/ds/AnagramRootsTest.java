package ds;

import org.junit.jupiter.api.Test;
import java.io.File;
import java.io.PrintWriter;

import static org.junit.jupiter.api.Assertions.*;

class AnagramRootsTest {

    @Test
    void tinyFile_mangoGonmaApple_returns2() throws Exception {
        File tmp = File.createTempFile("tiny", ".txt");
        try (PrintWriter pw = new PrintWriter(tmp)) {
            pw.println("mango, gonma apple"); // roots: agmno, agmno, aelpp  → 2
        }
        int ans = AnagramRoots.countUniqueAnagramRoots(tmp, 101);
        assertEquals(2, ans);
        tmp.deleteOnExit();
    }

    @Test
    void onlyDelimiters_returns0() throws Exception {
        File tmp = File.createTempFile("empty", ".txt");
        try (PrintWriter pw = new PrintWriter(tmp)) {
            pw.println(" ,.;!! \t\n --- ");
        }
        int ans = AnagramRoots.countUniqueAnagramRoots(tmp, 97);
        assertEquals(0, ans);
        tmp.deleteOnExit();
    }

    @Test
    void duplicatesAndCase_ignoredByLowercasing() throws Exception {
        File tmp = File.createTempFile("case", ".txt");
        try (PrintWriter pw = new PrintWriter(tmp)) {
            pw.println("Stop TOPS pots Spot"); // 全是同一組 root：opst
        }
        int ans = AnagramRoots.countUniqueAnagramRoots(tmp, 97);
        assertEquals(1, ans);
        tmp.deleteOnExit();
    }
}
