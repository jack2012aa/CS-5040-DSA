// -------------------------------------------------------------------------
/**
 * A bounding box in a 2-dimensional world.
 *
 *  @author Chang-Yu Huang
 *  @version 2024-10-14
 */
public class Box
{
    //~ Fields ................................................................
    /** Top left point of the box. */
    private Point topLeft;
    /** Bottom right point of the box. */
    private Point bottomRight;

    //~ Constructors ..........................................................
    /**
     * Create a box with two points.
     * @param topLeft Upper left point of the box.
     * @param bottomRight Lower right point of the box.
     */
    public Box(Point topLeft, Point bottomRight)
    {
        this.topLeft = topLeft;
        this.bottomRight = bottomRight;
    }

    /**
     * Create a box with origin and a side length.
     * @param origin Origin of the box.
     * @param sideLength Half of the side length of the box.
     */
    public Box(Point origin, int sideLength)
    {
        this.topLeft = new Point(
            origin.getX() - sideLength, origin.getY() - sideLength);
        this.bottomRight = new Point(
            origin.getX() + sideLength, origin.getY() + sideLength);
    }

    /**
     * Create a box using minimum and maximum on x and y axis.
     * @param xMin Minimum on x-axis.
     * @param xMax Maximum on x-axis.
     * @param yMin Minimum on y-axis.
     * @param yMax Maximum on y-axis.
     */
    public Box(int xMin, int xMax, int yMin, int yMax)
    {
        this.topLeft = new Point(xMin, yMin);
        this.bottomRight = new Point(xMax, yMax);
    }

    /**
     * Check whether is two boxes interact.
     * @param box Another box.
     * @return Is two boxes interact.
     */
    public boolean isIntersectWith(Box box)
    {
        return (this.topLeft.getX() <= box.bottomRight.getX()
            && this.bottomRight.getX() >= box.topLeft.getX())
            && (this.topLeft.getY() <= box.bottomRight.getY()
            && this.bottomRight.getY() >= box.topLeft.getY());
    }

    // ----------------------------------------------------------
    /**
     * Get the current value of topLeft.
     * @return The value of topLeft for this object.
     */
    public Point getTopLeft()
    {
        return topLeft;
    }

    // ----------------------------------------------------------
    /**
     * Set the value of topLeft for this object.
     * @param topLeft The new value for topLeft.
     */
    public void setTopLeft(Point topLeft)
    {
        this.topLeft = topLeft;
    }

    // ----------------------------------------------------------
    /**
     * Get the current value of bottomRight.
     * @return The value of bottomRight for this object.
     */
    public Point getBottomRight()
    {
        return bottomRight;
    }

    // ----------------------------------------------------------
    /**
     * Set the value of bottomRight for this object.
     * @param bottomRight The new value for bottomRight.
     */
    public void setBottomRight(Point bottomRight)
    {
        this.bottomRight = bottomRight;
    }

    /**
     * Return the string representation of the box.
     * @return The string representation of the box.
     */
    public String toString()
    {
        return String.format("%s to %s",
            this.topLeft.toString(), this.bottomRight.toString());
    }

    /**
     * Get the midpoint on the x-axis.
     * @return Midpoint on the x-axis.
     */
    public int getXMiddle()
    {
        return (this.topLeft.getX() + this.bottomRight.getX()) / 2;
    }

    /**
     * Get the midpoint on the y-axis.
     * @return Midpoint on the y-axis.
     */
    public int getYMiddle()
    {
        return (this.topLeft.getY() + this.bottomRight.getY()) / 2;
    }

    /**
     * Get the left half of the box, including middle.
     * @return Left half of the box.
     */
    public Box getLeft()
    {
        return new Box(this.topLeft.getX(), this.getXMiddle(),
            this.topLeft.getY(), this.bottomRight.getY());
    }

    /**
     * Get the right half of the box, excluding middle.
     * @return Right half of the box.
     */
    public Box getRight()
    {
        return new Box(this.getXMiddle() + 1, this.bottomRight.getX(),
            this.topLeft.getY(), this.bottomRight.getY());
    }

    /**
     * Get the upper half of the box, including middle.
     * @return Upper half of the box.
     */
    public Box getTop()
    {
        return new Box(this.topLeft.getX(), this.bottomRight.getX(),
            this.topLeft.getY(), this.getYMiddle());
    }

    /**
     * Get the bottom half of the box, excluding middle.
     * @return Upper half of the box.
     */
    public Box getBottom()
    {
        return new Box(this.topLeft.getX(), this.bottomRight.getX(),
            this.getYMiddle() + 1, this.bottomRight.getY());
    }


}
