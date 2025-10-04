package bank;

public class Node {
    public Account data;
    public Node next;

    Node(Account acc){
        this.data = acc;
        this.next = null;
    }

    // overriding the toString() method
    // print the data of the Node object instead of a memory address
    @Override
    public String toString(){
        return String.valueOf(data);
    }
}
