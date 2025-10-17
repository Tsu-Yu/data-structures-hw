package ds;

public class Node {
    StudentRecord data;
    Node left;
    Node right;
    Node parent;

    Node(StudentRecord data){
        this.data = data;
        this.left = null;
        this.right = null;
        this.parent = null;
    }
}
