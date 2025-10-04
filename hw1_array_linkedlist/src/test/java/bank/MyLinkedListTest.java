package bank;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;


public class MyLinkedListTest {
    
    @Test
    void addUser_keepsAscendingById() {
        MyLinkedList list = new MyLinkedList();
        list.addUser(new Account(3, "C","x","111",0));
        list.addUser(new Account(1, "A","x","111",0));
        list.addUser(new Account(2, "B","x","111",0));
        // 1 -> 2 -> 3
        assertEquals(1, list.findById(1).id);
        assertEquals(2, list.findById(2).id);
        assertEquals(3, list.findById(3).id);
        assertNull(list.findById(4));
    }
    
    @Test
    void removeById_handlesHeadMiddleTailAndEarlyStop() {
        MyLinkedList list = new MyLinkedList();
        list.addUser(new Account(1, "A","x","111",0));
        list.addUser(new Account(3, "C","x","111",0));
        list.addUser(new Account(5, "E","x","111",0));
    
        assertTrue(list.removeById(1)); // 刪頭
        assertFalse(list.removeById(2)); // 早停：2 不存在且已超過
        assertTrue(list.removeById(5)); // 刪尾
        assertTrue(list.removeById(3)); // 刪剩下的
        assertNull(list.findById(1));
        assertNull(list.findById(3));
        assertNull(list.findById(5));
    }
    
    @Test
    void getMedianId_oddAndEven() {
        MyLinkedList list = new MyLinkedList();
        list.addUser(new Account(1,"A","x","s",0));
        list.addUser(new Account(3,"B","x","s",0));
        list.addUser(new Account(7,"C","x","s",0));
        assertEquals(3.0f, list.getMedianId(), 1e-6);
    
        list.addUser(new Account(10,"D","x","s",0));
        assertEquals(5.0f, list.getMedianId(), 1e-6); // (3+7)/2
    }
    
}