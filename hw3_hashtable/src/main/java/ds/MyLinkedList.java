package ds;

public class MyLinkedList {
    private Node head;
    private int size;

    public MyLinkedList(){
        this.head = null;
        this.size = 0;
    }

    public boolean contains(String x){
        // if(x == null || x.isEmpty()) throw new IllegalArgumentException("Input cannot be null");
        Node cur = head;
        while(cur != null){
            if(x.equals(cur.value)) return true;
            cur = cur.next;
        }
        return false;
    }

    public boolean addIfAbsent(String x){
        // if(x == null || x.isEmpty()) throw new IllegalArgumentException("Input cannot be null");
        // If already present, do not add
        if(contains(x)) return false;

        // Insert at the beginning, because bucket just needs to store values
        // and order does not matter
        Node newNode = new Node(x);
        newNode.next = head;    
        head = newNode;

        size++;
        return true;
    }

    public int size(){
        return size;
    }
}
