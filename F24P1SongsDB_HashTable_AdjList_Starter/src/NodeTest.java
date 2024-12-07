import student.TestCase;

// -------------------------------------------------------------------------
/**
 * Test {@link Node}.
 *
 *  @author Chang-Yu Huang
 *  @version 2024-09-02
 */
public class NodeTest extends TestCase
{
    //~ Fields ................................................................
    private Node node;

    //~Public  Methods ........................................................
    /**
     * Test Node constructor and setter by set and check getter.
     */
    public void testNode()
    {
        int data = 0;
        this.node = new Node(data);
        assertEquals(data, this.node.getIndex());

        data = 1;
        this.node.setData(data);
        assertEquals(data, this.node.getIndex());


    }
}
