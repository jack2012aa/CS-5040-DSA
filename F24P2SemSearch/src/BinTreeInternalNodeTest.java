// -------------------------------------------------------------------------

import student.TestCase;

/**
 * Test {@link BinTreeInternalNode}.
 *
 *  @author Chang-Yu Huang
 *  @version 2024-10-15
 */
public class BinTreeInternalNodeTest
    extends TestCase
{

    /** Tested node. */
    private BinTreeInternalNode node;

    /** A helper node. */
    private BinTreeNode root;

    /** Coordinates. */
    private short x1 = 3;
    private short x2 = 5;
    private short x3 = 12;
    private short y1 = 3;
    private short y3 = 12;

    /** Some seminars to insert. */
    private Seminar[] seminars = {
        new Seminar(1, "1", "1", 1, this.x1, this.y1, 1, null, "1"),
        new Seminar(2, "1", "1", 1, this.x3, this.y1, 1, null, "1"),
        new Seminar(3, "1", "1", 1, this.x2, this.y3, 1, null, "1"),
        new Seminar(4, "1", "1", 1, this.x3, this.y3, 1, null, "1")
    };

    /** World box. */
    private Box world = new Box(0, 15, 0, 15);

    /**
     * Reset and insert some seminars into the node.
     */
    public void setUp()
    {
        this.node = new BinTreeInternalNode();
        this.root = node;
        for (Seminar seminar: this.seminars)
        {
            this.root = this.root.insert(this.world,
                new Point(seminar.x(), seminar.y()), seminar, true);
        }
        systemOut().clearHistory();
    }

    /**
     * Test isInLeft() through:
     * 1. Check a point in left of the world.
     * 2. Check a point in right of the world.
     * 3. Check a point in top of the world.
     * 4. Check a point in bottom of the world.
     */
    public void testInLeft()
    {

        // Check a point in left of the world.
        assertTrue(this.node.isInLeft(this.world, new Point(7, 13), true));

        // Check a point in right of the world.
        assertTrue(!this.node.isInLeft(this.world, new Point(13, 3), true));

        // Check a point in top of the world.
        assertTrue(this.node.isInLeft(this.world, new Point(13, 7), false));

        // Check a point in bottom of the world.
        assertTrue(!this.node.isInLeft(this.world, new Point(3, 13), false));
    }

    /**
     * Test getLeftWorld() and getRightWorld() through:
     * 1. Get the left half world.
     * 2. Get the top half world.
     * 3. Get the right half world.
     * 4. Get the bottom half world.
     */
    public void testGetWorld()
    {

        // Get the left half world.
        assertEquals(this.world.getLeft().toString(),
            this.node.getLeftWorld(this.world, true).toString());

        // Get the top half world.
        assertEquals(this.world.getTop().toString(),
            this.node.getLeftWorld(this.world, false).toString());

        // Get the right half world.
        assertEquals(this.world.getRight().toString(),
            this.node.getRightWorld(this.world, true).toString());

        // Get the bottom half world.
        assertEquals(this.world.getBottom().toString(),
            this.node.getRightWorld(this.world, false).toString());

    }

    /**
     * Test insert() through:
     * 1. Check current tree shape.
     * 2. Insert a deeper node and check shape.
     */
    public void testInsert()
    {

        // Check current tree shape.
        /*
         *             x=7
         *           /     \
         *          y=7     y=7
         *         /  \     /  \
         *       3,3 5,12  12,3 12,12
         */
        this.root.print(2, 0);
        String printed = systemOut().getHistory();
        assertEquals("        (I)\n"
            + "    (I)\n"
            + "(Leaf with 1 objects: 4)\n"
            + "(Leaf with 1 objects: 2)\n"
            + "    (I)\n"
            + "(Leaf with 1 objects: 3)\n"
            + "(Leaf with 1 objects: 1)\n", printed);

        // Insert a deeper node and check shape.
        systemOut().clearHistory();
        this.root = this.root.insert(this.world, new Point(0, 0),
            this.seminars[3], true);
        this.root = this.root.insert(this.world, new Point(8, 4),
            this.seminars[3], true);
        this.root = this.root.insert(this.world, new Point(11, 12),
            this.seminars[0], true);
        /*
         *             x=7
         *           /     \
         *          y=7       y=7
         *         /  \      /    \
         *       x=3  5,12  x=11   x = 11
         *      /   \       /  \         /    \
         *     y=3   E     8,4  12,3   11,12   12,12
         *    /   \
         *   x=1   E
         *  /   \
         * 0,0   3,3
         */
        this.root.print(5, 0);
        printed = systemOut().getHistory();
        assertEquals("                    (I)\n"
            + "                (I)\n"
            + "            (I)\n"
            + "        (Leaf with 1 objects: 4)\n"
            + "        (Leaf with 1 objects: 1)\n"
            + "            (I)\n"
            + "        (Leaf with 1 objects: 2)\n"
            + "        (Leaf with 1 objects: 4)\n"
            + "                (I)\n"
            + "            (Leaf with 1 objects: 3)\n"
            + "            (I)\n"
            + "        (E)\n"
            + "        (I)\n"
            + "    (E)\n"
            + "    (I)\n"
            + "(Leaf with 1 objects: 1)\n"
            + "(Leaf with 1 objects: 4)\n", printed);

    }

    /**
     * Test isLeaf()
     */
    public void testIsLeaf()
    {
        assertTrue(!this.node.isLeaf());
    }

    /**
     * Test delete through:
     * 1. Remove a leaf and remove an internal node.
     * 2. Remove a leaf without removing an internal node.
     * 3. Remove every node.
     */
    public void testDelete()
    {
        // Remove a leaf and remove an internal node.
        this.root = this.root.delete(this.world, new Point(12, 3),
            this.seminars[1], true);
        this.root.print(2, 0);
        String printed = systemOut().getHistory();
        assertEquals("        (I)\n"
            + "    (Leaf with 1 objects: 4)\n"
            + "    (I)\n"
            + "(Leaf with 1 objects: 3)\n"
            + "(Leaf with 1 objects: 1)\n", printed);

        // Remove a leaf without removing an internal node.
        systemOut().clearHistory();
        this.root = this.root.insert(this.world, new Point(7, 3),
            this.seminars[3], true);
        this.root = this.root.delete(this.world, new Point(5, 12),
            this.seminars[2], true);
        this.root.print(3, 0);
        printed = systemOut().getHistory();
        assertEquals("            (I)\n"
            + "        (Leaf with 1 objects: 4)\n"
            + "        (I)\n"
            + "    (E)\n"
            + "    (I)\n"
            + "(Leaf with 1 objects: 4)\n"
            + "(Leaf with 1 objects: 1)\n", printed);

        // Remove every node.
        systemOut().clearHistory();
        this.root = this.root.delete(this.world, new Point(3, 3),
            this.seminars[0], true);
        this.root = this.root.delete(this.world, new Point(7, 3),
            this.seminars[3], true);
        this.root = this.root.delete(this.world, new Point(12, 12),
            this.seminars[3], true);
        assertEquals(BinTreeEmptyLeaf.NODE, this.root);

    }

    /**
     *  Test delete in a deeper tree.
     */
    public void testDeeperDelete()
    {
        this.root = this.root.insert(this.world, new Point(0, 0),
            this.seminars[3], true);
        this.root = this.root.insert(this.world, new Point(8, 4),
            this.seminars[3], true);
        this.root = this.root.insert(this.world, new Point(11, 12),
            this.seminars[0], true);
        /*
         *             x=7
         *           /     \
         *          y=7       y=7
         *         /  \      /    \
         *       x=3  5,12  x=11   x = 11
         *      /   \       /  \         /    \
         *     y=3   E     8,4  12,3   11,12   12,12
         *    /   \
         *   x=1   E
         *  /   \
         * 0,0   3,3
         */
        // This point doesn't exist. Nothing should happen.
        this.root = this.root.delete(world, new Point(2, 5),
            this.seminars[0], true);
        this.root.print(5, 0);
        String printed = systemOut().getHistory();
        assertEquals("                    (I)\n"
            + "                (I)\n"
            + "            (I)\n"
            + "        (Leaf with 1 objects: 4)\n"
            + "        (Leaf with 1 objects: 1)\n"
            + "            (I)\n"
            + "        (Leaf with 1 objects: 2)\n"
            + "        (Leaf with 1 objects: 4)\n"
            + "                (I)\n"
            + "            (Leaf with 1 objects: 3)\n"
            + "            (I)\n"
            + "        (E)\n"
            + "        (I)\n"
            + "    (E)\n"
            + "    (I)\n"
            + "(Leaf with 1 objects: 1)\n"
            + "(Leaf with 1 objects: 4)\n", printed);

        systemOut().clearHistory();
        this.root = this.root.delete(world, new Point(8, 4),
            this.seminars[3], true);
        this.root.print(5,  0);
        printed = systemOut().getHistory();
        assertEquals("                    (I)\n"
            + "                (I)\n"
            + "            (I)\n"
            + "        (Leaf with 1 objects: 4)\n"
            + "        (Leaf with 1 objects: 1)\n"
            + "            (Leaf with 1 objects: 2)\n"
            + "                (I)\n"
            + "            (Leaf with 1 objects: 3)\n"
            + "            (I)\n"
            + "        (E)\n"
            + "        (I)\n"
            + "    (E)\n"
            + "    (I)\n"
            + "(Leaf with 1 objects: 1)\n"
            + "(Leaf with 1 objects: 4)\n", printed);

        systemOut().clearHistory();
        this.root = this.root.delete(world, new Point(0, 0),
            this.seminars[3], true);
        this.root.print(3, 0);
        printed = systemOut().getHistory();
        assertEquals("            (I)\n"
            + "        (I)\n"
            + "    (I)\n"
            + "(Leaf with 1 objects: 4)\n"
            + "(Leaf with 1 objects: 1)\n"
            + "    (Leaf with 1 objects: 2)\n"
            + "        (I)\n"
            + "    (Leaf with 1 objects: 3)\n"
            + "    (Leaf with 1 objects: 1)\n", printed);

        // Insert another node with x < x' and y > y' in the left side.
        systemOut().clearHistory();
        this.root = this.root.insert(world, new Point(8, 7), this.seminars[2],
            true);
        this.root = this.root.delete(world, new Point(8, 7), this.seminars[2],
            true);
        this.root.print(3, 0);
        printed = systemOut().getHistory();
        assertEquals("            (I)\n"
            + "        (I)\n"
            + "    (I)\n"
            + "(Leaf with 1 objects: 4)\n"
            + "(Leaf with 1 objects: 1)\n"
            + "    (Leaf with 1 objects: 2)\n"
            + "        (I)\n"
            + "    (Leaf with 1 objects: 3)\n"
            + "    (Leaf with 1 objects: 1)\n", printed);

        // Delete 12, 3
        systemOut().clearHistory();
        this.root = this.root.delete(world, new Point(12, 3), this.seminars[1],
            true);
        this.root.print(3, 0);
        printed = systemOut().getHistory();
        assertEquals("            (I)\n"
            + "        (I)\n"
            + "    (I)\n"
            + "(Leaf with 1 objects: 4)\n"
            + "(Leaf with 1 objects: 1)\n"
            + "    (E)\n"
            + "        (I)\n"
            + "    (Leaf with 1 objects: 3)\n"
            + "    (Leaf with 1 objects: 1)\n", printed);

        /*
         *               x=7
         *             /     \
         *          y=7       y=7
         *         /  \      /    \
         *       3,3  5,12  E    x = 11
         *                       /    \
         *                    11,12   12,12
         */
        systemOut().clearHistory();
        this.root = this.root.delete(world, new Point(11, 12),
            this.seminars[0], true);
        this.root.print(2, 0);
        printed = systemOut().getHistory();
        assertEquals("        (I)\n"
            + "    (Leaf with 1 objects: 4)\n"
            + "    (I)\n"
            + "(Leaf with 1 objects: 3)\n"
            + "(Leaf with 1 objects: 1)\n", printed);
    }

    /**
     * Test search() through:
     * 1. Search a totally out of bound range.
     * 2. Search a almost out of bound range.
     * 3. Search a specific location.
     * 4. Find every seminars in the tree.
     */
    public void testSearch()
    {

        // Search a totally out of bound range.
        Seminar[] emptyArray = {};
        SearchResult result = this.root.search(new Point(18, 18), 1,
            this.world, true, emptyArray);
        assertEquals(1, result.getCount());
        assertEquals(0, result.getSeminars().length);

        // Search a almost out of bound range.
        result = this.root.search(new Point(7, 8), 5, this.world, true,
            emptyArray);
        assertEquals(7, result.getCount());
        assertEquals(1, result.getSeminars().length);
        assertEquals(this.seminars[2], result.getSeminars()[0]);

        // Search a specific location.
        result = this.root.search(new Point(3, 3), 0, this.world, true,
            emptyArray);
        assertEquals(3, result.getCount());
        assertEquals(1, result.getSeminars().length);
        assertEquals(this.seminars[0], result.getSeminars()[0]);

        // Find every seminars in the tree.
        result = this.root.search(new Point(3, 3), 100, this.world, true,
            emptyArray);
        assertEquals(7, result.getCount());
        assertEquals(4, result.getSeminars().length);
        assertEquals(this.seminars[0], result.getSeminars()[0]);
        assertEquals(this.seminars[1], result.getSeminars()[2]);
        assertEquals(this.seminars[2], result.getSeminars()[1]);
        assertEquals(this.seminars[3], result.getSeminars()[3]);

    }

    /**
     *  Test print().
     */
    public void testPrint()
    {
        this.node = new BinTreeInternalNode();
        this.node.print(1, 0);
        assertEquals("    (I)\n(E)\n(E)\n", systemOut().getHistory());
    }

    /**
     * Test getHeight().
     */
    public void testGetHeight()
    {
        assertEquals(2, this.node.getHeight());
    }

}
