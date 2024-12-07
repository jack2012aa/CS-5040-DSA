// -------------------------------------------------------------------------
/**
 * A class representing non-empty leaves in a bintree.
 *
 *  @author Chang-Yu Huang
 *  @version 2024-10-14
 */
public class BinTreeFullLeaf
    implements BinTreeNode
{

    /** The point this node representing. */
    private Point point;

    /** Seminars held in this location. */
    private Seminar[] seminars;

    /**
     * Create a leaf with specific location and a seminar.
     * @param location Location of the leaf.
     * @param seminar A seminar held in this location.
     */
    public BinTreeFullLeaf(Point location, Seminar seminar)
    {
        this.point = location;
        this.seminars = new Seminar[1];
        this.seminars[0] = seminar;
    }

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
        // The new seminar is held here.
        if (this.point.equals(location))
        {
            // Use an insertion sort to put the new seminar in correct order.
            Seminar[] newSeminars = new Seminar[this.seminars.length + 1];
            // Move the pointer in newSemianrs.
            int rightMove = 0;
            for (int i = 0; i < this.seminars.length; i++)
            {
                // Put the new seminar in if it hasn't been put and here is the
                // correct position.
                if (rightMove == 0 && this.seminars[i].id() > seminar.id())
                {
                    newSeminars[i] = seminar;
                    rightMove = 1;
                }
                newSeminars[i + rightMove] = this.seminars[i];
            }
            // The new node may have to be inserted at the end.
            if (rightMove == 0)
            {
                newSeminars[newSeminars.length - 1] = seminar;
            }

            this.seminars = newSeminars;
            return this;
        }

        // Split the current node.
        BinTreeNode root = new BinTreeInternalNode();
        for (Seminar oldSeminar: this.seminars)
        {
            // The new internal node is in the same level with current leaf.
            root = root.insert(world, new Point(oldSeminar.x(),
                oldSeminar.y()), oldSeminar, xLayer);
        }
        root = root.insert(world, location, seminar, xLayer);
        return root;

    }


    // ----------------------------------------------------------
    /**
     * {@inheritDoc}
     */
    @Override
    public BinTreeNode delete(Box world, Point location, Seminar seminar,
        boolean xLayer)
    {

        // No invalid input.
        // Hence, I just remove the seminar in the seminar array.

        if (this.seminars.length == 1) {
            return BinTreeEmptyLeaf.NODE;
        }

        Seminar[] newSeminars = new Seminar[this.seminars.length - 1];
        int moveRight = 0;
        for (int i = 0; i < newSeminars.length; i++)
        {
            if (seminar == this.seminars[i])
            {
                moveRight = 1;
            }
            newSeminars[i] = this.seminars[i + moveRight];
        }
        this.seminars = newSeminars;

        // The seminar may at the end of the list.
        // Just ignore it.
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
        // Check the distance of this leaf to the origin.
        if (this.point.isNear(origin, radius))
        {
            // Add seminars into the list.
            Seminar[] appendedSeminars = new Seminar[foundSeminars.length
                                                     + this.seminars.length];
            for (int i = 0; i < foundSeminars.length; i++)
            {
                appendedSeminars[i] = foundSeminars[i];
            }
            for (int i = 0; i < this.seminars.length; i++)
            {
                appendedSeminars[foundSeminars.length + i] = this.seminars[i];
            }
            return new SearchResult(appendedSeminars, 1);
        }

        // This point is not in the range, but it is visited.
        return new SearchResult(foundSeminars, 1);
    }


    // ----------------------------------------------------------
    /**
     * {@inheritDoc}
     */
    @Override
    public void print(int height, int level)
    {
        // Generate seminars id string.
        String ids = "";
        for (int i = 0; i < this.seminars.length; i++)
        {
            ids = ids + " " + String.valueOf(this.seminars[i].id());
        }

        // Put things together and print.
        System.out.println(this.packageString(String.format(
            "(Leaf with %d objects:%s)", this.seminars.length, ids),
            height - level));
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

    // ----------------------------------------------------------
    /**
     * Get the current value of point.
     * @return The value of point for this object.
     */
    public Point getPoint()
    {
        return point;
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

    /**
     * {@inheritDoc}
     */
    @Override
    public int getHeight()
    {
        return 0;
    }

}
