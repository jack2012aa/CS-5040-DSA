// -------------------------------------------------------------------------
/**
 * A data class representing block in the memory manager.
 *
 *  @author Chang-Yu Huang
 *  @version 2024-11-10
 */
public class Handle
{
    //~ Fields ................................................................
    /** Starting position of the block. */
    private int start;
    /** Size of the block. */
    private int size;

    //~ Constructors ..........................................................
    /**
     * Create a handle with start position and size.
     * @param start Starting position of the block.
     * @param size Size of the block.
     */
    public Handle(int start, int size)
    {
        this.size = size;
        this.start = start;
    }

    //~Public  Methods ........................................................
    // ----------------------------------------------------------
    /**
     * Get the current value of start.
     * @return The value of start for this object.
     */
    public int getStart()
    {
        return start;
    }

    // ----------------------------------------------------------
    /**
     * Set the value of start for this object.
     * @param start The new value for start.
     */
    public void setStart(int start)
    {
        this.start = start;
    }

    // ----------------------------------------------------------
    /**
     * Get the current value of size.
     * @return The value of size for this object.
     */
    public int getSize()
    {
        return size;
    }

    // ----------------------------------------------------------
    /**
     * Set the value of size for this object.
     * @param size The new value for size.
     */
    public void setSize(int size)
    {
        this.size = size;
    }

    /**
     * Get the ending position of the handle.
     * The byte array in the memory will be [start, end).
     * @return The ending position of the handle.
     */
    public int getEnd()
    {
        return this.start + this.size;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString()
    {
        return String.format("(%d,%d)", this.start, this.size);
    }


}
