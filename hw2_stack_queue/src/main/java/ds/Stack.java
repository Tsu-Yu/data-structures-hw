package ds;

import java.util.NoSuchElementException;

class Node<E> {
    E data;
    Node<E> next;

    Node(E data) { this.data = data; }
}
public class Stack<E> {
    private Node<E> top;
    private int size;

    public void push (E item) {
        Node<E> node = new Node<>(item);
        node.next = top;
        top = node;
        size++;
    }

    public E pop() {
        if(isEmpty()) {
            throw new NoSuchElementException("Stack is empty");
        }
        E val = top.data;
        top = top.next;
        size--;
        return val;
    }

    public E peek() {
        if(isEmpty()) {
            throw new NoSuchElementException("Stack is empty");
        }
        return top.data;
    }

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return (top == null);
    }
}
