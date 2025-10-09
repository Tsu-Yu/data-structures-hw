package ds;


public class HashTable {
    private final MyLinkedList[] buckets;
    private int count;
    
    public HashTable(int capacity){
        // capacity is the number of buckets, cannot be less than or equal to 0
        if(capacity <= 0) throw new IllegalArgumentException("Capacity must be greater than zero.");
        
        // Initialize buckets
        buckets = new MyLinkedList[capacity];
        for(int i = 0; i < capacity; i++){
            buckets[i] = new MyLinkedList();
        }

        // the number of unique elements in the hashtable
        count = 0;
    }

    // private because it's an internal helper function
    private int hash(String x){
        if(x == null) throw new IllegalArgumentException("Input cannot be null");
        
        // CONSIDERING: using long to avoid overflow
        final int a = 33;   // follow the textbook's suggestion
        int h = 0;

        // Polynomial rolling hash function (Horner's rule)
        for(int i = 0; i < x.length(); i++){
            h = h * a + x.charAt(i);  // CONSIDERING: if % large prime, avoid overflow
        }

        // Ensure non-negative index within bucket array bounds
        return Math.floorMod(h, buckets.length);
    }

    public void insert(String x){
        if(x == null) throw new IllegalArgumentException("Input cannot be null");
        
        // Insert only if x is not already present
        int idx = hash(x);
        if(buckets[idx].addIfAbsent(x)){
            count++;
        }
    }

    public int size(){
        return count;
    }
}
