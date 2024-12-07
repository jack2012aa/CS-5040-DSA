// -------------------------------------------------------------------------
/**
 * A data class storing the result of search() in {@link BinTreeNode}.
 *
 *  @author Chang-Yu Huang
 *  @version 2024-10-14
 */
public class SearchResult
{
    //~ Fields ................................................................
    /** Searched seminars. */
    private Seminar[] seminars;
    /** Number of visited node. */
    private int count;

    /**
     * Create a search result which finds seminars and visits count nodes.
     * @param seminars Searched seminars.
     * @param count Number of visited node.
     */
    public SearchResult(Seminar[] seminars, int count)
    {
        this.seminars = seminars;
        this.count = count;
    }

    // ----------------------------------------------------------
    /**
     * Get the current value of seminars.
     * @return The value of seminars for this object.
     */
    public Seminar[] getSeminars()
    {
        return seminars;
    }

    // ----------------------------------------------------------
    /**
     * Set the value of seminars for this object.
     * @param seminars The new value for seminars.
     */
    public void setSeminars(Seminar[] seminars)
    {
        this.seminars = seminars;
    }

    // ----------------------------------------------------------
    /**
     * Get the current value of count.
     * @return The value of count for this object.
     */
    public int getCount()
    {
        return count;
    }

    // ----------------------------------------------------------
    /**
     * Set the value of count for this object.
     * @param count The new value for count.
     */
    public void setCount(int count)
    {
        this.count = count;
    }


}
