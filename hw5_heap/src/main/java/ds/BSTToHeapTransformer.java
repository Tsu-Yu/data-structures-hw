package ds;
import java.util.List; 
import java.util.ArrayList;
import java.util.Queue;
import java.util.ArrayDeque;

public class BSTToHeapTransformer {
    private final HeapBuilder heapBuilder = new HeapBuilder();

    public List<Node> bstToMinHeap(BST bst){
        if (bst == null || bst.getRoot() == null) return null;

        List<Integer> values = new ArrayList<>();
        inOrder(bst.getRoot(), values); // get sorted records

        // Question for TA: should we return List<Node>, Node?
        Node root = heapBuilder.createMinHeap(values);
        return toLevelOrderList(root);
    }

    public List<Node> bstToMaxHeap(BST bst){
        if (bst == null || bst.getRoot() == null) return null;

        List<Integer> values = new ArrayList<>();
        inOrder(bst.getRoot(), values); // get sorted records

        // Question for TA: should we return List<Node>, Node?
        Node root = heapBuilder.createMaxHeap(values);
        return toLevelOrderList(root);
    }

    private void inOrder(Node n, List<Integer> out){
        if(n == null) return;
        inOrder(n.left, out);  // left
        out.add(n.value);       // me
        inOrder(n.right, out); // right
    }

    private List<Node> toLevelOrderList(Node root){
        if (root == null) return null;
        List<Node> out = new ArrayList<>();
        Queue<Node> queue = new ArrayDeque<>();
        queue.add(root);

        while (!queue.isEmpty()) {
            Node cur = queue.poll();
            out.add(cur);

            if (cur.left != null) {
                queue.add(cur.left);
            }
            if (cur.right != null) {
                queue.add(cur.right);
            }
        }

        return out;
    }
}
