// -------------------------------------------------------------------------

import student.TestCase;

/**
 * Test {@link Point}
 *
 *  @author Chang-Yu Huang
 *  @version 2024-10-14
 */
public class PointTest
    extends TestCase
{

    /**
     * Assign coordinates and check the string representation.
     */
    public void testPoint()
    {

        Point point = new Point(0, 1);
        assertEquals("(0, 1)", point.toString());
        assertEquals(0, point.getX());
        assertEquals(1, point.getY());

        point.setX(10);
        assertEquals("(10, 1)", point.toString());
        assertEquals(10, point.getX());
        assertEquals(1, point.getY());

        point.setY(100);
        assertEquals("(10, 100)", point.toString());
        assertEquals(10, point.getX());
        assertEquals(100, point.getY());
    }

    /**
     * Test isNear()
     */
    public void testIsNear()
    {
        Point pointA = new Point(0, 0);
        Point pointB = new Point(3, 4);
        assertTrue(pointA.isNear(pointB, 5));
        assertTrue(pointA.isNear(pointB, 6));
        assertTrue(!pointA.isNear(pointB, 2));

    }

    /**
     * Test equals()
     */
    public void testEquals()
    {
        Point pointA = new Point(0, 0);
        Point pointB = new Point(0, 0);
        assertTrue(pointA.equals(pointB));

        pointB.setX(1);
        assertTrue(!pointA.equals(pointB));

        pointB.setX(0);
        pointB.setY(1);
        assertTrue(!pointA.equals(pointB));

        pointB.setX(1);
        assertTrue(!pointA.equals(pointB));
    }

}
