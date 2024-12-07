// -------------------------------------------------------------------------
/**
 * A key-value pair used in hash table.
 *
 *  @author Chang-Yu Huang
 *  @version 2024-11-11
 */
public class Record
{
    //~ Fields ................................................................
    /** Key. */
    private int key;
    /** Value. */
    private Handle value;

    //~ Constructors ..........................................................
    /**
     * Create a key-value pair.
     * @param key An integer key.
     * @param value An handle value.
     */
    public Record(int key, Handle value)
    {
        this.key = key;
        this.value = value;
    }

    //~Public  Methods ........................................................
    // ----------------------------------------------------------
    /**
     * Get the current value of key.
     * @return The value of key for this object.
     */
    public int getKey()
    {
        return key;
    }

    // ----------------------------------------------------------
    /**
     * Set the value of key for this object.
     * @param key The new value for key.
     */
    public void setKey(int key)
    {
        this.key = key;
    }

    // ----------------------------------------------------------
    /**
     * Get the current value of value.
     * @return The value of value for this object.
     */
    public Handle getValue()
    {
        return value;
    }

    // ----------------------------------------------------------
    /**
     * Set the value of value for this object.
     * @param value The new value for value.
     */
    public void setValue(Handle value)
    {
        this.value = value;
    }



}
