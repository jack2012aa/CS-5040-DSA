// -------------------------------------------------------------------------
/**
 *  Test {@link MemoryManagerTest}.
 *
 *  @author Chang-Yu Huang
 *  @version 2024-11-11
 */
public class MemoryManagerTest extends student.TestCase
{
    //~ Fields ................................................................
    /** Tested manager. */
    private MemoryManager manager;
    /** Initial size. */
    private int initialSize = 256;
    /** Test data. */
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
        new Seminar(2, "2", "3", 4, (short) 5, (short) 6, 7,
            new String[] {"1sfdf", "we2", "3", "4", "5", "6", "7", "8"}, "8"),
    };

    //~ Constructors ..........................................................
    /**
     * Reassign manager.
     */
    public void setUp()
    {
        manager = new MemoryManager(this.initialSize);
    }

    //~Public  Methods ........................................................
    /**
     * Test insert() and remove() through:
     * 1. Insert some small space.
     * 2. Insert a large space.
     * 3. Remove some elements and insert some small space.
     */
    public void testInsertRemove()
    {

        this.manager.print();
        String printed = systemOut().getHistory();
        assertEquals("(0,256)\n", printed);

        // Insert some small space.
        Handle[] handles = new Handle[this.data.length];
        for (int i = 0; i < 5; i++)
        {
            try
            {
                handles[i] = this.manager.insert(this.data[i].serialize());
                // Sequential insertion.
                assertEquals(this.data[i].serialize().length,
                    handles[i].getSize());
                if (i > 0)
                {
                    assertEquals(handles[i - 1].getEnd(),
                        handles[i].getStart());
                }
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }
        systemOut().clearHistory();
        this.manager.print();
        printed = systemOut().getHistory();
        assertEquals("(250,6)\n", printed);

        // Insert a large space.
        systemOut().clearHistory();
        try
        {
            handles[5] = this.manager.insert(this.data[5].serialize());
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        printed = systemOut().getHistory();
        assertEquals("Memory pool expanded to 512 bytes\n", printed);

        // Remove some elements and insert some small space.
        this.manager.remove(handles[1]);
        this.manager.remove(handles[3]);
        this.manager.remove(handles[4]);
        systemOut().clearHistory();
        this.manager.print();
        assertEquals("(41,41) -> (126,124) -> (315,197)\n",
            systemOut().getHistory());
        try
        {
            handles[2] = this.manager.insert(this.data[5].serialize());
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        assertEquals(126, handles[2].getStart());
        assertEquals(65, handles[2].getSize());
        systemOut().clearHistory();
        this.manager.print();
        assertEquals("(41,41) -> (191,59) -> (315,197)\n",
            systemOut().getHistory());

    }

    /**
     * Test get() through insert and get seminars.
     */
    public void testGet()
    {
        Handle[] handles = new Handle[this.data.length];
        for (int i = 0; i < this.data.length; i++)
        {
            try
            {
                handles[i] = this.manager.insert(this.data[i].serialize());
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }
        byte[] space = new byte[256];
        for (int i = 0; i < this.data.length; i++)
        {
            this.manager.get(space, handles[i]);
            Seminar seminar = new Seminar();
            try
            {
                seminar = Seminar.deserialize(space);
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
            assertEquals(this.data[i].toString(), seminar.toString());
        }

    }

}
