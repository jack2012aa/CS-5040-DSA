// -------------------------------------------------------------------------
/**
 * A class of bintree.
 *
 *  @author Chang-Yu Huang
 *  @version 2024-10-15
 */
public class BinTree
{
    /** Root of the tree. */
    private BinTreeNode root;

    /** A box representing the x y coordinate of the world. */
    private Box world;

    /** A fly-weight empty leaf. */
    private static final BinTreeEmptyLeaf EMPTY_LEAF = BinTreeEmptyLeaf.NODE;

    /**
     * Create an empty bintree with specific world size.
     * @param world A box representing the x y coordinate of the world.
     */
    public BinTree(Box world)
    {
        this.root = BinTree.EMPTY_LEAF;
        this.world = world;
    }

    /**
     * Insert the seminar into the bintree.
     * The tree won't check duplication.
     * @param seminar The seminar to be inserted.
     */
    public void insert(Seminar seminar)
    {
        Point location = new Point(seminar.x(), seminar.y());
        this.root = this.root.insert(this.world, location, seminar, true);
    }

    /**
     * Delete the seminar from the bintree.
     * The tree won't check whether the seminar exists.
     * @param seminar The seminar to be deleted.
     */
    public void delete(Seminar seminar)
    {
        Point location = new Point(seminar.x(), seminar.y());
        this.root = this.root.delete(this.world, location, seminar, true);
    }

    /**
     * Search all seminars in a circle range and return a list of seminars and
     * how many nodes are visited during the search.
     * @param origin Origin of the search circle.
     * @param radius Radius of the search circle.
     * @return a list of seminars and how many nodes are visited during the
     * search.
     */
    public SearchResult search(Point origin, int radius)
    {
        // Create an empty list to start.
        Seminar[] emptyList = {};
        return this.root.search(origin, radius, this.world, true, emptyList);
    }

    /**
     * Print the tree structure.
     */
    public void print()
    {
        if (this.root == BinTreeEmptyLeaf.NODE)
        {
            System.out.println("E");
            return;
        }
        // Calculate height first.
        this.root.print(this.root.getHeight(), 0);
    }
}
