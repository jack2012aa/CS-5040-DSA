// -------------------------------------------------------------------------
/**
 * A linked list node.
 *
 *  @author Chang-Yu Huang
 *  @version 2024-10-30
 *  @param <T> Data class.
 */
public class ListNode<T>
{
    //~ Fields ................................................................
    private T data;
    private ListNode<T> next;

    //~ Constructors ..........................................................
    /**
     * Create a new node with data.
     * @param data Data in the node.
     */
    public ListNode(T data)
    {
        this.data = data;
        this.next = null;
    }

    // ----------------------------------------------------------
    /**
     * Get the current value of data.
     * @return The value of data for this object.
     */
    public T getData()
    {
        return data;
    }

    // ----------------------------------------------------------
    /**
     * Set the value of data for this object.
     * @param data The new value for data.
     */
    public void setData(T data)
    {
        this.data = data;
    }

    // ----------------------------------------------------------
    /**
     * Get the current value of next.
     * @return The value of next for this object.
     */
    public ListNode<T> getNext()
    {
        return next;
    }

    // ----------------------------------------------------------
    /**
     * Set the value of next for this object.
     * @param next The new value for next.
     */
    public void setNext(ListNode<T> next)
    {
        this.next = next;
    }

    //~Public  Methods ........................................................


}
