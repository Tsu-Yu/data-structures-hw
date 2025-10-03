package bank;

import java.util.PriorityQueue;

public class Bank {
    private final MyLinkedList users = new MyLinkedList();
    private final PriorityQueue<Integer> freedIds = new PriorityQueue<>();
    private int nextId = 1;
    
    public int addUser(String name, String address, String ssn, double initialDeposit){
        validateNewUser(name, address, ssn, initialDeposit);

        final int id = !freedIds.isEmpty()? freedIds.poll() : nextId++;

        Account newAccount = new Account(id, name, address, ssn, initialDeposit);
        users.addSorted(newAccount);

        return id;
        
    }

    private static void validateNewUser(String name, String address, String ssn, double initialDeposit){
        StringBuilder errors = new StringBuilder();

        if(name == null || name.isBlank()) errors.append("name required; ");
        if(address == null || address.isBlank()) errors.append("address required; ");
        if(ssn == null || ssn.isBlank()) errors.append("ssn required; ");
        if(initialDeposit < 0) errors.append("initial deposit must be >= 0; ");

        if(!errors.isEmpty()){
            throw new IllegalArgumentException(errors.toString().trim());
        }
    }

    
}
