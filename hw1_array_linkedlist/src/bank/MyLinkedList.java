package bank;

public class MyLinkedList {
    Node head;

    // add new account in sorted order by id
    public void addSorted(Account acc){
        
        // if the list is empty or the new id is smaller than the head id
        // insert at the beginning
        Node newNode = new Node(acc);
        if(head == null || newNode.data.id < head.data.id){
            newNode.next = head;
            head = newNode;
            return;
        } 

        // if not, and new id is greater than head id
        // find the correct position to insert
        Node cur = head;
        while(cur.next != null && cur.next.data.id < newNode.data.id){
            cur = cur.next;
        }
        newNode.next = cur.next;
        cur.next = newNode;
    }

    // print all accounts in the linked list
    public void printList(){
        Node cur = head;
        while(cur != null){
            System.out.print(cur);
            cur = cur.next;
            System.out.println();
        }
    }
    
}


