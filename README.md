# Data Structures and Algorithms in Java

This repository contains my Java implementations of fundamental data structures, organized as separate **Maven projects**.  
Each project demonstrates modular design, **object-oriented principles**, and **test-driven development** using **JUnit 5**.  
The work is inspired by exercises from the textbook *Data Structures & Algorithms in Python*.

---

## Project Overview

| Module | Description | Key Classes |
|:--------|:-------------|:-------------|
| **hw1_array_linkedlist** | Implements array-based and linked-list data structures, including custom node management and a simple banking example. | `MyLinkedList.java`, `Node.java`, `Account.java`, `Bank.java` |
| **hw2_stack_queue** | Stack and queue data structures, including a two-stack queue implementation and an expression evaluator. | `Stack.java`, `Queue.java`, `StackWithTwoQs.java`, `ExprEvaluator.java` |
| **hw3_hashtable** | Hash table implementation supporting collision resolution and basic dictionary operations. Includes a text-based word frequency demo using *Pride and Prejudice*. | `HashTable.java`, `AnagramRoots.java`, `MyLinkedList.java` |
| *(Upcoming)* **hw4_tree** | Binary tree and BST implementations. |
| *(Upcoming)* **hw5_heap** | Min/Max heap and priority queue. |

---

## Tech Stack

- **Language:** Java  
- **Build Tool:** Maven  
- **Testing Framework:** JUnit 5  
- **IDE:** Visual Studio Code  
- **Test Runner Extension:** *Test Runner for Java* by Microsoft   
- **Textbook Reference:** *Data Structures & Algorithms in Python*  

---

## Testing and Validation

Each project includes a dedicated `src/test/java` directory with unit tests that validate correctness, boundary conditions, and performance under different data volumes.  
All tests are run via **JUnit 5** and integrated into the Maven lifecycle (`mvn test`).

**Example test case:**
```java
@Test
    public void testPushAndPopOrder() {
        Stack<Integer> stack = new Stack<>();
        stack.push(10);
        stack.push(20);
        stack.push(30);

        assertEquals(30, stack.pop());
        assertEquals(20, stack.pop());
        assertEquals(10, stack.pop());
        assertTrue(stack.isEmpty());
    }
```

---

## Highlights

- Implemented **core data structures from scratch** with OOP principles  
- Applied **test-driven development** and **modular Maven structure**  
- Designed for **readability, maintainability, and scalability**  
- Demonstrates strong understanding of **time complexity and algorithm design**

---

## Author

**Yu-Tsu Chang**  
M.S. in Software Engineering @ University of California, Irvine  
📍 Irvine, CA  
🔗 [LinkedIn](https://linkedin.com/in/carriechang-uci) | [GitHub](https://github.com/Tsu-Yu)
