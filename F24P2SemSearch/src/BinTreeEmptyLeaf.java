// -------------------------------------------------------------------------
/**
 * A empty leaf node.
 *
 *  @author Chang-Yu Huang
 *  @version 2024-10-14
 */
public class BinTreeEmptyLeaf
    implements BinTreeNode
{

    /** A singleton. */
    public static final BinTreeEmptyLeaf NODE = new BinTreeEmptyLeaf();

    // ----------------------------------------------------------
    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isLeaf()
    {
        return true;
    }


    // ----------------------------------------------------------
    /**
     * {@inheritDoc}
     */
    @Override
    public BinTreeNode insert(Box world, Point location, Seminar seminar,
        boolean xLayer)
    {
        // Create a normal leaf and return.
        return new BinTreeFullLeaf(location, seminar);
    }


    // ----------------------------------------------------------
    /**
     * {@inheritDoc}
     */
    @Override
    public BinTreeNode delete(Box world, Point location, Seminar seminar,
        boolean xLayer)
    {
        // This function should not be called.
        // A seminar which doesn't exist won't be deleted. It will be checked
        // in the Controller.
        return this;
    }


    // ----------------------------------------------------------
    /**
     * {@inheritDoc}
     */
    @Override
    public SearchResult search(Point origin, int radius, Box world,
        boolean xLayer, Seminar[] foundSeminars)
    {
        // No available seminar.
        return new SearchResult(foundSeminars, 1);
    }


    // ----------------------------------------------------------
    /**
     * {@inheritDoc}
     */
    @Override
    public void print(int height, int level)
    {
        System.out.println(this.packageString("(E)", height - level));
    }


    // ----------------------------------------------------------
    /**
     * {@inheritDoc}
     */
    @Override
    public String packageString(String s, int numOfSpaces)
    {
        String result = "";
        for (int i = 0; i < numOfSpaces; i++)
        {
            result = result + "    ";
        }
        return String.format(result + "%s", s);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getHeight()
    {
        return 0;
    }

}
