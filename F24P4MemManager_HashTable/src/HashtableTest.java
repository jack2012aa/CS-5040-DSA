import student.TestCase;

/**
 * Test {@link HashTable}
 *
 *  @author Chang-Yu Huang
 *  @version 2024-11-11
 */
public class HashtableTest
    extends TestCase
{
    //~ Fields ................................................................
    /** Tested table. */
    private HashTable table;
    /** Initial size of the table. */
    private int initialCapacity = 2;
    /** Memory pool. */
    private MemoryManager pool;
    /** Data. */
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
    private Seminar[] moreData = {
        new Seminar(1 + 16 * 1, "2", "3", 4, (short) 5, (short) 6, 7,
            new String[] {"1", "2"}, "8"),
        new Seminar(1 + 16 * 2, "2", "3", 4, (short) 5, (short) 6, 7,
            new String[] {"3", "4"}, "8"),
        new Seminar(1 + 16 * 3, "2", "3", 4, (short) 5, (short) 6, 7,
            new String[] {"3131", "2"}, "8"),
        new Seminar(1 + 16 * 4, "2", "3", 4, (short) 5, (short) 6, 7,
            new String[] {"1sdfsdrer", "gdfgfd2", "asd2dfsd"}, "8"),
        new Seminar(3 + 16 * 1, "2", "3", 4, (short) 5, (short) 6, 7,
            new String[] {"1", "2", "3", "4", "5", "6", "7", "8"}, "8"),
        new Seminar(3 + 16 * 5, "2", "3", 4, (short) 5, (short) 6, 7,
            new String[] {"1sfdf", "we2", "3", "4", "5", "6", "7", "8"}, "8"),
    };
    private int[] moreIds = {1 + 16 * 1, 1 + 16 * 2, 1 + 16 * 3, 1 + 16 * 4,
        3 + 16 * 1, 3 + 16 * 5};

    //~Public  Methods ........................................................
    /**
     * Reset table.
     */
    public void setUp()
    {
        this.table = new HashTable(this.initialCapacity);
        this.pool = new MemoryManager(1024);
    }

    /**
     * Test insert() and remove() through:
     * 1. Insert and then check content and capacity.
     * 2. Remove some records and check content.
     * 3. Remove a key doesn't exist.
     * 4. Insert some records to the tombstone position.
     */
    public void testInsertRemove()
    {

        // Insert and then check content and capacity.
        Handle[] handles = new Handle[this.data.length];
        for (int i = 0; i < this.ids.length; i++)
        {
            try
            {
                handles[i] = pool.insert(this.data[i].serialize());
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
            this.table.insert(new Record(this.ids[i], handles[i]));
        }
        assertEquals(16, this.table.getCapacity());
        this.table.print();
        assertEquals("0: 132112\n"
            + "1: 1\n"
            + "2: 11121\n"
            + "3: 2\n"
            + "4: 3\n"
            + "5: 2341\n"
            + "total records: 6\n", systemOut().getHistory());

        // Remove some records and check content.
        systemOut().clearHistory();
        this.table.remove(1);
        this.table.remove(2341);
        this.table.print();
        assertEquals("0: 132112\n"
            + "1: TOMBSTONE\n"
            + "2: 11121\n"
            + "3: 2\n"
            + "4: 3\n"
            + "5: TOMBSTONE\n"
            + "total records: 4\n", systemOut().getHistory());

        // Remove a key doesn't exist.
        systemOut().clearHistory();
        this.table.remove(114514);
        this.table.print();
        assertEquals("0: 132112\n"
            + "1: TOMBSTONE\n"
            + "2: 11121\n"
            + "3: 2\n"
            + "4: 3\n"
            + "5: TOMBSTONE\n"
            + "total records: 4\n", systemOut().getHistory());

        // Insert some records to the tombstone position.
        systemOut().clearHistory();
        for (int i = 0; i < 4; i++)
        {
            Handle handle = new Handle(1, 1);
            try
            {
                handle = this.pool.insert(this.moreData[i].serialize());
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
            this.table.insert(new Record(this.moreIds[i], handle));
        }
        this.table.print();
        assertEquals(16, this.table.getCapacity());
        assertEquals("0: 132112\n"
            + "1: 17\n"
            + "2: 11121\n"
            + "3: 2\n"
            + "4: 3\n"
            + "5: TOMBSTONE\n"
            + "6: 65\n"
            + "7: 33\n"
            + "11: 49\n"
            + "total records: 8\n", systemOut().getHistory());

        Handle handle = new Handle(1, 1);
        try
        {
            handle = this.pool.insert(this.moreData[4].serialize());
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        this.table.insert(new Record(this.moreIds[4], handle));
        assertEquals(32, this.table.getCapacity());

    }

    /**
     * Test find() through:
     * 1. Find an exist record.
     * 2. Find a record doesn't exist.
     */
    public void testFind()
    {

        for (int i = 0; i < this.ids.length; i++)
        {
            Handle handle = new Handle(1, 1);
            try
            {
                handle = pool.insert(this.data[i].serialize());
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
            this.table.insert(new Record(this.ids[i], handle));
        }

        // Find an exist record.
        Record record = this.table.find(this.ids[0]);
        assertEquals(this.ids[0], record.getKey());

        // Find a record doesn't exist.
        assertNull(this.table.find(114514));

    }

}
