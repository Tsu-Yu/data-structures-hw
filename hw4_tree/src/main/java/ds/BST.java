package ds;

import java.util.Queue;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class BST {
    private Node root;

    public boolean insert(StudentRecord s){
        if(root == null) {root = new Node(s); return true; }
        Node cur = root, parent = null;
        int cmp = 0;
        while(cur != null){
            cmp = StudentRecord.KEY_COMPARATOR.compare(s, cur.data);
            parent = cur;
            if(cmp == 0) return false; // it already exists
            cur = (cmp < 0)? cur.left : cur.right;
        }
        if (cmp < 0) parent.left = new Node(s);
        else parent.right = new Node(s);
        return true;
    }

    public StudentRecord search(String lastName, String studentNo){
        StudentRecord key = new StudentRecord(studentNo, lastName, "", "","");
        Node cur = root;
        while(cur != null) {
            int cmp = StudentRecord.KEY_COMPARATOR.compare(key, cur.data);
            if(cmp == 0) return cur.data;
            cur = (cmp < 0)? cur.left : cur.right; 
        }
        return null;
    }

    public boolean delete(String lastName, String studentNo){
        StudentRecord key = new StudentRecord(studentNo, lastName, "", "","");
        Node cur = root, parent = null;
        boolean isLeft = false;

        while(cur != null){
            int cmp = StudentRecord.KEY_COMPARATOR.compare(key, cur.data);
            if (cmp == 0) break; // found the target, and keep going to find does it have children
            parent = cur;
            if (cmp < 0) { isLeft = true; cur = cur.left; }
            else { isLeft = false; cur = cur.right; }
        }
        if (cur == null) return false;

        // Case 1: no child
        if (cur.left == null && cur.right == null) {
            if (cur == root) root = null;
            else if (isLeft) parent.left = null; else parent.right = null;
        }

        // Case 2: 1 child
        else if (cur.left == null || cur.right == null) {
            Node child = (cur.left != null) ? cur.left : cur.right;
            if (cur == root) root = child;
            else if (isLeft) parent.left = child; else parent.right = child;
        }

        // Case 3: 2 children
        else {
            // cur = deleting target
            Node succParent = cur;
            Node succ = cur.right;

            // find the most left node of the right tree
            while (succ.left != null) { succParent = succ; succ = succ.left; }
            
            // override target data
            cur.data = succ.data;

            if (succParent.left == succ) succParent.left = succ.right;
            else succParent.right = succ.right;
        }
        return true;
    }

    // depth-first traversal

    public List<StudentRecord> inOrderTraversal(){
        List<StudentRecord> out = new ArrayList<>();
        inOrder(root, out);
        return out;
    }

    private void inOrder(Node n, List<StudentRecord> out){
        if(n == null) return;
        inOrder(n.left, out);  // left
        out.add(n.data);       // me
        inOrder(n.right, out); // right
    }

    public List<StudentRecord> preOrderTraversal(){
        List<StudentRecord> out = new ArrayList<>();
        preOrder(root, out);
        return out;
    }

    private void preOrder(Node n, List<StudentRecord> out){
        if(n == null) return;
        out.add(n.data);       // me
        preOrder(n.left, out);  // left
        preOrder(n.right, out); // right
    }

    public List<StudentRecord> postOrderTraversal(){
        List<StudentRecord> out = new ArrayList<>();
        postOrder(root, out);
        return out;
    }

    private void postOrder(Node n, List<StudentRecord> out){
        if(n == null) return;
        postOrder(n.left, out);  // left
        postOrder(n.right, out); // right
        out.add(n.data);       // me
    }

    // breadth-first traversal
    public List<StudentRecord> levelOrderTraversal(){
        List<StudentRecord> out = new ArrayList<>();
        if (root == null) return out;

        Queue<Node> q = new LinkedList<>();
        q.add(root);

        /* while the queue is not empty:
             1. remove the front node from the queue
             2. visit the node (add its data to the output list)
             3. add the node's left child to the queue (if it exists)
             4. add the node's right child to the queue (if it exists)
        */ 
        while (!q.isEmpty()) {
            Node n = q.remove();
            out.add(n.data);
            if (n.left != null) q.add(n.left);
            if (n.right != null) q.add(n.right);
        }
        return out;
    }
}
