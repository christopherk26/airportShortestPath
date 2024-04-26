//
// Name:       Kurdoghlian, Christopher
// Project:    2
// Due:        3/14/2024
// Course:     cs-2400-03-sp24
//
// Description:
//             In this project I create an ADT stack, implement it using 
//             the array in ArrayStack, and then create a class Expression
//             with class methods that allow conversion of an array of strings
//             into postfix notation, and then evaluate that postfix expression.
//             Doing so requires the stack. In Expresesion test, command line
//             parameters are read in, converted to postfix, then evaluated and printed.
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