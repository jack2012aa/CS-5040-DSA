// -------------------------------------------------------------------------

import student.TestCase;

/**
 * Test {@link DatabaseController}
 *
 *  @author Chang-Yu Huang
 *  @version 2024-12-03
 */
public class DatabaseControllerTest
    extends TestCase
{

    private Seminar[] data = {
        new Seminar(1, "2", "3", 4, (short) 5, (short) 6, 7,
            new String[] {"1", "2"}, "8"),
        new Seminar(11121, "2", "3", 4, (short) 5, (short) 6, 7,
            new String[] {"3", "4"}, "8"),
        new Seminar(132112, "2", "3", 4, (short) 5, (short) 6, 7,
            new String[] {"3131", "2"}, "8"),
        new Seminar(2341, "2", "3", 4, (short) 5, (short) 6, 7,
            new String[] {"1sdfsdrer", "gdfgfd2", "asd2dfsd"}, "8"),
        new Seminar(2, "2", "3", 4, (short) 5, (short) 6, 7,
            new String[] {"1", "2", "3", "4", "5", "6", "7", "8"}, "8"),
        new Seminar(3, "2", "3", 4, (short) 5, (short) 6, 7,
            new String[] {"1sfdf", "we2", "3", "4", "5", "6", "7", "8"}, "8"),
    };
    private int[] ids = {1, 11121, 132112, 2341, 2, 3};
    private DatabaseController controller;

    /**
     * Initialize database controller.
     */
    public void setUp()
    {
        this.controller = new DatabaseController(256, 4);
    }

    /**
     * Insert and search.
     */
    public void testInsert()
    {

        for (int i = 0; i < this.data.length; i++)
        {
            this.controller.insert(this.ids[i], this.data[i]);
        }

        assertEquals("Successfully inserted record with ID 1\n"
            + "ID: 1, Title: 2\n"
            + "Date: 3, Length: 4, X: 5, Y: 6, Cost: 7\n"
            + "Description: 8\n"
            + "Keywords: 1, 2\n"
            + "Size: 41\n"
            + "Successfully inserted record with ID 11121\n"
            + "ID: 11121, Title: 2\n"
            + "Date: 3, Length: 4, X: 5, Y: 6, Cost: 7\n"
            + "Description: 8\n"
            + "Keywords: 3, 4\n"
            + "Size: 41\n"
            + "Hash table expanded to 8 records\n"
            + "Successfully inserted record with ID 132112\n"
            + "ID: 132112, Title: 2\n"
            + "Date: 3, Length: 4, X: 5, Y: 6, Cost: 7\n"
            + "Description: 8\n"
            + "Keywords: 3131, 2\n"
            + "Size: 44\n"
            + "Successfully inserted record with ID 2341\n"
            + "ID: 2341, Title: 2\n"
            + "Date: 3, Length: 4, X: 5, Y: 6, Cost: 7\n"
            + "Description: 8\n"
            + "Keywords: 1sdfsdrer, gdfgfd2, asd2dfsd\n"
            + "Size: 65\n"
            + "Hash table expanded to 16 records\n"
            + "Successfully inserted record with ID 2\n"
            + "ID: 2, Title: 2\n"
            + "Date: 3, Length: 4, X: 5, Y: 6, Cost: 7\n"
            + "Description: 8\n"
            + "Keywords: 1, 2, 3, 4, 5, 6, 7, 8\n"
            + "Size: 59\n"
            + "Memory pool expanded to 512 bytes\n"
            + "Successfully inserted record with ID 3\n"
            + "ID: 3, Title: 2\n"
            + "Date: 3, Length: 4, X: 5, Y: 6, Cost: 7\n"
            + "Description: 8\n"
            + "Keywords: 1sfdf, we2, 3, 4, 5, 6, 7, 8\n"
            + "Size: 65\n"
            , systemOut().getHistory());

        for (int id: this.ids)
        {
            systemOut().clearHistory();
            this.controller.search(id);
            assertFalse(systemOut().getHistory().equals("Search FAILED -- "
                + "There is no record with ID " + id + "\n"));
        }

        for (int i = 0; i < this.data.length; i++)
        {
            systemOut().clearHistory();
            this.controller.insert(this.ids[i], this.data[i]);
            assertTrue(systemOut().getHistory().equals("Insert FAILED - There "
                + "is already a record with ID " + this.ids[i] + "\n"));
        }

    }

    /**
     * Delete and search
     */
    public void testDelete()
    {

        for (int i = 0; i < this.data.length; i++)
        {
            this.controller.insert(this.ids[i], this.data[i]);
        }

        for (int i = 0; i < this.data.length; i++)
        {
            systemOut().clearHistory();
            this.controller.delete(this.ids[i]);
            this.controller.search(this.ids[i]);
            assertEquals("Record with ID " + this.ids[i]
                + " successfully deleted from the database\n"
                + "Search FAILED -- There is no record with ID "
                + this.ids[i] + "\n", systemOut().getHistory());
        }

        systemOut().clearHistory();
        this.controller.delete(this.ids[0]);
        assertEquals("Delete FAILED -- There is no record with ID "
            + this.ids[0] + "\n", systemOut().getHistory());
    }

}
