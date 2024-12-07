// -------------------------------------------------------------------------

import student.TestCase;

/**
 * Test {@link LinkedList}
 *
 *  @author Chang-Yu Huang
 *  @version 2024-10-30
 */
public class LinkedListTest
    extends TestCase
{
    //~ Fields ................................................................
    private LinkedList<Integer> list = new LinkedList<Integer>();

    //~Public  Methods ........................................................
    /**
     * General functionality test.
     */
    public void testGeneral()
    {
        for (int i = 0; i < 10; i++)
        {
            this.list.insertTail(i);
        }
        assertEquals(10, this.list.getSize());
        for (int i = 0; i < 10; i++)
        {
            assertEquals(i, (int) this.list.popFront());
        }
        assertEquals(0, this.list.getSize());

        for (int i = 0; i < 10; i++)
        {
            this.list.insertTail(i);
        }
        LinkedList<Integer> anotherList = new LinkedList<Integer>();
        for (int i = 0; i < 10; i++)
        {
            anotherList.insertTail(i);
        }
        this.list.insertTail(anotherList);
        assertEquals(20, this.list.getSize());
        int sum = 0;
        for (int i: this.list)
        {
            sum += i;
        }
        assertEquals(90, sum);
    }

}
