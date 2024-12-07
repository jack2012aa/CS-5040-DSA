// -------------------------------------------------------------------------
/**
 * Unit test of {@link Handle}
 *
 *  @author Chang-Yu Huang
 *  @version 2024-11-10
 */
public class HandleTest extends student.TestCase
{

    /**
     * Test constructor.
     */
    public void testConstructor()
    {
        int start = 0;
        int size = 10;
        Handle handle = new Handle(0, 10);
        assertEquals(start, handle.getStart());
        assertEquals(size, handle.getSize());
        assertEquals(start + size, handle.getEnd());
        assertEquals("(0,10)", handle.toString());
    }

}
