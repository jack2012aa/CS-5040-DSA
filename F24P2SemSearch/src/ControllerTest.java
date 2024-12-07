// -------------------------------------------------------------------------

import student.TestCase;

/**
 * Test of {@link Controller}.
 *
 *  @author Chang-Yu Huang
 *  @version 2024-09-26
 */
public class ControllerTest
    extends TestCase
{
    //~ Fields ................................................................
    /** id data. */
    public static final int[] IDS = {1, 2, 0};
    /** title data. */
    public static final String[] TITLES = {"A", "B", "C"};
    /** date data. */
    public static final String[] DATES = {"1234", "2234", "3234"};
    /** length data. */
    public static final int[] LENGTHS = {10, 20, 3};
    /** x coordinate data. */
    public static final short[] XS = {9, 1, 12};
    /** y coordinate data. */
    public static final short[] YS = {2, 4, 24};
    /** cost data. */
    public static final int[] COSTS = {100, 50, 10};
    /** keywords data. */
    public static final String[][] KEYWORDS = {{"VT", "Computer Science"},
        {"Animal Science", "VT"}, {"Cook", "Space"}};
    /** description data. */
    public static final String[] DESCRIPTIONS = {"666", "19aaa", "1199abab"};
    /** Built seminars for check. */
    private Seminar[] seminars;
    /** Tested controller. */
    private Controller controller;

    //~Public  Methods ........................................................
    /**
     * Insert some data into the controller.
     */
    public void setUp()
    {
        this.controller = new Controller(128);
        this.seminars = new Seminar[ControllerTest.IDS.length];
        for (int i = 0; i < ControllerTest.IDS.length; i++)
        {
            this.controller.insert(ControllerTest.IDS[i],
                ControllerTest.TITLES[i],
                ControllerTest.DATES[i],
                ControllerTest.LENGTHS[i],
                ControllerTest.XS[i],
                ControllerTest.YS[i],
                ControllerTest.COSTS[i],
                ControllerTest.KEYWORDS[i],
                ControllerTest.DESCRIPTIONS[i]);
            this.seminars[i] = new Seminar(ControllerTest.IDS[i],
                ControllerTest.TITLES[i],
                ControllerTest.DATES[i],
                ControllerTest.LENGTHS[i],
                ControllerTest.XS[i],
                ControllerTest.YS[i],
                ControllerTest.COSTS[i],
                ControllerTest.KEYWORDS[i],
                ControllerTest.DESCRIPTIONS[i]);
        }
        systemOut().clearHistory();
    }

    /**
     * Test insert() through:
     * 1. Insert a duplicate id.
     * 2. Insert a new id and check structures of trees.
     */
    public void testInsert()
    {

        // Insert a duplicate id.
        this.controller.insert(ControllerTest.IDS[0], "", "", 1, (short) 1,
            (short) 1, 1, new String[0], "");
        String expectedOutput = String.format("Insert FAILED - There is "
            + "already a record with ID %d\n", ControllerTest.IDS[0]);
        String actualOutput = systemOut().getHistory();
        assertEquals(expectedOutput, actualOutput);
        systemOut().clearHistory();

        // Insert a new id and check the structure of trees.
        this.controller.insert(4, "a", "3234", 1, (short) 1, (short) 1, 10,
            new String[0], "");
        Seminar seminar = new Seminar(4, "a", "3234", 1, (short) 1, (short) 1,
            10, new String[0], "");
        expectedOutput = "Successfully inserted record with ID 4\n" +
            seminar.toString() + "\n";
        actualOutput = systemOut().getHistory();
        assertEquals(expectedOutput, actualOutput);

        systemOut().clearHistory();
        this.controller.printID();
        expectedOutput = "ID Tree:\n"
            + "    (null)\n"
            + "        \\\n"
            + "        (0)\n"
            + "        /\n"
            + "    (null)\n"
            + "            \\\n"
            + "            (1)\n"
            + "            /\n"
            + "    (null)\n"
            + "        \\\n"
            + "        (2)\n"
            + "        /\n"
            + "(null)\n"
            + "    \\\n"
            + "    (4)\n"
            + "    /\n"
            + "(null)\n"
            + "Number of records: 4\n";
        actualOutput = systemOut().getHistory();
        assertEquals(expectedOutput, actualOutput);

        systemOut().clearHistory();
        this.controller.printCost();
        expectedOutput = "Cost Tree:\n"
            + "(null)\n"
            + "    \\\n"
            + "    (10)\n"
            + "    /\n"
            + "(null)\n"
            + "        \\\n"
            + "        (10)\n"
            + "        /\n"
            + "    (null)\n"
            + "            \\\n"
            + "            (50)\n"
            + "            /\n"
            + "        (null)\n"
            + "                \\\n"
            + "                (100)\n"
            + "                /\n"
            + "            (null)\n"
            + "Number of records: 4\n";
        actualOutput = systemOut().getHistory();
        assertEquals(expectedOutput, actualOutput);

        systemOut().clearHistory();
        this.controller.printDate();
        expectedOutput = "Date Tree:\n"
            + "            (null)\n"
            + "                \\\n"
            + "                (1234)\n"
            + "                /\n"
            + "        (null)\n"
            + "            \\\n"
            + "            (2234)\n"
            + "            /\n"
            + "(null)\n"
            + "    \\\n"
            + "    (3234)\n"
            + "    /\n"
            + "(null)\n"
            + "        \\\n"
            + "        (3234)\n"
            + "        /\n"
            + "    (null)\n"
            + "Number of records: 4\n";
        actualOutput = systemOut().getHistory();
        assertEquals(expectedOutput, actualOutput);

        systemOut().clearHistory();
        this.controller.printKeyword();
        expectedOutput = "Keyword Tree:\n"
            + "        (null)\n"
            + "            \\\n"
            + "            (Animal Science)\n"
            + "            /\n"
            + "        (null)\n"
            + "                \\\n"
            + "                (Computer Science)\n"
            + "                /\n"
            + "    (null)\n"
            + "        \\\n"
            + "        (Cook)\n"
            + "        /\n"
            + "(null)\n"
            + "    \\\n"
            + "    (Space)\n"
            + "    /\n"
            + "(null)\n"
            + "            \\\n"
            + "            (VT)\n"
            + "            /\n"
            + "        (null)\n"
            + "                    \\\n"
            + "                    (VT)\n"
            + "                    /\n"
            + "                (null)\n"
            + "Number of records: 6\n";
        actualOutput = systemOut().getHistory();
        assertEquals(expectedOutput, actualOutput);
    }

    /**
     * Test delete() through:
     * 1. Delete a seminar which doesn't exist.
     * 2. Delete a seminar and check structures of trees.
     */
    public void testDelete()
    {

        // Delete a seminar which doesn't exist.
        this.controller.delete(110);
        String expectedOutput = "Delete FAILED -- There is no record with ID "
            + "110\n";
        String actualOutput = systemOut().getHistory();
        assertEquals(expectedOutput, actualOutput);
        systemOut().clearHistory();

        // Delete a seminar and check structures of trees.
        this.controller.delete(ControllerTest.IDS[0]);
        expectedOutput = String.format("Record with ID %d successfully "
            + "deleted from the database\n", ControllerTest.IDS[0]);
        actualOutput = systemOut().getHistory();
        assertEquals(expectedOutput, actualOutput);

        systemOut().clearHistory();
        this.controller.printCost();
        expectedOutput = "Cost Tree:\n"
            + "(null)\n"
            + "    \\\n"
            + "    (10)\n"
            + "    /\n"
            + "(null)\n"
            + "        \\\n"
            + "        (50)\n"
            + "        /\n"
            + "    (null)\n"
            + "Number of records: 2\n";
        actualOutput = systemOut().getHistory();
        assertEquals(expectedOutput, actualOutput);

        systemOut().clearHistory();
        this.controller.printDate();
        expectedOutput = "Date Tree:\n"
            + "    (null)\n"
            + "        \\\n"
            + "        (2234)\n"
            + "        /\n"
            + "(null)\n"
            + "    \\\n"
            + "    (3234)\n"
            + "    /\n"
            + "(null)\n"
            + "Number of records: 2\n";
        actualOutput = systemOut().getHistory();
        assertEquals(expectedOutput, actualOutput);

        systemOut().clearHistory();
        this.controller.printID();
        expectedOutput = "ID Tree:\n"
            + "    (null)\n"
            + "        \\\n"
            + "        (0)\n"
            + "        /\n"
            + "(null)\n"
            + "    \\\n"
            + "    (2)\n"
            + "    /\n"
            + "(null)\n"
            + "Number of records: 2\n";
        actualOutput = systemOut().getHistory();
        assertEquals(expectedOutput, actualOutput);

        systemOut().clearHistory();
        this.controller.printKeyword();
        // "VT" was the root at the beginning.
        // Because it didn't have a right child, "Computer Science" became the
        // root after the first deletion.
        // Then "Computer Science" was deleted. "Animal Science" became the
        // root.
        expectedOutput = "Keyword Tree:\n"
            + "            (null)\n"
            + "                \\\n"
            + "                (Animal Science)\n"
            + "                /\n"
            + "    (null)\n"
            + "        \\\n"
            + "        (Cook)\n"
            + "        /\n"
            + "(null)\n"
            + "    \\\n"
            + "    (Space)\n"
            + "    /\n"
            + "(null)\n"
            + "            \\\n"
            + "            (VT)\n"
            + "            /\n"
            + "        (null)\n"
            + "Number of records: 4\n";
        actualOutput = systemOut().getHistory();
        assertEquals(expectedOutput, actualOutput);
    }

    /**
     * Test searchByID through:
     * 1. Search an id which doesn't exist.
     * 2. Search an id and check what's printed.
     */
    public void testSearchByID()
    {

        // Search an id which doesn't exist.
        this.controller.searchByID(11111);
        String expectedOutput = "Search FAILED -- There is no record with "
            + "ID 11111\n";
        String actualOutput = systemOut().getHistory();
        assertEquals(expectedOutput, actualOutput);
        systemOut().clearHistory();

        // Search an id and check what's printed.
        for (int i = 0; i < ControllerTest.IDS.length; i++)
        {
            int id = ControllerTest.IDS[i];
            this.controller.searchByID(id);
            expectedOutput = String.format("Found record with ID %d:\n", id)
                + this.seminars[i].toString() + "\n";
            actualOutput = systemOut().getHistory();
            assertEquals(expectedOutput, actualOutput);
            systemOut().clearHistory();
        }

    }

    /**
     * Test searchByCost() through:
     * 1. Provide totally out-of-range boundaries.
     * 2. Provide boundaries which contain some seminars.
     * 3. Provide boundaries which contain all seminars.
     */
    public void testSearchByCost()
    {

        // Provide totally out-of-range boundaries.
        this.controller.searchByCost(1000, 2000);
        String expectedOutput = "Seminars with costs in range 1000 to 2000:\n"
            + "2 nodes visited in this search\n";
        String actualOutput = systemOut().getHistory();
        assertEquals(expectedOutput, actualOutput);
        systemOut().clearHistory();

        // Provide boundaries which contain some seminars.
        this.controller.searchByCost(30, 120);
        // Seminars should be printed in ascending ordered.
        expectedOutput = "Seminars with costs in range 30 to 120:\n"
            + this.seminars[1].toString() + "\n" + this.seminars[0].toString()
            + "\n6 nodes visited in this search"
            + "\n";
        actualOutput = systemOut().getHistory();
        assertEquals(expectedOutput, actualOutput);
        systemOut().clearHistory();

        // Provide boundaries which contain all seminars.
        this.controller.searchByCost(0, 120);
        // Seminars should be printed in ascending ordered.
        expectedOutput = "Seminars with costs in range 0 to 120:\n"
            + this.seminars[2].toString() + "\n" + this.seminars[1].toString()
            + "\n" + this.seminars[0].toString() + "\n"
            + "7 nodes visited in this search\n";
        actualOutput = systemOut().getHistory();
        assertEquals(expectedOutput, actualOutput);

    }

    /**
     * Test searchByDate() through:
     * 1. Provide totally out-of-range boundaries.
     * 2. Provide boundaries which contain some seminars.
     * 3. Provide boundaries which contain all seminars.
     */
    public void testSearchByDate()
    {

        // Provide totally out-of-range boundaries.
        this.controller.searchByDate("000", "0001");
        String expectedOutput = "Seminars with dates in range 000 to 0001:\n"
            + "2 nodes visited in this search\n";
        String actualOutput = systemOut().getHistory();
        assertEquals(expectedOutput, actualOutput);
        systemOut().clearHistory();

        // Provide boundaries which contain some seminars.
        this.controller.searchByDate("0", "2");
        // Seminars should be printed in ascending ordered.
        expectedOutput = "Seminars with dates in range 0 to 2:\n"
            + this.seminars[0].toString() + "\n"
            + "4 nodes visited in this search\n";
        actualOutput = systemOut().getHistory();
        assertEquals(expectedOutput, actualOutput);
        systemOut().clearHistory();

        // Provide boundaries which contain all seminars.
        this.controller.searchByDate("0", "4");
        // Seminars should be printed in ascending ordered.
        expectedOutput = "Seminars with dates in range 0 to 4:\n"
            + this.seminars[0].toString() + "\n" + this.seminars[1].toString()
            + "\n" + this.seminars[2].toString() + "\n"
            + "7 nodes visited in this search\n";
        actualOutput = systemOut().getHistory();
        assertEquals(expectedOutput, actualOutput);

    }

    /**
     * Test searchByKeyword() through:
     * 1. Search a keyword which doesn't exist.
     * 2. Search a keyword and check what's printed.
     */
    public void testSearchByKeyword()
    {

        // Search a keyword which doesn't exist.
        this.controller.searchByKeyword("11111");
        String expectedOutput = "Seminars matching keyword 11111:\n";
        String actualOutput = systemOut().getHistory();
        assertEquals(expectedOutput, actualOutput);
        systemOut().clearHistory();

        // Search a keyword and check what's printed.
        this.controller.searchByKeyword("VT");
        expectedOutput = "Seminars matching keyword VT:\n"
            + this.seminars[1].toString() + "\n" + this.seminars[0].toString()
            + "\n";
        actualOutput = systemOut().getHistory();
        assertEquals(expectedOutput, actualOutput);
        systemOut().clearHistory();

    }

    // All print() are thoroughly tested in previous tests.

}
