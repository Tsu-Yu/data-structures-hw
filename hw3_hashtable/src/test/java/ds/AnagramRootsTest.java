package ds;

import org.junit.jupiter.api.Test;

import ds.AnagramRoots;

import java.io.File;
import java.io.PrintWriter;
import static org.junit.jupiter.api.Assertions.*;

class AnagramRootsTest {

    @Test
    void tinyFile_twoUniqueRoots() throws Exception {
        // 內容：mango gonma apple  → roots: agmno, agmno, aelpp → 獨特=2
        File tmp = File.createTempFile("tiny", ".txt");
        tmp.deleteOnExit();
        try (PrintWriter pw = new PrintWriter(tmp)) {
            pw.println("mango, gonma apple");
        }
        int count = AnagramRoots.countUniqueRoots(tmp, 101);
        assertEquals(2, count);
    }

    @Test
    void onlyDelimiters_zero() throws Exception {
        File tmp = File.createTempFile("empty", ".txt");
        tmp.deleteOnExit();
        try (PrintWriter pw = new PrintWriter(tmp)) {
            pw.println(" ,.;!! \t\n ");
        }
        int count = AnagramRoots.countUniqueRoots(tmp, 101);
        assertEquals(0, count);
    }
}
