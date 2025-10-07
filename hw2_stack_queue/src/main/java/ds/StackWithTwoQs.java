package ds;

import java.util.NoSuchElementException;

public class StackWithTwoQs<E> {
    private final Queue<E> q1;
    private final Queue<E> q2;

    public StackWithTwoQs(){
        this.q1 = new Queue<>();
        this.q2 = new Queue<>();
    }

    public void push(E x) {
        q2.enqueue(x);
        while(q1.size() > 0){
            q2.enqueue(q1.dequeue());
        }
        
        while(q2.size()>0){
            q1.enqueue(q2.dequeue());
        }
    }

    public E pop() {
        if(q1.size() == 0){
            throw new NoSuchElementException("Queue is empty.");
        }
        return q1.dequeue();
    }

    public E peek() {
        if(q1.size() == 0){
            throw new NoSuchElementException("Queue is empty.");
        }
        return q1.poll();
    }

    public int size() {
        return q1.size();
    }

    public boolean isEmpty() {
        return q1.size() == 0;
    }



}