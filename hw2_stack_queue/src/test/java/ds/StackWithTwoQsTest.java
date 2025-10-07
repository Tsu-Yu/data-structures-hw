package ds;

import org.junit.jupiter.api.Test;
import java.util.NoSuchElementException;
import static org.junit.jupiter.api.Assertions.*;

class StackWithTwoQsTest {

    @Test
    void push_pop_lifo() {
        StackWithTwoQs<Integer> s = new StackWithTwoQs<>();
        s.push(1); s.push(2); s.push(3);
        assertEquals(3, s.pop());
        assertEquals(2, s.pop());
        assertEquals(1, s.pop());
        assertTrue(s.isEmpty());
    }

    @Test
    void peek_does_not_remove() {
        StackWithTwoQs<String> s = new StackWithTwoQs<>();
        s.push("A"); s.push("B");
        assertEquals("B", s.peek());
        assertEquals(2, s.size());
        assertEquals("B", s.pop());
        assertEquals("A", s.pop());
    }

    @Test
    void size_and_isEmpty() {
        StackWithTwoQs<Integer> s = new StackWithTwoQs<>();
        assertTrue(s.isEmpty());
        s.push(10);
        assertEquals(1, s.size());
        s.push(20);
        assertEquals(2, s.size());
        s.pop();
        assertEquals(1, s.size());
        assertFalse(s.isEmpty());
    }

    @Test
    void pop_peek_on_empty_throw() {
        StackWithTwoQs<Integer> s = new StackWithTwoQs<>();
        assertThrows(NoSuchElementException.class, s::pop);
        assertThrows(NoSuchElementException.class, s::peek);
    }

    @Test
    void many_elements_stability() {
        StackWithTwoQs<Integer> s = new StackWithTwoQs<>();
        for (int i = 1; i <= 1000; i++) s.push(i);
        for (int i = 1000; i >= 1; i--) assertEquals(i, s.pop());
        assertTrue(s.isEmpty());
    }
}
