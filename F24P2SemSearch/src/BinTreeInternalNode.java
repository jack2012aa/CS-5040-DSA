// -------------------------------------------------------------------------
/**
 *  A class representing every internal node in a bintree.
 *
 *  @author Chang-Yu Huang
 *  @version 2024-10-14
 */
public class BinTreeInternalNode implements BinTreeNode
{
    //~ Fields ................................................................
    private BinTreeNode left;
    private BinTreeNode right;

    //~ Constructors ..........................................................
    /**
     * Create an internal node connect to two empty leaf.
     */
    public BinTreeInternalNode()
    {
        this.left = BinTreeEmptyLeaf.NODE;
        this.right = BinTreeEmptyLeaf.NODE;
    }

    //~Public  Methods ........................................................

    /**
     * {@inheritDoc}
     */
    @Override
    public BinTreeNode insert(
        Box world, Point location, Seminar seminar, boolean xLayer)
    {

        if (this.isInLeft(world, location, xLayer))
        {
            this.left = this.left.insert(this.getLeftWorld(world, xLayer),
                location, seminar, !xLayer);
        }
        else
        {
            this.right = this.right.insert(this.getRightWorld(world, xLayer),
                location, seminar, !xLayer);
        }
        return this;

    }

    /**
     * Check the point should be in the left or right subtree.
     * @param world Bounding box of the world.
     * @param point The point to check.
     * @param xLayer Is this node in x layer or y layer.
     * @return True if the point should be in the left, else false.
     */
    boolean isInLeft(Box world, Point point, boolean xLayer)
    {
        if (xLayer)
        {
            return point.getX() <= world.getXMiddle();
        }
        // Use y coordinate.
        return point.getY() <= world.getYMiddle();
    }

    /**
     * Modify the world to the left half in the specific layer.
     * @param world The world to be changed.
     * @param xLayer Current layer.
     * @return Modified world.
     */
    Box getLeftWorld(Box world, boolean xLayer)
    {
        return (xLayer) ? world.getLeft() : world.getTop();
    }

    /**
     * Modify the world to the right half in the specific layer.
     * @param world The world to be changed.
     * @param xLayer Current layer.
     * @return Modified world.
     */
    Box getRightWorld(Box world, boolean xLayer)
    {
        return (xLayer) ? world.getRight() : world.getBottom();
    }

    // ----------------------------------------------------------
    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isLeaf()
    {
        return false;
    }


    // ----------------------------------------------------------
    /**
     * {@inheritDoc}
     */
    @Override
    public BinTreeNode delete(Box world, Point location, Seminar seminar,
        boolean xLayer)
    {
        // Check left or right.
        if (this.isInLeft(world, location, xLayer))
        {
            this.left = this.left.delete(this.getLeftWorld(world, xLayer),
                location, seminar, !xLayer);
        }
        else
        {
            this.right = this.right.delete(this.getRightWorld(world, xLayer),
                location, seminar, !xLayer);
        }

        // If both children is a empty or one is empty and another is leaf,
        // then this node can be removed.
        if (this.left.isLeaf() && this.right.isLeaf())
        {
            if (this.left instanceof BinTreeEmptyLeaf)
            {
                return this.right;
            }
            if (this.right instanceof BinTreeEmptyLeaf)
            {
                return this.left;
            }
        }
        return this;

    }


    // ----------------------------------------------------------
    /**
     * {@inheritDoc}
     */
    @Override
    public SearchResult search(Point origin, int radius, Box world,
        boolean xLayer, Seminar[] seminars)
    {
        // Generate a bounding box from the origin.
        Box boundingBox = new Box(origin, radius);

        // Use a variable to store result from child.
        SearchResult result;

        // Use two variable to store the final search result.
        Seminar[] appendedSeminars = seminars;
        int count = 1;

        // Check intersection between left world and bounding box.
        Box leftWorld = this.getLeftWorld(world, xLayer);
        if (boundingBox.isIntersectWith(leftWorld))
        {
            result = this.left.search(origin, radius, leftWorld,
                !xLayer, seminars);
            appendedSeminars = result.getSeminars();
            count += result.getCount();
        }

        // Check intersection between right world and bounding box.
        Box rightWorld = this.getRightWorld(world, xLayer);
        if (boundingBox.isIntersectWith(rightWorld))
        {
            result = this.right.search(origin, radius, rightWorld,
                !xLayer, appendedSeminars);
            appendedSeminars = result.getSeminars();
            count += result.getCount();
        }

        return new SearchResult(appendedSeminars, count);
    }


    // ----------------------------------------------------------
    /**
     * {@inheritDoc}
     */
    @Override
    public void print(int height, int level)
    {
        // Print *space*(I) and traverse.
        System.out.println(this.packageString("(I)", height - level));
        this.right.print(height, level + 1);
        this.left.print(height, level + 1);
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
        return Math.max(this.left.getHeight(), this.right.getHeight()) + 1;
    }

}
