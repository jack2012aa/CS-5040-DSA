// -------------------------------------------------------------------------

import student.TestCase;

/**
 * Test {@link Record}.
 *
 *  @author Chang-Yu Huang
 *  @version 2024-09-05
 */
public class RecordTest
    extends TestCase
{
    //~ Fields ................................................................
    /** Tested Record. */
    private Record record;
    /** Record's key. */
    private String key;
    /** Node's data. */
    private int data;
    /** Record's node. */
    private Node node;

    //~Public  Methods ........................................................
    /** Create a record with key "1" and node index in 1. */
    public void setUp()
    {
        this.key = "1";
        this.data = 1;
        this.node = new Node(data);
        this.record = new Record(key, this.node);
    }

    /** Test key and node getter. */
    public void testGetter()
    {
        assertEquals(this.key, this.record.getKey());
        assertEquals(this.node, this.record.getNode());
    }

    /** Test key and Node setter by set and check. */
    public void testSetter()
    {
        this.key = "0";
        this.node = new Node(0);
        this.record.setKey(this.key);
        this.record.setNode(this.node);
        assertEquals(this.key, this.record.getKey());
        assertEquals(this.node, this.record.getNode());
    }

}
