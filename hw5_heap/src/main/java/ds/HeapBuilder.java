package ds;

public class HeapBuilder {
    public Node createMinHeap(List<Integer> values){
        createHeap(values, true); // true for min-heap
    }

    public Node createMaxHeap(List<Integer> values){
        createHeap(values, false); // false for max-heap
    }

    // use heapify to build heap
    private Node createHeap(List<Integer> values, boolean isMin){
        if(values == null || values.size() == 0) return null;

        int n = values.size();
        int[] a = new int[n];
        for(int i = 0; i < n; i++){ 
            a[i] = values.get(i); 
        }

        buildHeap(a, isMin);

        List<Node> nodes = new ArrayList<>();
        for(int val : a){
            nodes.add(new Node(val));
        }

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

        return nodes[0]; // root
    }

    private void buildHeap(int[] a, boolean isMin){
        for(int i = a.length / 2 - 1; i >= 0; i--){
            heapify(a, n, i, isMin);
        }
    }

    private void heapify(int[] a, int n, int i, boolean isMin){
        while(true) {
            int left = 2 * i + 1; right = 2 * i + 2;
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

    private boolean better(int a, int b, boolean isMin){
        return isMin ? (a <= b) : (a >= b);
        // if isMin is true, we want parent <= child
        // if isMin is false, we want parent >= child
    }


}
