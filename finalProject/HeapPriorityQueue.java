
//
// Name:       Kurdoghlian, Christopher
// Project:    4
// Due:        April 30, 2024
// Course:     cs-2400-03-sp24
//
// Description:
//             In this project we create an airport app where we can find the optimal path between
//             two airports, given their names and locations. We can also query Airports using their code. 
//
import java.util.Arrays;

public class HeapPriorityQueue<T extends Comparable<? super T>> implements PriorityQueueInterface<T> {

    private T[] heap; // Array of heap entries
    private int lastIndex; // Index of last entry
    int rootIndex = 1;
    private boolean integrityOK = false;
    private static final int DEFAULT_CAPACITY = 25;
    private static final int MAX_CAPACITY = 10000;

    public HeapPriorityQueue() {
        this(DEFAULT_CAPACITY); // Call next constructor
    } // end default constructor

    public HeapPriorityQueue(int initialCapacity) {
        // Is initialCapacity too small?
        if (initialCapacity < DEFAULT_CAPACITY)
            initialCapacity = DEFAULT_CAPACITY;
        else // Is initialCapacity too big?
            checkCapacity(initialCapacity);

        // The cast is safe because the new array contains all null entries
        @SuppressWarnings("unchecked")
        T[] tempHeap = (T[]) new Comparable[initialCapacity + 1];
        heap = tempHeap;
        lastIndex = 0;
        integrityOK = true;
    } // end constructor

    @Override
    public void add(T newEntry) {
        checkIntegrity(); // Ensure initialization of data fields
        int newIndex = lastIndex + 1;
        int parentIndex = newIndex / 2;
        while ((parentIndex > 0) && newEntry.compareTo(heap[parentIndex]) > 0) {
            heap[newIndex] = heap[parentIndex];
            newIndex = parentIndex;
            parentIndex = newIndex / 2;
        } // end while
        heap[newIndex] = newEntry;
        lastIndex++;
        ensureCapacity();
    } // end add

    private void reheap(int rootIndex) {
        boolean done = false;
        T orphan = heap[rootIndex];
        int leftChildIndex = 2 * rootIndex;
        while (!done && (leftChildIndex <= lastIndex)) {
            int largerChildIndex = leftChildIndex; // Assume larger
            int rightChildIndex = leftChildIndex + 1;
            if ((rightChildIndex <= lastIndex) &&
                    heap[rightChildIndex].compareTo(heap[largerChildIndex]) > 0) {
                largerChildIndex = rightChildIndex;
            } // end if
            if (orphan.compareTo(heap[largerChildIndex]) < 0) {
                heap[rootIndex] = heap[largerChildIndex];
                rootIndex = largerChildIndex;
                leftChildIndex = 2 * rootIndex;
            } else
                done = true;
        } // end while
        heap[rootIndex] = orphan;
    } // end reheap

    @Override
    public T peek() {
        checkIntegrity();
        T root = null;
        if (!isEmpty())
            root = heap[1];
        return root;
    }

    @Override
    public boolean isEmpty() {
        return lastIndex < 1;
    }

    @Override
    public int getSize() {
        return lastIndex;
    }

    @Override
    public void clear() {
        checkIntegrity();
        while (lastIndex > -1) {
            heap[lastIndex] = null;
            lastIndex--;
        } // end while
        lastIndex = 0;
    }

    // Throws an exception if the client requests a capacity that is too large.
    private void checkCapacity(int capacity) {
        if (capacity > MAX_CAPACITY)
            throw new IllegalStateException("Attempt to create a bag whose " +
                    "capacity exeeds allowed " +
                    "maximum of " + MAX_CAPACITY);
    } // end checkCapacity

    private void checkIntegrity() {
        if (!integrityOK)
            throw new SecurityException("ArrayBag object is corrupt.");
    } // end checkIntegrity

    // Ensures the capacity, does both isArrayFull and doubleCapacity together.
    private void ensureCapacity() {
        if (lastIndex >= heap.length - 1) // If array is full, double its size
        {
            int newLength = 2 * heap.length;
            checkCapacity(newLength);
            heap = Arrays.copyOf(heap, newLength);
        } // end if
    } // end ensureCapacity

    @Override
    public T remove() {
        checkIntegrity(); // Ensure initialization of data fields
        T root = null;
        if (!isEmpty()) {
            root = heap[1]; // Return value
            heap[1] = heap[lastIndex]; // Form a semiheap
            lastIndex--; // Decrease size
            reheap(1); // Transform to a heap
        } // end if
        return root;
    } // end removeMax

}