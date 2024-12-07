// -------------------------------------------------------------------------

import student.TestCase;

/**
 * Test of {@link BST}
 *
 *  @author Chang-Yu Huang
 *  @version 2024-09-27
 */
public class BSTTest
    extends TestCase
{

    /** Tested BST. */
    private BST<Integer> intTree;
    private BST<String> strTree;

    /** Data. */
    private static final Integer[] INT_DATA = {5, 4, 6, 1, 7, 2, 3};
    private static final String[] STR_DATA = {"0", "1", "00", "01", "2222",
        "2333", "9901"};
    private static final Seminar[] SEMINARS = {new Seminar(), new Seminar(),
        new Seminar(), new Seminar(), new Seminar(), new Seminar(),
        new Seminar()};


    /**
     * Insert data into the tree.
     */
    public void setUp()
    {
        this.intTree = new BST<Integer>();
        this.strTree = new BST<String>();
        for (int i = 0; i < INT_DATA.length; i++)
        {
            this.intTree.insert(BSTTest.INT_DATA[i], BSTTest.SEMINARS[i]);
            this.strTree.insert(BSTTest.STR_DATA[i], BSTTest.SEMINARS[i]);
        }

    }

    /**
     * Test insert() through:
     * 1. Search existing keys.
     * 2. Search keys do not exist.
     * 3. Insert and check duplicate key.
     * 4. Check the structure of the tree by print.
     */
    public void testInsert()
    {

        // Search existing keys.
        for (int i = 0; i < BSTTest.INT_DATA.length; i++)
        {
            assertEquals(BSTTest.SEMINARS[i],
                this.intTree.search(BSTTest.INT_DATA[i])[0]);
            assertEquals(BSTTest.SEMINARS[i],
                this.strTree.search(BSTTest.STR_DATA[i])[0]);
        }

        // Search keys do not exist.
        assertEquals(0, this.intTree.search(10).length);
        assertEquals(0, this.strTree.search("1919").length);

        // Insert and check duplicate key.
        Seminar seminar = new Seminar();
        this.intTree.insert(BSTTest.INT_DATA[0], seminar);
        Seminar[] searchResult = this.intTree.search(BSTTest.INT_DATA[0]);
        assertEquals(2, searchResult.length);
        // search() works in LIFO style.
        assertEquals(seminar, searchResult[0]);
        assertEquals(BSTTest.SEMINARS[0], searchResult[1]);

        this.strTree.insert(BSTTest.STR_DATA[0], seminar);
        searchResult = this.strTree.search(BSTTest.STR_DATA[0]);
        assertEquals(2, searchResult.length);
        // search() works in LIFO style.
        assertEquals(seminar, searchResult[0]);
        assertEquals(BSTTest.SEMINARS[0], searchResult[1]);

        // Check the structure of the tree by print.
        /*
         * intTree:
         *            5
         *           / \
         *          4   6
         *         / \ / \
         *        1   5n  7
         *         \
         *          2
         *           \
         *            3
         */
        this.intTree.print();
        String actualOutput = systemOut().getHistory();
        String expectedOutput = "        (null)\n"
            + "            \\\n"
            + "            (1)\n"
            + "            /\n"
            + "    (null)\n"
            + "        \\\n"
            + "        (2)\n"
            + "        /\n"
            + "(null)\n"
            + "    \\\n"
            + "    (3)\n"
            + "    /\n"
            + "(null)\n"
            + "                \\\n"
            + "                (4)\n"
            + "                /\n"
            + "        (null)\n"
            + "            \\\n"
            + "            (5)\n"
            + "            /\n"
            + "        (null)\n"
            + "                    \\\n"
            + "                    (5)\n"
            + "                    /\n"
            + "            (null)\n"
            + "                \\\n"
            + "                (6)\n"
            + "                /\n"
            + "        (null)\n"
            + "            \\\n"
            + "            (7)\n"
            + "            /\n"
            + "        (null)\n"
            + "Number of records: 8\n";
        assertEquals(expectedOutput, actualOutput);

        systemOut().clearHistory();

        /*
         * strTree:
         *        0
         *       / \
         *      0   1
         *         / \
         *        00 2222
         *          \    \
         *          01   2333
         *                 \
         *                 9901
         */
        this.strTree.print();
        actualOutput = systemOut().getHistory();
        expectedOutput = "            (null)\n"
            + "                \\\n"
            + "                (0)\n"
            + "                /\n"
            + "            (null)\n"
            + "                    \\\n"
            + "                    (0)\n"
            + "                    /\n"
            + "        (null)\n"
            + "            \\\n"
            + "            (00)\n"
            + "            /\n"
            + "    (null)\n"
            + "        \\\n"
            + "        (01)\n"
            + "        /\n"
            + "    (null)\n"
            + "                \\\n"
            + "                (1)\n"
            + "                /\n"
            + "        (null)\n"
            + "            \\\n"
            + "            (2222)\n"
            + "            /\n"
            + "    (null)\n"
            + "        \\\n"
            + "        (2333)\n"
            + "        /\n"
            + "(null)\n"
            + "    \\\n"
            + "    (9901)\n"
            + "    /\n"
            + "(null)\n"
            + "Number of records: 8\n";
        assertEquals(expectedOutput, actualOutput);
    }

    /**
     * Test getMax() through:
     * 1. Get the largest node in a nonempty tree.
     * 2. Get the largest node in an empty tree.
     */
    public void testGetMax()
    {
        // Get the largest node in a nonempty tree.
        assertEquals(BSTTest.SEMINARS[BSTTest.SEMINARS.length - 1],
            this.strTree.getMax(this.strTree.getRoot()).getValue());
        // Get the largest node in an empty tree.
        this.strTree = new BST<String>();
        assertNull(this.strTree.getMax(this.strTree.getRoot()));
    }

    /**
     * Test deleteMax() through:
     * 1. Delete max in the right subtree.
     * 2. Delete max as the root.
     * 3. Delete max in an empty tree.
     */
    public void testDeleteMax()
    {
        // Delete max in the right subtree.
        this.intTree.deleteMax(this.intTree.getRoot());
        assertEquals(0, this.intTree.search(7).length);
        this.intTree.deleteMax(this.intTree.getRoot());
        assertEquals(0, this.intTree.search(6).length);

        // Delete max as the root.
        // deleteMax doesn't set the root of the BST.
        // It only return the new root of the subtree.
        assertEquals(4,
            (int) this.intTree.deleteMax(this.intTree.getRoot()).getKey());

        // Delete max in an empty tree.
        assertNull(this.intTree.deleteMax(null));
    }

    /**
     * Test delete() through:
     * 1. Delete a node which isn't a root and doesn't have a left child.
     * 2. Delete a node which isn't a root and doesn't have a right child.
     * 3. Delete a node which doesn't exist.
     * 4. Delete the root.
     * 5. Try to delete a node by identical key but different value.
     */
    public void testDelete()
    {

        // Delete a node which isn't a root and doesn't have a left child.
        // 6
        this.intTree.delete(BSTTest.INT_DATA[2], BSTTest.SEMINARS[2]);
        assertEquals(0, this.intTree.search(BSTTest.INT_DATA[2]).length);
        assertEquals(1, this.intTree.search(BSTTest.INT_DATA[4]).length);

        // Delete a node which isn't a root and doesn't have a right child.
        // 4
        this.intTree.delete(BSTTest.INT_DATA[1], BSTTest.SEMINARS[1]);
        assertEquals(0, this.intTree.search(BSTTest.INT_DATA[1]).length);
        assertEquals(1, this.intTree.search(BSTTest.INT_DATA[3]).length);

        // Delete a node which doesn't exist.
        // Compare print result.
        systemOut().clearHistory();
        this.intTree.print();
        String originalTree = systemOut().getHistory();
        this.intTree.delete(1000, BSTTest.SEMINARS[0]);
        systemOut().clearHistory();
        this.intTree.print();
        String treeAfterDeletion = systemOut().getHistory();
        assertEquals(originalTree, treeAfterDeletion);

        // Delete the root.
        this.intTree.delete(BSTTest.INT_DATA[0], BSTTest.SEMINARS[0]);
        assertTrue(BSTTest.INT_DATA[0] != this.intTree.getRoot().getKey());
        // Check the appearance of the tree.
        systemOut().clearHistory();
        this.intTree.print();
        String expectedOutput = "    (null)\n"
            + "        \\\n"
            + "        (1)\n"
            + "        /\n"
            + "(null)\n"
            + "    \\\n"
            + "    (2)\n"
            + "    /\n"
            + "(null)\n"
            + "            \\\n"
            + "            (3)\n"
            + "            /\n"
            + "    (null)\n"
            + "        \\\n"
            + "        (7)\n"
            + "        /\n"
            + "    (null)\n"
            + "Number of records: 4\n";
        String actualOutput = systemOut().getHistory();
        assertEquals(expectedOutput, actualOutput);

        // Try to delete a node by identical key but different value.
        this.intTree.delete(3, new Seminar());
        systemOut().clearHistory();
        this.intTree.print();
        actualOutput = systemOut().getHistory();
        assertEquals(expectedOutput, actualOutput);

    }

    // search() is tested thoroughly in other tests.

    /**
     * Test searchInRange() through:
     * 1. Search for a valid range.
     * 2. Search for an invalid range.
     */
    public void testSearchInRange()
    {

        // Search for a valid range.
        Seminar[] result = this.intTree.searchInRange(2, 5);
        assertEquals(4, result.length);
        assertEquals(BSTTest.SEMINARS[5], result[0]);
        assertEquals(BSTTest.SEMINARS[6], result[1]);
        assertEquals(BSTTest.SEMINARS[1], result[2]);
        assertEquals(BSTTest.SEMINARS[0], result[3]);

        result = this.intTree.searchInRange(-1, 2);
        assertEquals(2, result.length);
        assertEquals(BSTTest.SEMINARS[3], result[0]);
        assertEquals(BSTTest.SEMINARS[5], result[1]);

        // Search for an invalid range.
        result = this.intTree.searchInRange(99, 1999);
        assertEquals(0, result.length);

    }

    /**
     * Test print() through:
     * 1. Print an empty tree.
     * Other cases were tested in previous tests.
     */
    public void testPrint()
    {
        this.intTree = new BST<Integer>();
        this.intTree.print();
        String expectedOutput = "This tree is empty\n";
        String actualOutput = systemOut().getHistory();
        assertEquals(expectedOutput, actualOutput);
    }

    /**
     * Test getHeight.
     */
    public void testGetHeight()
    {
        /*
         * intTree:
         *            5
         *           / \
         *          4   6
         *         / \ / \
         *        1   nn  7
         *         \
         *          2
         *           \
         *            3
         */
        assertEquals(5, this.intTree.getHeight(this.intTree.getRoot()));

        /*
         * strTree:
         *        0
         *       / \
         *          1
         *         / \
         *        00 2222
         *          \    \
         *          01   2333
         *                 \
         *                 9901
         */
        assertEquals(5, this.strTree.getHeight(this.strTree.getRoot()));
    }

}
