package bank;

import java.util.BitSet;
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
        users.addUser(newAccount);

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

    // task 7: TODO
    public static Bank mergedBank(Bank b1, Bank b2){
        if(b1 == null && b2 == null) throw new IllegalArgumentException("One of Bank must be non-null!!");
        
        Bank nb = new Bank();
        
        int maxId1 = 0;
        int maxId2 = 0;
        java.util.BitSet used = new java.util.BitSet();

        for(Node cur = b1.users.head; cur != null; cur = cur.next){
            if(cur.data.id > maxId1) maxId1 = cur.data.id;
            used.set(cur.data.id);
        }
        for(Node cur = b2.users.head; cur != null; cur = cur.next){
            if(cur.data.id > maxId2) maxId2 = cur.data.id;
            used.set(cur.data.id);
        }

        int maxId = Math.max(maxId1, maxId2);
        nb.nextId = (maxId <= 0)? 1 : maxId + 1;

        java.util.PriorityQueue<Integer> freePool = new java.util.PriorityQueue<>();
        for (int i = 1; i <= maxId; i++) {
            if (!used.get(i)) freePool.offer(i);
        }

        Node n1 = b1.users.head;
        Node n2 = b2.users.head;

        while (n1 != null && n2 != null){
            int id1 = n1.data.id;
            int id2 = n2.data.id;

            if(id1 < id2){
                Account a = n1.data;
                nb.users.addUser(new Account(a.id, a.name, a.address, a.ssn, a.initialDeposit));
                n1 = n1.next;
            } else if(id1 > id2){
                Account a = n2.data;
                nb.users.addUser(new Account(a.id, a.name, a.address, a.ssn, a.initialDeposit));
                n2 = n2.next;
            } else {
                Account a1 = n1.data;
                nb.users.addUser(new Account(a1.id, a1.name, a1.address, a1.ssn, a1.initialDeposit));

                Account a2 = n2.data;

                int newId;
                if(!freePool.isEmpty()){
                    newId = freePool.poll();
                }else {
                    newId = nb.nextId++;
                }
                nb.users.addUser(new Account(newId, a2.name, a2.address, a2.ssn, a2.initialDeposit)); 
            
                n1 = n1.next;
                n2 = n2.next;
            }
        }

        while (n1 != null){
            Account a = n1.data;
            nb.users.addUser(new Account(a.id, a.name, a.address, a.ssn, a.initialDeposit)); 
            n1 = n1.next;
        }
        while (n2 != null){
            Account a = n2.data;
            nb.users.addUser(new Account(a.id, a.name, a.address, a.ssn, a.initialDeposit)); 
            n2 = n2.next;
        }

        return nb;
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

    public Account findUser(int id){
        return users.findById(id);
    }

    
}
