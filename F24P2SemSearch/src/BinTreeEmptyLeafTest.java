// -------------------------------------------------------------------------

import student.TestCase;

/**
 * Test {@link BinTreeEmptyLeaf}.
 *
 *  @author Chang-Yu Huang
 *  @version 2024-10-15
 */
public class BinTreeEmptyLeafTest
    extends TestCase
{

    /** Tested node. */
    private BinTreeNode node;

    /** Index of location used when creating a seminar. */
    private short location = 2;

    /**
     * Reassign the node and clear history.
     */
    public void setUp()
    {
        this.node = BinTreeEmptyLeaf.NODE;
        systemOut().clearHistory();
    }

    /**
     * Test whether the node is a singleton.
     */
    public void testIsSingleton()
    {
        assertEquals(this.node, BinTreeEmptyLeaf.NODE);
    }

    /**
     * Test isLeaf().
     */
    public void testIsLeaf()
    {
        assertTrue(this.node.isLeaf());
    }

    /**
     * Test insert().
     */
    public void testInsert()
    {
        Box world = new Box(new Point(0, 0), new Point(2, 2));
        int id = 2;
        Seminar seminar = new Seminar(2, "1", "1", 1, this.location,
            this.location, 1, null, "1");
        this.node = this.node.insert(world, new Point(1, 1), seminar, false);

        // Because the BinTreeNode interface says that the return value is a
        // BinTreeNode, I can't access data items inside the node.
        // Nor can I assign it to a BinTreeFullLeaf variable. There will be a
        // warning.
        this.node.print(0, 0);
        String printed = systemOut().getHistory();
        assertEquals(String.format("(Leaf with 1 objects: %d)\n", id),
            printed);
    }

    /**
     * Test delete().
     */
    public void testDelete()
    {
        // Nothing should happen.
        assertEquals(this.node, this.node.delete(null, null, null, false));
    }

    /**
     * Test search().
     */
    public void testSearch()
    {
        Seminar seminar = new Seminar();
        Seminar[] seminars = {seminar};

        SearchResult result = this.node.search(null, 0, null, false, seminars);
        assertEquals(seminar, result.getSeminars()[0]);
        assertEquals(1, result.getSeminars().length);
        // Empty leaf also count for visiting.
        assertEquals(1, result.getCount());
    }

    /**
     * Test print().
     */
    public void testPrint()
    {
        this.node.print(2, 1);
        String printed = systemOut().getHistory();
        assertEquals("    (E)\n", printed);

        systemOut().clearHistory();
        this.node.print(0, 0);
        printed = systemOut().getHistory();
        assertEquals("(E)\n", printed);

        systemOut().clearHistory();
        this.node.print(2, 0);
        printed = systemOut().getHistory();
        assertEquals("        (E)\n", printed);
    }

    /**
     * Test packageString()
     */
    public void testPackageString()
    {
        assertEquals("hi", this.node.packageString("hi", 0));
        assertEquals("    hi", this.node.packageString("hi", 1));
    }

    /**
     * Test getHeight()
     */
    public void testGetHeight()
    {
        assertEquals(0, this.node.getHeight());
    }
}
