package bank;

public class MyLinkedList {
    Node head;

    public void addSorted(Account acc){
        Node newNode = new Node(acc);
        if(head == null || newNode.data.id < head.data.id){
            newNode.next = head;
            head = newNode;
            return;
        } 
        Node cur = head;
        while(cur.next != null && cur.next.data.id < newNode.data.id){
            cur = cur.next;
        }
        newNode.next = cur.next;
        cur.next = newNode;
    }

    public void printList(){
        Node cur = head;
        while(cur != null){
            System.out.print(cur);
            cur = cur.next;
            System.out.println();
        }
    }
    
}


