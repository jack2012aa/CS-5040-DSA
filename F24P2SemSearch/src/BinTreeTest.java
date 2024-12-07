// -------------------------------------------------------------------------

import student.TestCase;

/**
 * Test {@link BinTree}
 * Most cases are tested in {@link BinTreeInternalNodeTest}.
 *
 *  @author Chang-Yu Huang
 *  @version 2024-10-15
 */
public class BinTreeTest
    extends TestCase
{

    /** Tested tree. */
    private BinTree tree;

    /** Bounding box of the world. */
    private Box world = new Box(0, 15, 0, 15);

    /** Insert data. */
    private short x1 = 3;
    private short x2 = 5;
    private short x3 = 12;
    private short y1 = 3;
    private short y2 = 12;
    private short y3 = 8;

    private Seminar[] seminars = {
        new Seminar(1, "1", "1", 1, this.x1, this.y1, 1, null, "1"),
        new Seminar(2, "1", "1", 1, this.x3, this.y1, 1, null, "1"),
        new Seminar(3, "1", "1", 1, this.x2, this.y2, 1, null, "1"),
        new Seminar(4, "1", "1", 1, this.x3, this.y2, 1, null, "1"),
        new Seminar(5, "1", "1", 1, this.x2, this.y3, 1, null, "1")
    };

    /**
     * Insert data.
     */
    public void setUp()
    {
        this.tree = new BinTree(world);
        for (Seminar seminar: this.seminars)
        {
            this.tree.insert(seminar);
        }
        systemOut().clearHistory();
    }

    /**
     * Test insert and print by checking tree structure.
     */
    public void testInsertAndPrint()
    {
        /*
         *              x=7
         *            /     \
         *          y=7     y=7
         *        /   \      /  \
         *      3,3   x=3  12,3  12,12
         *           /   \
         *          E    y=11
         *              /    \
         *             5,8   5,12
         */
        this.tree.print();
        assertEquals("                (I)\n"
            + "            (I)\n"
            + "        (Leaf with 1 objects: 4)\n"
            + "        (Leaf with 1 objects: 2)\n"
            + "            (I)\n"
            + "        (I)\n"
            + "    (I)\n"
            + "(Leaf with 1 objects: 3)\n"
            + "(Leaf with 1 objects: 5)\n"
            + "    (E)\n"
            + "        (Leaf with 1 objects: 1)\n",
            systemOut().getHistory());
    }

    /**
     * Test delete by checking tree structure.
     */
    public void testDelete()
    {
        this.tree.delete(this.seminars[0]);
        /*
         *              x=7
         *            /     \
         *          y=7     y=7
         *        /   \      /  \
         *       E    x=3  12,3  12,12
         *           /   \
         *          E    y=11
         *              /    \
         *             5,8   5,12
         */
        this.tree.print();
        assertEquals("                (I)\n"
            + "            (I)\n"
            + "        (Leaf with 1 objects: 4)\n"
            + "        (Leaf with 1 objects: 2)\n"
            + "            (I)\n"
            + "        (I)\n"
            + "    (I)\n"
            + "(Leaf with 1 objects: 3)\n"
            + "(Leaf with 1 objects: 5)\n"
            + "    (E)\n"
            + "        (E)\n",
            systemOut().getHistory());

        systemOut().clearHistory();
        this.tree.delete(this.seminars[4]);
        /*
         *              x=7
         *            /     \
         *          5,12     y=7
         *                   /  \
         *               12,3  12,12
         */
        this.tree.print();
        assertEquals("        (I)\n"
            + "    (I)\n"
            + "(Leaf with 1 objects: 4)\n"
            + "(Leaf with 1 objects: 2)\n"
            + "    (Leaf with 1 objects: 3)\n",
            systemOut().getHistory());
    }

    /**
     * Test search()
     */
    public void testSearch()
    {
        /*
         *           O  x=7
         *            /     \
         *       O  y=7     y=7
         *        /   \      /  \
         *   O  3,3 O x=3  12,3  12,12
         *           /   \
         *       O  E  O y=11
         *              /    \
         *           O 5,8  O 5,12
         */
        SearchResult result = this.tree.search(new Point(-1, 7), 8);
        assertEquals(8, result.getCount());
        assertEquals(3, result.getSeminars().length);
        assertEquals(this.seminars[0], result.getSeminars()[0]);
        assertEquals(this.seminars[4], result.getSeminars()[1]);
        assertEquals(this.seminars[2], result.getSeminars()[2]);
    }

}
