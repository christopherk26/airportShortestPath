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

/** An interface for the ADT stack. */
public interface StackInterface<T> {
    /**
     * Adds a new entry to the top of this stack.
     * 
     * @param newEntry An object to be added to the stack.
     */
    public void push(T newEntry);

    /**
     * Removes and returns this stack's top entry.
     * 
     * @return The object at the top of the stack.
     * @throws EmptyStackException if the stack is empty before the operation.
     */
    public T pop();

    /**
     * Retrieves this stack's top entry.
     * 
     * @return The object at the top of the stack.
     * @throws EmptyStackException if the stack is empty.
     */
    public T peek();

    /**
     * Detects whether this stack is empty.
     * 
     * @return True if the stack is empty.
     */
    public boolean isEmpty();

    /** Removes all entries from this stack. */
    public void clear();
} // end StackInterface