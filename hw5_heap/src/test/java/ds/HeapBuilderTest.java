package ds;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.*;
import java.util.stream.Collectors;

class HeapBuilderTest {

    private List<Integer> levelOrderValues(Node root) {
        if (root == null) return List.of();
        List<Integer> vals = new ArrayList<>();
        Deque<Node> q = new ArrayDeque<>();
        q.add(root);
        while (!q.isEmpty()) {
            Node n = q.poll();
            vals.add(n.value);
            if (n.left != null) q.add(n.left);
            if (n.right != null) q.add(n.right);
        }
        return vals;
    }

    private void assertHeapProperty(List<Integer> levelOrder, boolean isMin) {
        // 用完全二元樹陣列索引檢查 heap 性質
        for (int i = 0; i < levelOrder.size(); i++) {
            final int idx = i; // 避免 lambda 捕捉非 final 變數
            int parent = levelOrder.get(i);
            int left = 2 * i + 1;
            int right = 2 * i + 2;

            if (left < levelOrder.size()) {
                int child = levelOrder.get(left);
                if (isMin) {
                    assertTrue(parent <= child,
                        () -> String.format("Min-heap violated at i=%d: parent=%d child(left)=%d",
                                idx, parent, child));
                } else {
                    assertTrue(parent >= child,
                        () -> String.format("Max-heap violated at i=%d: parent=%d child(left)=%d",
                                idx, parent, child));
                }
            }
            if (right < levelOrder.size()) {
                int child = levelOrder.get(right);
                if (isMin) {
                    assertTrue(parent <= child,
                        () -> String.format("Min-heap violated at i=%d: parent=%d child(right)=%d",
                                idx, parent, child));
                } else {
                    assertTrue(parent >= child,
                        () -> String.format("Max-heap violated at i=%d: parent=%d child(right)=%d",
                                idx, parent, child));
                }
            }
        }
    }

    private void assertSameMultiset(Collection<Integer> a, Collection<Integer> b) {
        var ca = a.stream().collect(Collectors.groupingBy(x -> x, Collectors.counting()));
        var cb = b.stream().collect(Collectors.groupingBy(x -> x, Collectors.counting()));
        assertEquals(ca, cb, "Tree values multiset differs from input values");
    }

    @Test
    void createMinHeap_basic() {
        HeapBuilder hb = new HeapBuilder();
        List<Integer> input = Arrays.asList(5, 3, 8, 1, 4, 7, 9);

        Node root = hb.createMinHeap(input);
        assertNotNull(root, "Root should not be null");

        List<Integer> level = levelOrderValues(root);
        assertEquals(input.size(), level.size(), "Node count should match input size");
        assertSameMultiset(input, level);
        assertHeapProperty(level, true); // min-heap
        assertEquals(Collections.min(input), level.get(0));
    }

    @Test
    void createMaxHeap_basic() {
        HeapBuilder hb = new HeapBuilder();
        List<Integer> input = Arrays.asList(5, 3, 8, 1, 4, 7, 9);

        Node root = hb.createMaxHeap(input);
        assertNotNull(root, "Root should not be null");

        List<Integer> level = levelOrderValues(root);
        assertEquals(input.size(), level.size(), "Node count should match input size");
        assertSameMultiset(input, level);
        assertHeapProperty(level, false); // max-heap
        assertEquals(Collections.max(input), level.get(0));
    }

    @Test
    void createHeap_handlesDuplicates() {
        HeapBuilder hb = new HeapBuilder();
        List<Integer> input = Arrays.asList(2, 2, 2, 1, 1, 3, 3);

        Node minRoot = hb.createMinHeap(input);
        Node maxRoot = hb.createMaxHeap(input);

        var minLevel = levelOrderValues(minRoot);
        var maxLevel = levelOrderValues(maxRoot);

        assertSameMultiset(input, minLevel);
        assertSameMultiset(input, maxLevel);

        assertHeapProperty(minLevel, true);
        assertHeapProperty(maxLevel, false);
    }

    @Test
    void createHeap_singleElement() {
        HeapBuilder hb = new HeapBuilder();
        List<Integer> input = List.of(42);

        Node minRoot = hb.createMinHeap(input);
        Node maxRoot = hb.createMaxHeap(input);

        assertNotNull(minRoot);
        assertNotNull(maxRoot);
        assertNull(minRoot.left);
        assertNull(minRoot.right);
        assertEquals(42, minRoot.value);
        assertEquals(42, maxRoot.value);
    }

    @Test
    void createHeap_nullOrEmpty() {
        HeapBuilder hb = new HeapBuilder();

        assertNull(hb.createMinHeap(null));
        assertNull(hb.createMaxHeap(null));
        assertNull(hb.createMinHeap(List.of()));
        assertNull(hb.createMaxHeap(List.of()));
    }
}
