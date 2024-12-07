// -------------------------------------------------------------------------

import student.TestCase;

/**
 * Test {@link Box}
 *
 *  @author Chang-Yu Huang
 *  @version 2024-10-14
 */
public class BoxTest
    extends TestCase
{

    /** Test point A. */
    private Point pointA = new Point(0, 0);
    /** Test point B. */
    private Point pointB = new Point(3, 4);
    /** Test Box. */
    private Box box;

    /**
     * Create a new box.
     */
    public void setUp()
    {
        this.box = new Box(this.pointA, this.pointB);
    }


    /**
     * Test basic function.
     */
    public void testBox()
    {

        assertEquals(box.getTopLeft(), pointA);
        assertEquals(box.getBottomRight(), pointB);
        assertEquals("(0, 0) to (3, 4)", box.toString());

        box.setTopLeft(pointB);
        assertEquals(box.getTopLeft(), pointB);

        box.setBottomRight(pointA);
        assertEquals(box.getBottomRight(), pointA);

        this.box = new Box(new Point(0, 1), 1);
        assertEquals(-1, this.box.getTopLeft().getX());
        assertEquals(0, this.box.getTopLeft().getY());
        assertEquals(1, this.box.getBottomRight().getX());
        assertEquals(2, this.box.getBottomRight().getY());

    }

    /**
     * Test constructors.
     */
    public void testConstructors()
    {
        Box box2 = new Box(new Point(4, 4), 2);
        Box box3 = new Box(2, 6, 2, 6);
        assertTrue(box2.getTopLeft().equals(box3.getTopLeft()));
        assertTrue(box2.getBottomRight().equals(box3.getBottomRight()));
        assertEquals(2, box2.getTopLeft().getX());
        assertEquals(2, box2.getTopLeft().getY());
        assertEquals(6, box2.getBottomRight().getX());
        assertEquals(6, box2.getBottomRight().getY());

    }

    /**
     * Test interact.
     */
    public void testIsInteractWith()
    {

        Box anotherBox = new Box(new Point(5, 6), new Point(6, 7));
        assertTrue(!this.box.isIntersectWith(anotherBox));

        anotherBox = new Box(new Point(-4, 1), new Point(-1, 7));
        assertTrue(!this.box.isIntersectWith(anotherBox));

        anotherBox = new Box(new Point(-4, 5), new Point(9, 6));
        assertTrue(!this.box.isIntersectWith(anotherBox));

        anotherBox = new Box(new Point(-1, 3), new Point(1, 5));
        assertTrue(this.box.isIntersectWith(anotherBox));

        anotherBox = new Box(new Point(3, 4), new Point(5, 5));
        assertTrue(this.box.isIntersectWith(anotherBox));

        anotherBox = new Box(new Point(-1, -3), new Point(0, 0));
        assertTrue(this.box.isIntersectWith(anotherBox));

        anotherBox = new Box(new Point(2, -1), new Point(5, 2));
        assertTrue(this.box.isIntersectWith(anotherBox));
    }

    /**
     * Test getXMiddle() and getYMiddle.
     */
    public void testGetMiddle()
    {
        this.box = new Box(2, 8, 1, 5);
        assertEquals(5, this.box.getXMiddle());
        assertEquals(3, this.box.getYMiddle());
    }

    /**
     * Test get half.
     */
    public void testGetHalf()
    {
        this.box = new Box(2, 8, 1, 5);
        assertEquals("(2, 1) to (5, 5)", this.box.getLeft().toString());
        assertEquals("(6, 1) to (8, 5)", this.box.getRight().toString());
        assertEquals("(2, 1) to (8, 3)", this.box.getTop().toString());
        assertEquals("(2, 4) to (8, 5)", this.box.getBottom().toString());
    }

}
