// -------------------------------------------------------------------------

import java.util.Iterator;

/**
 * A class of double linked list.
 *
 *  @author Chang-Yu Huang
 *  @version 2024-09-04
 */
public class DoubleLinkedList
    implements Iterable<Node>
{
    //~ Fields ................................................................

    /**
     *  Node in the double linked list.
     *
     *  @author Chang-Yu Huang
     *  @version 2024-09-03
     */
    private class DoubleLinkedListNode
    {
        /** Stored data. */
        private Node data;
        /** Previous node. */
        private DoubleLinkedListNode previous;
        /** Next node. */
        private DoubleLinkedListNode next;

        public DoubleLinkedListNode(
            Node data,
            DoubleLinkedListNode previous,
            DoubleLinkedListNode next
        )
        {
            this.data = data;
            this.previous = previous;
            this.next = next;
        }
    }

    /**
     * Iterator of DoubleLinkedList.
     *
     *  @author Chang-Yu Huang
     *  @version 2024-09-04
     */
    private class DoubleLinkedListIterator implements Iterator<Node>
    {

        /** Current position in the list. */
        private DoubleLinkedListNode currentNode = head;
        // ----------------------------------------------------------
        /**
         * {@inheritDoc}
         */
        @Override
        public boolean hasNext()
        {
            return currentNode != null;
        }

        // ----------------------------------------------------------
        /**
         * {@inheritDoc}
         */
        @Override
        public Node next()
        {
            if (currentNode == null) {
                throw new IllegalStateException("No more elements");
            }
            // Save the data for return.
            Node node = currentNode.data;
            currentNode = currentNode.next;
            return node;
        }

    }

    /** Head of the list. */
    private DoubleLinkedListNode head;
    /** Tail of the list. */
    private DoubleLinkedListNode tail;
    /** Number of nodes in the list. */
    private int size;

    //~ Constructors ..........................................................
    /**
     * Create a empty double link list.
     */
    public DoubleLinkedList()
    {
        this.head = null;
        this.tail = null;
        this.size = 0;
    }

    /**
     * Create a double link list whose head store data.
     * @param node Data to be stored in the list head.
     */
    public DoubleLinkedList(Node node)
    {
        this.head = new DoubleLinkedListNode(node, null, null);
        this.tail = head;
        this.size = 1;
    }

    //~Public  Methods ........................................................
    /**
     * Check is node exist in the list.
     * @param node Node to be checked.
     * @return True if exist, else false.
     */
    public boolean hasNode(Node node)
    {

        // Iterator
        DoubleLinkedListNode currentNode = this.head;
        while (currentNode != null)
        {
            if (currentNode.data == node)
            {
                return true;
            }
            currentNode = currentNode.next;
        }
        return false;

    }

    /**
     * Add a new node to the tail of the list.
     * @param node Node to be added.
     */
    public void addNodeToRear(Node node)
    {

        this.size++;

        // If the list is empty, set the node as the head.
        if (this.head == null)
        {
            this.head = new DoubleLinkedListNode(node, null, null);
            this.tail = head;
            return;
        }

        DoubleLinkedListNode newNode
            = new DoubleLinkedListNode(node, this.tail, null);
        this.tail.next = newNode;
        this.tail = newNode;

    }

    /**
     * Remove the last node in the linked list.
     */
    public void removeFromRear() {

        if (this.tail == null)
        {
            return;
        }

        this.tail = this.tail.previous;
        this.size--;

    }

    /**
     * Remove the first encountered node in the list.
     * @param node Node to be removed.
     * @return True if the node exists, else false.
     */
    public boolean removeNode(Node node)
    {

        // Iterator
        DoubleLinkedListNode currentNode = this.head;
        while (currentNode != null)
        {
            if (currentNode.data == node)
            {
                // Cases: head, tail, head and tail, middle
                if (this.head == this.tail)
                {
                    this.head = null;
                    this.tail = null;
                }
                else if (currentNode == this.head)
                {
                    this.head = currentNode.next;
                    this.head.previous = null;
                }
                else if (currentNode == this.tail)
                {
                    this.tail = currentNode.previous;
                    this.tail.next = null;
                }
                else
                {
                    currentNode.previous.next = currentNode.next;
                    currentNode.next.previous = currentNode.previous;
                }
                this.size--;
                return true;
            }
            currentNode = currentNode.next;
        }
        return false;

    }

    /**
     * Get the node in index.
     * @param index Index wanted to get.
     * @return A Node.
     * @throws IndexOutOfBoundsException If index out of range.
     */
    public Node getNode(int index) throws IndexOutOfBoundsException
    {
        if (index < 0 || index >= this.size)
        {
            String errorString = String.format(
                "Index out of bounds. Got %d.", index);
            throw new IndexOutOfBoundsException(errorString);
        }

        DoubleLinkedListNode currentNode = this.head;
        for (int i = 0; i < index; i++)
        {
            currentNode = currentNode.next;
        }
        return currentNode.data;

    }

    /**
     * Get the head node of the list.
     * @return The head of the list, or null.
     */
    public Node getHead()
    {
        if (this.head == null)
        {
            return null;
        }
        return this.head.data;
    }

    /**
     * Get the tail node of the list.
     * @return The tail of the list, or null.
     */
    public Node getTail()
    {
        if (this.tail == null)
        {
            return null;
        }
        return this.tail.data;
    }

    /**
     * Get the size of the list.
     * @return Size of the list.
     */
    public int getSize()
    {
        return this.size;
    }

    // ----------------------------------------------------------
    /**
     * {@inheritDoc}
     */
    @Override
    public Iterator<Node> iterator()
    {
        return new DoubleLinkedListIterator();
    }


}
