// -------------------------------------------------------------------------
/**
 * A container to store index in the adjacency list.
 *
 *  @author Chang-Yu Huang
 *  @version 2024-09-05
 */
public class Node
{
    //~ Fields ................................................................
    /** Index in the adjacency list. */
    private int index;

    //~ Constructors ..........................................................
    /**
     * Create a Node with data.
     * @param index Index in the adjacency list..
     */
    public Node(int index)
    {
        this.setData(index);
    }

    //~Public  Methods ........................................................
    /**
     * Set data of the Node.
     * @param i Index in the adjacency list..
     */
    public void setData(int i)
    {
        this.index = i;
    }

    /**
     * Return the data.
     * @return Index in the adjacency list..
     */
    public int getIndex()
    {
        return this.index;
    }

}