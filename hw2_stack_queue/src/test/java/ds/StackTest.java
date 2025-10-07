package ds;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.NoSuchElementException;

class StackTest {

    @Test
    public void testPushAndPopOrder() {
        Stack<Integer> stack = new Stack<>();
        stack.push(10);
        stack.push(20);
        stack.push(30);

        // LIFO order
        assertEquals(30, stack.pop());
        assertEquals(20, stack.pop());
        assertEquals(10, stack.pop());
        assertTrue(stack.isEmpty());
    }

    @Test
    public void testPeekDoesNotRemove() {
        Stack<String> stack = new Stack<>();
        stack.push("A");
        stack.push("B");

        assertEquals("B", stack.peek());   // top element
        assertEquals(2, stack.size());     // peek shouldn't remove
        assertEquals("B", stack.pop());    // pop still returns same element
    }

    @Test
    public void testSizeAndIsEmpty() {
        Stack<Integer> stack = new Stack<>();
        assertTrue(stack.isEmpty());
        stack.push(1);
        stack.push(2);
        assertEquals(2, stack.size());
        assertFalse(stack.isEmpty());

        stack.pop();
        assertEquals(1, stack.size());
    }

    @Test
    public void testPopOnEmptyThrows() {
        Stack<Integer> stack = new Stack<>();
        assertThrows(NoSuchElementException.class, stack::pop);
    }

    @Test
    public void testPeekOnEmptyThrows() {
        Stack<Integer> stack = new Stack<>();
        assertThrows(NoSuchElementException.class, stack::peek);
    }
}
