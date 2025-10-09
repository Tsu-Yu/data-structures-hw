package ds;

public class Node {
    public final String value;
    public Node next;

    public Node(String value){
        if(value == null){
            throw new IllegalArgumentException("value is null.");
        }
        this.value = value;
        this.next = null;
    }
}
