// -------------------------------------------------------------------------

import java.util.Iterator;
import student.TestCase;

/**
 * Test {@link DoubleLinkedList}
 *
 *  @author Chang-Yu Huang
 *  @version 2024-09-04
 */
public class DoubleLinkedListTest
    extends TestCase
{
    //~ Fields ................................................................
    /** Tested list. */
    private DoubleLinkedList list;
    private Node[] data = {new Node(0), new Node(1), new Node(2), new Node(3)};

    //~Public  Methods ........................................................
    /** Setup list and add some data. */
    public void setUp()
    {
        this.list = new DoubleLinkedList();
        for (Node node: this.data)
        {
            this.list.addNodeToRear(node);
        }
    }

    /**
     * Test hasNode()
     */
    public void testHasNode()
    {

        // Exist nodes.
        for (Node node: this.data)
        {
            assertTrue(this.list.hasNode(node));
        }

        // New node.
        assertFalse(this.list.hasNode(new Node(0)));

    }

    /**
     * Test addNodeToRear() by:
     * 1. Check head.
     * 2. Check tail.
     * 3. Check size.
     */
    public void testAddNodeToRear()
    {

        this.list = new DoubleLinkedList();

        // Insert the first node will change head and tail.
        this.list.addNodeToRear(this.data[0]);
        assertEquals(this.data[0], this.list.getHead());
        assertEquals(this.data[0], this.list.getTail());
        assertEquals(1, this.list.getSize());

        // Insert other node will only change the tail.
        this.list.addNodeToRear(this.data[1]);
        assertEquals(this.data[0], this.list.getHead());
        assertEquals(this.data[1], this.list.getTail());
        assertEquals(2, this.list.getSize());

    }

    /**
     * Test removeFromRear().
     */
    public void testRemoveFromRear()
    {
        for (int i = 0; i < this.data.length; i++)
        {
            this.list.removeFromRear();
        }
        // Nothing will happen.
        this.list.removeFromRear();
        assertEquals(0, this.list.getSize());
    }

    /**
     * Test removeNode() by:
     * 1. Remove a node doesn't exist.
     * 2. Remove middle.
     * 3. Remove tail.
     * 4. Remove head.
     * 5. Remove duplicate node.
     * 6. Remove the last node.
     */
    public void testRemoveNode()
    {

        // Remove node doesn't exist
        assertFalse(this.list.removeNode(new Node(0)));

        // Remove middle.
        assertTrue(this.list.removeNode(this.data[1]));
        assertEquals(this.data[0], this.list.getHead());
        assertEquals(this.data[this.data.length - 1], this.list.getTail());
        assertEquals(this.data.length - 1, this.list.getSize());
        assertFalse(this.list.hasNode(this.data[1]));

        // Check connection.
        assertEquals(this.data[2], this.list.getNode(1));
        assertEquals(this.data[3], this.list.getNode(2));

        // Remove tail.
        assertTrue(this.list.removeNode(this.data[this.data.length - 1]));
        assertTrue(this.list.getTail() != this.data[this.data.length - 1]);
        assertFalse(this.list.hasNode(this.data[this.data.length - 1]));

        // Remove head.
        assertTrue(this.list.removeNode(this.data[0]));
        assertTrue(this.list.getHead() != this.data[0]);
        assertFalse(this.list.hasNode(this.data[0]));

        // Remove duplicate node.
        this.list.addNodeToRear(this.data[2]);
        assertTrue(this.list.removeNode(this.data[2]));
        assertTrue(this.list.hasNode(this.data[2]));

        // Remove the last node.
        this.list.removeNode(this.data[2]);
        assertNull(this.list.getHead());
        assertNull(this.list.getTail());

    }

    /**
     * Test getNode() by passing index which is out of bounds.
     */
    public void testGetNode()
    {
        try
        {
            this.list.getNode(-1);
            fail("Should throw an exception");
        }
        catch (IndexOutOfBoundsException ex)
        {
            // Pass the test.
        }

        try
        {
            this.list.getNode(this.data.length);
            fail("Should throw an exception");
        }
        catch (IndexOutOfBoundsException ex)
        {
            // Pass the test.
        }

    }

    /**
     * Test iterator.
     */
    public void testIterator()
    {
        Iterator<Node> iterator = this.list.iterator();
        assertTrue(iterator.hasNext());
        for (Node node: this.data)
        {
            assertEquals(node, iterator.next());
        }
        assertFalse(iterator.hasNext());
        try
        {
            iterator.next();
            fail("Should throw an exception.");
        }
        catch (IllegalStateException ex)
        {
            // Pass the test.
        }
    }

}
