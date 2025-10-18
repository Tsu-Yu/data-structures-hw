package ds;
import java.util.List; 
import java.util.ArrayList;

public class HeapBuilder {
    public Node createMinHeap(List<Integer> values){
        return createHeap(values, true); // true for min-heap
    }

    public Node createMaxHeap(List<Integer> values){
        return createHeap(values, false); // false for max-heap
    }

    // use heapify to build heap
    private Node createHeap(List<Integer> values, boolean isMin){
        if(values == null || values.isEmpty()) return null;

        int n = values.size();
        int[] a = new int[n];
        for(int i = 0; i < n; i++){ 
            a[i] = values.get(i); 
        }

        buildHeap(a, isMin);  // heapify O(n)

        List<Node> nodes = new ArrayList<>();
        for(int v : a) nodes.add(new Node(v));

        for(int i = 0; i < n; i++){
            int leftIdx = 2 * i + 1;
            int rightIdx = 2 * i + 2;
            if(leftIdx < n){
                nodes.get(i).left = nodes.get(leftIdx);
            }
            if(rightIdx < n){
                nodes.get(i).right = nodes.get(rightIdx);
            }
        }

        return nodes.get(0); // root
    }

    private void buildHeap(int[] a, boolean isMin){
        for(int i = (a.length / 2) - 1; i >= 0; i--){
            heapify(a, a.length, i, isMin);
        }
    }

    private void heapify(int[] a, int n, int i, boolean isMin){
        while(true) {
            int left = 2 * i + 1; 
            int right = 2 * i + 2;
            int target = i;

            if (left < n && better(a[left], a[target], isMin)) {
                target = left;
            }
            if (right < n && better(a[right], a[target], isMin)) {
                target = right;
            }

            if (target == i) break;

            // swap
            int temp = a[i];
            a[i] = a[target];
            a[target] = temp;
            i = target;
        }
    }

    private boolean better(int child, int parent, boolean isMin){
        // if isMin is true, we want parent <= child
        // if isMin is false, we want parent >= child
        return isMin ? (child < parent) : (child > parent);
    }


}
