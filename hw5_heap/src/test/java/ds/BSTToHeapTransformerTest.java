package ds;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.*;
import java.util.stream.Collectors;

class BSTToHeapTransformerTest {

    private void buildBST(BST bst, int... vals) {
        for (int v : vals) bst.insert(v);
    }

    private List<Integer> inorderValues(Node root) {
        List<Integer> out = new ArrayList<>();
        Deque<Node> stack = new ArrayDeque<>();
        Node cur = root;
        while (cur != null || !stack.isEmpty()) {
            while (cur != null) {
                stack.push(cur);
                cur = cur.left;
            }
            cur = stack.pop();
            out.add(cur.value);
            cur = cur.right;
        }
        return out;
    }

    private void assertSameMultiset(Collection<Integer> a, Collection<Integer> b) {
        var ca = a.stream().collect(Collectors.groupingBy(x -> x, Collectors.counting()));
        var cb = b.stream().collect(Collectors.groupingBy(x -> x, Collectors.counting()));
        assertEquals(ca, cb, "Values multiset differs");
    }

    private void assertListIsHeapLevelOrder(List<Node> levelNodes, boolean isMin) {
        // 用 List<Node>（層序）依完全二元樹索引檢查 heap 性質
        for (int i = 0; i < levelNodes.size(); i++) {
            final int idx = i; // 給 lambda 用
            int parent = levelNodes.get(i).value;
            int left = 2 * i + 1;
            int right = 2 * i + 2;

            if (left < levelNodes.size()) {
                int child = levelNodes.get(left).value;
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
            if (right < levelNodes.size()) {
                int child = levelNodes.get(right).value;
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

    @Test
    void bstToMinHeap_basic() {
        BST bst = new BST();
        buildBST(bst, 5, 3, 8, 1, 4, 7, 9);

        BSTToHeapTransformer t = new BSTToHeapTransformer();
        List<Node> levelNodes = t.bstToMinHeap(bst);

        assertNotNull(levelNodes, "Returned list should not be null");

        List<Integer> expected = inorderValues(bst.getRoot()); // 實際 BST 內容
        List<Integer> values = levelNodes.stream().map(n -> n.value).collect(Collectors.toList());

        assertEquals(expected.size(), levelNodes.size(), "Node count should match BST size");
        assertSameMultiset(expected, values);
        assertListIsHeapLevelOrder(levelNodes, true); // min-heap

        // sanity: root 應為最小值
        assertEquals(Collections.min(expected), values.get(0));
    }

    @Test
    void bstToMaxHeap_basic() {
        BST bst = new BST();
        buildBST(bst, 5, 3, 8, 1, 4, 7, 9);

        BSTToHeapTransformer t = new BSTToHeapTransformer();
        List<Node> levelNodes = t.bstToMaxHeap(bst);

        assertNotNull(levelNodes);

        List<Integer> expected = inorderValues(bst.getRoot());
        List<Integer> values = levelNodes.stream().map(n -> n.value).collect(Collectors.toList());

        assertEquals(expected.size(), levelNodes.size());
        assertSameMultiset(expected, values);
        assertListIsHeapLevelOrder(levelNodes, false); // max-heap

        // sanity: root 應為最大值
        assertEquals(Collections.max(expected), values.get(0));
    }

    @Test
    void bstToHeap_handlesDuplicates_orDedup() {
        BST bst = new BST();
        // 不論 BST 是否接受重複值，期望都以「實際 BST 內容」為準
        buildBST(bst, 2, 2, 1, 3, 3, 1, 2);

        List<Integer> expected = inorderValues(bst.getRoot());

        BSTToHeapTransformer t = new BSTToHeapTransformer();
        var minList = t.bstToMinHeap(bst);
        var maxList = t.bstToMaxHeap(bst);

        var minVals = minList.stream().map(n -> n.value).collect(Collectors.toList());
        var maxVals = maxList.stream().map(n -> n.value).collect(Collectors.toList());

        assertEquals(expected.size(), minList.size());
        assertEquals(expected.size(), maxList.size());
        assertSameMultiset(expected, minVals);
        assertSameMultiset(expected, maxVals);

        assertListIsHeapLevelOrder(minList, true);
        assertListIsHeapLevelOrder(maxList, false);
    }

    @Test
    void bstToHeap_nullOrEmpty() {
        BSTToHeapTransformer t = new BSTToHeapTransformer();

        // null BST
        assertNull(t.bstToMinHeap(null));
        assertNull(t.bstToMaxHeap(null));

        // empty BST（root 為 null）
        BST empty = new BST();
        if (empty.getRoot() == null) {
            assertNull(t.bstToMinHeap(empty));
            assertNull(t.bstToMaxHeap(empty));
        } else {
            // 若你的 BST 用哨兵節點，則 expected 依中序為準（通常 size 會是 0）
            List<Node> minList = t.bstToMinHeap(empty);
            List<Node> maxList = t.bstToMaxHeap(empty);
            List<Integer> expected = inorderValues(empty.getRoot());
            assertSameMultiset(expected, minList.stream().map(n -> n.value).collect(Collectors.toList()));
            assertSameMultiset(expected, maxList.stream().map(n -> n.value).collect(Collectors.toList()));
        }
    }
}
