package ds;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TraversalTest {

    private StudentRecord s(String no, String ln) {
        return new StudentRecord(no, ln, "CS", "SE", "1");
    }

    @Test
    void inorder_is_sorted_by_key() {
        BST t = new BST();
        t.insert(s("3", "Clark"));
        t.insert(s("2", "brown"));
        t.insert(s("1", "Brown")); // 同姓，學號打平
        t.insert(s("4", "davis"));

        List<StudentRecord> in = t.inOrderTraversal();
        assertEquals(4, in.size());
        assertEquals("Brown", in.get(0).getLastname());
        assertEquals("brown", in.get(1).getLastname());
        assertEquals("Clark", in.get(2).getLastname());
        assertEquals("davis", in.get(3).getLastname());
    }

    @Test
    void levelOrder_is_layer_by_layer() {
        BST t = new BST();
        t.insert(s("2", "Miller"));
        t.insert(s("1", "Clark"));
        t.insert(s("4", "Smith"));
        t.insert(s("3", "Jones"));

        List<StudentRecord> bfs = t.levelOrderTraversal();
        assertEquals(4, bfs.size());
        assertEquals("Miller", bfs.get(0).getLastname());
        assertEquals("Clark",  bfs.get(1).getLastname());
        assertEquals("Smith",  bfs.get(2).getLastname());
        assertEquals("Jones",  bfs.get(3).getLastname());
    }
}
