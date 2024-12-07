// -------------------------------------------------------------------------

import student.TestCase;

/**
 * Test {@link BinTreeFullLeaf}.
 *
 *  @author Chang-Yu Huang
 *  @version 2024-10-14
 */
public class BinTreeFullLeafTest
    extends TestCase
{

    /** x coordinate of the leaf. */
    private short x = 3;

    /** y coordinate of the leaf. */
    private short y = 4;

    /** Location of the leaf. */
    private Point location = new Point(this.x, this.y);

    /** Seminars to be stored in the leaf. */
    private Seminar[] seminars = {
        new Seminar(1, "1", "1", 1, this.x, this.y, 1, null, "1"),
        new Seminar(2, "1", "1", 1, this.x, this.y, 1, null, "1"),
        new Seminar(3, "1", "1", 1, this.x, this.y, 1, null, "1"),
    };

    /** Tested leaf. */
    private BinTreeFullLeaf leaf;

    /**
     * Create a leaf with a seminar inside.
     */
    public void setUp()
    {
        this.leaf = new BinTreeFullLeaf(location, this.seminars[0]);
        systemOut().clearHistory();
    }

    /**
     * Test constructor.
     */
    public void testConstructor()
    {
        // Check data items inside.
        assertEquals(location, this.leaf.getPoint());
        assertEquals(this.seminars[0], this.leaf.getSeminars()[0]);
        assertEquals(1, this.leaf.getSeminars().length);
    }

    /**
     * Test isLeaf().
     */
    public void testIsLeaf()
    {
        assertTrue(this.leaf.isLeaf());
    }

    /**
     * Test insert() through:
     * 1. Insert seminars in same location.
     * 2. Insert a seminar in a different half of the world.
     * 3. Insert a seminar which needs multiple splitting.
     */
    public void testInsert()
    {

        Box world = new Box(new Point(0, 0), new Point(15, 15));
        BinTreeNode root = this.leaf;

        // Insert seminars in same location.
        for (int i = this.seminars.length - 1; i > 0; i--)
        {
            root = root.insert(world, location, this.seminars[i], true);
        }
        // Every seminar is in the leaf and sorted.
        assertEquals(this.leaf, root);
        assertEquals(this.seminars.length, this.leaf.getSeminars().length);
        for (int i = 0; i < this.seminars.length; i++)
        {
            assertEquals(this.seminars[i], this.leaf.getSeminars()[i]);
        }

        // Insert a seminar in a different half of the world.
        Seminar seminar = new Seminar(4, "1", "1", 1, this.x, this.y,
            1, null, "1");
        root = root.insert(world, new Point(10, 4), seminar, true);
        systemOut().clearHistory();
        /*
         *    (I)
         *   /   \
         *  3, 4  10, 4
         */
        root.print(1, 0);
        String printed = systemOut().getHistory();
        assertEquals(
            "    (I)\n(Leaf with 1 objects: 4)\n(Leaf with 3 objects: 1 2 3)"
            + "\n", printed);

        // Insert a seminar which needs multiple splitting.
        // Reset root.
        root = this.leaf;
        root = root.insert(world, new Point(3, 1), seminar, true);
        /*
         *              (I)
         *             /   \
         *           (I)   (E)
         *          /   \
         *         (I)  (E)
         *        /   \
         *       (I)  (E)
         *      /   \
         *    3, 1  3, 4
         */
        systemOut().clearHistory();
        root.print(4, 0);
        printed = systemOut().getHistory();
        assertEquals(
            "                (I)\n"
            + "            (E)\n"
            + "            (I)\n"
            + "        (E)\n"
            + "        (I)\n"
            + "    (E)\n"
            + "    (I)\n"
            + "(Leaf with 3 objects: 1 2 3)\n"
            + "(Leaf with 1 objects: 4)\n",
            printed);

    }

    /**
     * Test delete() through:
     * 1. Remove a seminar which doesn't exist.
     * 2. Remove a seminar from many.
     * 3. Remove a seminar and let the leaf become empty.
     */
    public void testDelete()
    {

        // Remove a seminar which doesn't exist.
        // delete() doesn't use parameters except of seminar.
        this.leaf.delete(null, this.location, new Seminar(), true);
        assertEquals(location, this.leaf.getPoint());
        assertEquals(this.seminars[0], this.leaf.getSeminars()[0]);

        // Remove a seminar from many.
        this.leaf.insert(null, location, this.seminars[2], true);
        this.leaf.insert(null, location, this.seminars[1], true);
        this.leaf.delete(null, location, this.seminars[1], true);
        assertEquals(2, this.leaf.getSeminars().length);
        assertEquals(this.seminars[0], this.leaf.getSeminars()[0]);
        assertEquals(this.seminars[2], this.leaf.getSeminars()[1]);

        // Remove a seminar and let the leaf become empty.
        this.leaf.delete(null, location, this.seminars[2], false);
        assertEquals(BinTreeEmptyLeaf.NODE, this.leaf.delete(null, location,
            this.seminars[0], false));

    }

    /**
     * Test search() through:
     * 1. Provide a far origin and an empty array.
     * 2. Provide a far origin and a non-empty array.
     * 3. Provide a close origin and an empty array.
     * 4. Provide a close origin and a non-empty array.
     */
    public void testSearch()
    {

        this.leaf.insert(null, location, this.seminars[1], false);
        this.leaf.insert(null, location, this.seminars[2], false);

        // Provide a far origin and an empty array.
        Seminar[] found = {};
        SearchResult result = this.leaf.search(new Point(2000, 2000), 10,
            null, false, found);
        assertEquals(1, result.getCount());
        assertEquals(0, result.getSeminars().length);

        // Provide a far origin and a non-empty array.
        found = this.seminars;
        result = this.leaf.search(new Point(2000, 2000), 10,
            null, false, found);
        assertEquals(1, result.getCount());
        assertEquals(3, result.getSeminars().length);
        for (int i = 0; i < result.getSeminars().length; i++)
        {
            assertEquals(this.seminars[i], result.getSeminars()[i]);
        }

        // Provide a close origin and an empty array.
        found = new Seminar[0];
        result = this.leaf.search(new Point(0, 0), 5, null, false, found);
        assertEquals(1, result.getCount());
        assertEquals(3, result.getSeminars().length);
        for (int i = 0; i < result.getSeminars().length; i++)
        {
            assertEquals(this.seminars[i], result.getSeminars()[i]);
        }

        // Provide a close origin and a non-empty array.
        found = result.getSeminars();
        result = this.leaf.search(new Point(3, 4), 0, null, false, found);
        assertEquals(1, result.getCount());
        assertEquals(6, result.getSeminars().length);
        for (int i = 0; i < result.getSeminars().length; i++)
        {
            assertEquals(this.seminars[i % 3], result.getSeminars()[i]);
        }
    }

    /**
     * Test print().
     */
    public void testPrint()
    {
        this.leaf.print(0, 0);
        String printed = systemOut().getHistory();
        assertEquals("(Leaf with 1 objects: 1)\n", printed);

        systemOut().clearHistory();
        this.leaf.insert(null, location, this.seminars[1], false);
        this.leaf.print(3, 1);
        printed = systemOut().getHistory();
        assertEquals("        (Leaf with 2 objects: 1 2)\n", printed);
    }

    /**
     * Test getHeight().
     */
    public void testGetHeight()
    {
        assertEquals(0, this.leaf.getHeight());
    }

}
