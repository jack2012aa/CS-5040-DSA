// -------------------------------------------------------------------------
/**
 * A class managing free blocks in the memory manager.
 *
 *  @author Chang-Yu Huang
 *  @version 2024-11-10
 */
public class FreeBlockManager
{

    /**
    /**
     *  A doubly linked node representing a block (using Handle class).
     *  @author Chang-Yu Huang
     *  @version 2024-11-11
     */
    private class Node
    {
        /** The handle representing the block. */
        private Handle handle;
        /** Next node. */
        private Node next;
        /** Previous node. */
        private Node previous;

        /**
         * Create a node with a handle.
         * @param handle Handle representing the block
         */
        public Node(Handle handle)
        {
            this.handle = handle;
            this.next = null;
            this.previous = null;
        }

    }

    //~ Fields ................................................................
    /** A dummy head. */
    private Node head;
    /** A dummy tail. */
    private Node tail;
    /** Number of free blocks. */
    private int length;

    //~ Constructors ..........................................................
    /**
     * Create a manager with an initial size of the first free block.
     * @param size Size of the first free block.
     */
    public FreeBlockManager(int size)
    {
        // Create a doubly linked list.
        this.head = new Node(null);
        this.tail = new Node(null);
        Node block = new Node(new Handle(0, size));
        this.head.next = block;
        this.tail.previous = block;
        block.previous = this.head;
        block.next = this.tail;
        this.length = 1;
    }

    //~Public  Methods ........................................................
    /**
     * Merge overlapping blocks.
     */
    private void merge()
    {

        // Check the current node and its previous node.
        Node current = this.head.next.next;
        while (current != this.tail)
        {
            if (current.previous.handle.getEnd() >= current.handle.getStart())
            {
                // Handle the case that the current handle is actually in the
                // previous handle.
                // This case shouldn't happen if this class is used properly.
                if (current.handle.getEnd() > current.previous.handle.getEnd())
                {
                    int mergedSize = current.handle.getEnd()
                        - current.previous.handle.getStart();
                    Handle mergedHandle = new Handle(
                        current.previous.handle.getStart(),
                        mergedSize);
                    current.previous.handle = mergedHandle;
                }

                // Remove the current node.
                current.previous.next = current.next;
                current.next.previous = current.previous;
                this.length--;
            }
            current = current.next;
        }
    }

    /**
     * Add a new free block.
     * @param handle The new free block.
     */
    public void free(Handle handle)
    {

        // New node.
        Node node = new Node(handle);

        // Find the position to insert.
        Node current = this.head.next;
        while (current != this.tail
            && current.handle.getStart() < handle.getStart())
        {
            current = current.next;
        }

        // Connect the new node to the previous of the current node.
        node.previous = current.previous;
        node.next = current;
        current.previous.next = node;
        current.previous = node;
        this.length++;

        // Merge.
        this.merge();

    }

    /**
     * Finding is there a free space large enough.
     * @param size Size to allocate.
     * @return Has a large-enough free block.
     */
    public boolean hasSpace(int size)
    {

        // Find an appropriate block.
        Node current = this.head.next;
        while (current != this.tail && current.handle.getSize() < size)
        {
            current = current.next;
        }
        return current != this.tail;

    }

    /**
     * Allocate a space and return used space.
     * If no space can use, return null.
     * @param size Size to allocate.
     * @return Handle representation of the allocated space.
     */
    public Handle allocate(int size)
    {

        // Find an appropriate block.
        Node current = this.head.next;
        while (current != this.tail && current.handle.getSize() < size)
        {
            current = current.next;
        }

        if (current == this.tail) {
            return null;
        }

        Handle allocated = new Handle(current.handle.getStart(), size);

        if (size == current.handle.getSize())
        {
            // No remain memory in this block.
            this.length--;
            current.previous.next = current.next;
            current.next.previous = current.previous;
        }
        else
        {
            // Update remain memory in this block.
            current.handle.setSize(
                current.handle.getEnd() - allocated.getEnd());
            current.handle.setStart(allocated.getEnd());
        }

        return allocated;

    }

    /**
     * Print the linked list.
     */
    public void print()
    {

        if (this.length == 0)
        {
            System.out.println("There are no freeblocks in the memory pool.");
            return;
        }

        // Use String.join.
        String[] blocks = new String[this.length];
        Node current = this.head.next;
        for (int i = 0; i < this.length; i++)
        {
            blocks[i] = current.handle.toString();
            current = current.next;
        }
        System.out.println(String.join(" -> ", blocks));

    }

}
