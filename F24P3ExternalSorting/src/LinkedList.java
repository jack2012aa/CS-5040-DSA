// -------------------------------------------------------------------------

import java.util.Iterator;

/**
 * A linked list.
 *
 *  @author Chang-Yu Huang
 *  @version 2024-10-30
 *  @param <T> A data class.
 */
public class LinkedList<T> implements Iterable<T>
{
    //~ Fields ................................................................
    /** Head of the list. */
    private ListNode<T> head;
    /** Tail of the list. */
    private ListNode<T> tail;
    /** Number of element in the list. */
    private int size;

    private class LinkedListIterator implements Iterator<T>
    {

        /** Current position in the list. */
        private ListNode<T> currentNode = head.getNext();
        // ----------------------------------------------------------
        /**
         * {@inheritDoc}
         */
        @Override
        public boolean hasNext()
        {
            return currentNode != tail;
        }

        // ----------------------------------------------------------
        /**
         * {@inheritDoc}
         */
        @Override
        public T next()
        {
            if (currentNode == null) {
                throw new IllegalStateException("No more elements");
            }
            // Save the data for return.
            T data = currentNode.getData();
            currentNode = currentNode.getNext();
            return data;
        }

    }

    //~ Constructors ..........................................................
    /**
     * Create an empty list.
     */
    public LinkedList()
    {
        this.head = new ListNode<T>(null);
        this.tail = new ListNode<T>(null);
        this.head.setNext(this.tail);
        this.size = 0;
    }

    //~Public  Methods ........................................................
    /**
     * Insert a new element to the tail of the list.
     * @param data The new element.
     */
    public void insertTail(T data)
    {
        this.tail.setData(data);
        this.tail.setNext(new ListNode<T>(null));
        this.tail = this.tail.getNext();
        this.size++;
    }

    /**
     * Insert a list to the tail of this list
     * @param list List to be inserted.
     */
    public void insertTail(LinkedList<T> list)
    {
        for (T data: list)
        {
            this.insertTail(data);
        }
    }

    /**
     * Remove and return the first element.
     * @return The first element.
     */
    public T popFront()
    {
        if (this.size == 0)
        {
            return null;
        }
        T data = this.head.getNext().getData();
        this.head.setNext(this.head.getNext().getNext());
        this.size--;
        return data;
    }

    /**
     * Get the number of elements in the list.
     * @return Number of elements in the list.
     */
    public int getSize()
    {
        return this.size;
    }

    @Override
    public Iterator<T> iterator()
    {
        return new LinkedListIterator();
    }


}
