package bank;

import java.util.NoSuchElementException;
import java.util.PriorityQueue;

public class Bank {
    private final MyLinkedList users = new MyLinkedList();
    private final PriorityQueue<Integer> freedIds = new PriorityQueue<>();
    private int nextId = 1;
    
    // task 2
    public int addUser(String name, String address, String ssn, double initialDeposit){
        validateNewUser(name, address, ssn, initialDeposit);

        final int id = !freedIds.isEmpty()? freedIds.poll() : nextId++;

        Account newAccount = new Account(id, name, address, ssn, initialDeposit);
        users.addSorted(newAccount);

        return id;  
    }

    // task 3
    public boolean deleteUser(int id){
        if(id < 0) return false;

        boolean removed = users.removeById(id);

        if(removed){
            freedIds.offer(id);
            return true;
        }else {
            return false;
        }
    }

    // task 4
    public boolean payUserToUser(int payerId, int payeeId, double amount) {
        Account payer = users.findById(payerId);
        Account payee = users.findById(payeeId);

        if (payer == null || payee == null){
            return false;
        }
        if(payer.initialDeposit < amount || amount < 0){
            return false;
        } 
        payer.initialDeposit -= amount;
        payee.initialDeposit += amount;
        return true;
    }

    // task 5
    public float getMedianId(){
        return users.getMedianId();
    }

    // task 6
    public boolean mergeAccounts(int id1, int id2){

        if (id1 == id2){
            throw new NoSuchElementException("Same account ID; nothing to merge.");
        }

        Account a1 = users.findById(id1);
        Account a2 = users.findById(id2);
        if (a1 == null || a2 == null){
            throw new NoSuchElementException("One of accounts doesn't exist!!");
        } 

        if (!a1.name.equals(a2.name) || !a1.address.equals(a2.address) || !a1.ssn.equals(a2.ssn)){
            throw new IllegalArgumentException("Accounts are not owned by the same person.");
        }

        double newBalance = a1.initialDeposit + a2.initialDeposit;

        int keepId = Math.min(id1, id2);
        int dropId = Math.max(id1, id2);

        Account keepAcc = (keepId == id1)? a1 : a2;
        keepAcc.initialDeposit = newBalance;
        
        boolean removed = users.removeById(dropId);
        if(!removed) return false;    
        freedIds.offer(dropId);
        
        return true;
    }

    // for task 2
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

    public void printUsers(){
        users.printList();
    }

    
}
