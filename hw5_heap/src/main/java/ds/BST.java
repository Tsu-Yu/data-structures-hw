package ds;


public class BST {
    private Node root;
    // Question for TA: do we need to implement insert, delete, search methods for BST?
    // getRoot() for hw5 BSTToHeapTransformer
    public Node getRoot(){
        return root;
    }

    public void insert(int value){
        root = insertRec(root, value);
    }

    private Node insertRec(Node cur, int v){
        if(cur == null) return new Node(v);
        if(v < cur.value){
            cur.left = insertRec(cur.left, v);
        } else if (v > cur.value){
            cur.right = insertRec(cur.right, v);
        }
        return cur;
    }
        

}
