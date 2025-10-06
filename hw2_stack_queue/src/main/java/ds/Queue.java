package ds;

import java.util.NoSuchElementException;

class Node<E> {
    E data;
    Node<E> next;

    Node (E data){ this.data = data; }
}
public class Queue<E> {
    private Node<E> head;
    private Node<E> tail;
    private int size;

    public void enqueue(E item){
        Node<E> node = new Node<>(item);
        if (tail == null){
            head = tail = node;
        } else {
            tail.next = node;
            tail = node;
        }
        size++;
    }

    public E dequeue(){
        if (head == null) throw new NoSuchElementException("Queue is empty");
        E val = head.data;
        head = head.next;
        if(head == null){
            tail = null;
        }
        size--;
        return val;
    }

    public E poll(){
        if (head == null) throw new NoSuchElementException("Queue is empty");
        return head.data;
    }
    public int size(){
        return size;
    }
    
}
