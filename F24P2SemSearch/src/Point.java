// -------------------------------------------------------------------------
/**
 * A class representing a point in a 2-dimensional world.
 *
 *  @author Chang-Yu Huang
 *  @version 2024-10-14
 */
public class Point
{
    //~ Fields ................................................................
    /** x coordinate. */
    private int x;
    /** y coordinate. */
    private int y;

    //~ Constructors ..........................................................
    /**
     * Create a point at (x, y).
     * @param x The x coordinate.
     * @param y The y coordinate.
     */
    public Point(int x, int y)
    {
        this.x = x;
        this.y = y;
    }

    //~Public  Methods ........................................................

    // ----------------------------------------------------------
    /**
     * Get the current value of x.
     * @return The value of x for this object.
     */
    public int getX()
    {
        return x;
    }

    // ----------------------------------------------------------
    /**
     * Set the value of x for this object.
     * @param x The new value for x.
     */
    public void setX(int x)
    {
        this.x = x;
    }

    // ----------------------------------------------------------
    /**
     * Get the current value of y.
     * @return The value of y for this object.
     */
    public int getY()
    {
        return y;
    }

    // ----------------------------------------------------------
    /**
     * Set the value of y for this object.
     * @param y The new value for y.
     */
    public void setY(int y)
    {
        this.y = y;
    }

    /**
     * Return the string representation of the point.
     * @return The string representation of the point.
     */
    public String toString()
    {
        return String.format("(%d, %d)", this.x, this.y);
    }

    /**
     * Check if the distance between this point and another point is lower than
     * a number.
     * @param point Another point.
     * @param distance Goal distance.
     * @return If two points are closer than the distance.
     */
    public boolean isNear(Point point, int distance)
    {
        int xDifference = Math.abs(this.x - point.getX());
        int yDifference = Math.abs(this.y - point.getY());
        return xDifference * xDifference + yDifference * yDifference
            <= distance * distance;
    }

    /**
     * Compare two points and see whether they represent the same point.
     * @param point Another point.
     * @return Are two points the same.
     */
    public boolean equals(Object point)
    {
        if (!(point instanceof Point)) {
            return false;
        }
        Point p = (Point) point;
        return this.x == p.getX() && this.y == p.getY();
    }

}
