package bank;

public class Node {
    public Account data;
    public Node next;

    Node(Account acc){
        this.data = acc;
        this.next = null;
    }

    @Override
    public String toString(){
        return String.valueOf(data);
    }
}
