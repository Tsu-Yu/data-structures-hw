package bank;

import java.util.NoSuchElementException;

public class MyLinkedList {
    Node head;

    // add new account in sorted order by id
    public void addUser(Account acc){
        
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

    public boolean removeById(int id){
        if(head == null) return false;

        if (head.data.id == id){
            head = head.next;
            return true;
        }

        Node prev = head;
        Node cur = head.next;
        while(cur != null){
            if(cur.data.id == id){
                prev.next = cur.next;
                return true;
            }

            if(cur.data.id > id){
                return false;
            }

            prev = cur;
            cur = cur.next;
        }
        return false;    
    }

    public Account findById(int id){
        Node cur = head;
        while(cur != null){
            if(cur.data.id == id) return cur.data;
            if(cur.data.id > id) return null;
            cur = cur.next;
        }
        return null;
    }

    public float getMedianId(){
        if(head == null){
            throw new NoSuchElementException("empty list");
        }

        Node slow = head;
        Node fast = head;

        while (fast.next != null && fast.next.next != null){
            slow = slow.next;
            fast =fast.next.next;
        }

        if(fast.next == null){
            return (float)slow.data.id;
        }else {
            return (slow.data.id + slow.next.data.id) / 2.0f;
        }
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


