// -------------------------------------------------------------------------
/**
 * A class to contain a hash table Node and its key.
 *
 *  @author Chang-Yu Huang
 *  @version 2024-09-02
 */
public class Record
{
    //~ Fields ................................................................
    /** Key of the record in the hash table. */
    private String key;
    /** A Node in the hash table. */
    private Node value;

    //~ Constructors ..........................................................
    /**
     * Create a Record by key and node.
     * @param key Key of the record in the hash table.
     * @param node A Node in the hash table.
     */
    public Record(String key, Node node)
    {
        this.setKey(key);
        this.setNode(node);
    }

    //~Public  Methods ........................................................
    // ----------------------------------------------------------
    /**
     * Get the current value of key.
     * @return The value of key for this object.
     */
    public String getKey()
    {
        return key;
    }

    // ----------------------------------------------------------
    /**
     * Set the value of key for this object.
     * @param key The new value for key.
     */
    public void setKey(String key)
    {
        this.key = key;
    }

    // ----------------------------------------------------------
    /**
     * Get the current value of node.
     * @return The value of node for this object.
     */
    public Node getNode()
    {
        return value;
    }

    // ----------------------------------------------------------
    /**
     * Set the value of node for this object.
     * @param node The new value for node.
     */
    public void setNode(Node node)
    {
        this.value = node;
    }


}
