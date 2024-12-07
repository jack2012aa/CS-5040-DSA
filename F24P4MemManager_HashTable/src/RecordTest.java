// -------------------------------------------------------------------------

import student.TestCase;

/**
 * Test {@link Record}
 *
 *  @author Chang-Yu Huang
 *  @version 2024-12-03
 */
public class RecordTest
    extends TestCase
{

    /**
     * General test.
     */
    public void testGeneral()
    {

        Handle[] handles = {new Handle(0, 10), new Handle(11, 100)};
        Record record = new Record(0, handles[0]);
        assertEquals(0, record.getKey());
        assertEquals(handles[0], record.getValue());
        record = new Record(11, handles[1]);
        assertEquals(11, record.getKey());
        assertEquals(handles[1], record.getValue());

    }

}
