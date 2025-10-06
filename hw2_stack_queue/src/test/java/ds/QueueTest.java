package ds;

import org.junit.jupiter.api.Test;

import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;

public class QueueTest {

    @Test
    void enqueueDequeue_FifoOrder() {
        Queue<Integer> q = new Queue<>();
        q.enqueue(1);
        q.enqueue(2);
        q.enqueue(3);

        assertEquals(1, q.dequeue());
        assertEquals(2, q.dequeue());
        assertEquals(3, q.dequeue());
    }

    @Test
    void pollReturnsHeadWithoutRemoving() {
        Queue<String> q = new Queue<>();
        q.enqueue("A");
        q.enqueue("B");

        // poll() 不移除元素
        assertEquals("A", q.poll());
        assertEquals(2, q.size());
        // dequeue() 仍應該拿到 A
        assertEquals("A", q.dequeue());
        assertEquals("B", q.dequeue());
        assertEquals(0, q.size());
    }

    @Test
    void sizeIncreasesAndDecreasesCorrectly() {
        Queue<Integer> q = new Queue<>();
        assertEquals(0, q.size());

        q.enqueue(10);
        q.enqueue(20);
        assertEquals(2, q.size());

        q.dequeue();
        assertEquals(1, q.size());

        q.dequeue();
        assertEquals(0, q.size());
    }

    @Test
    void dequeueOnEmpty_throws() {
        Queue<Integer> q = new Queue<>();
        assertThrows(NoSuchElementException.class, q::dequeue);
    }

    @Test
    void pollOnEmpty_throws() {
        Queue<Integer> q = new Queue<>();
        assertThrows(NoSuchElementException.class, q::poll);
    }

    @Test
    void manyEnqueues_resizesLogically_keepsOrder() {
        Queue<Integer> q = new Queue<>();
        int n = 1000;
        for (int i = 1; i <= n; i++) q.enqueue(i);
        assertEquals(n, q.size());

        for (int i = 1; i <= n; i++) {
            assertEquals(i, q.dequeue());
        }
        assertEquals(0, q.size());
        // 再 enqueue/dequeue 一次，確保 head/tail 在清空後仍正常
        q.enqueue(42);
        assertEquals(42, q.dequeue());
    }
}
