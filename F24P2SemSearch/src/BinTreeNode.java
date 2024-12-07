// -------------------------------------------------------------------------
/**
 * An interface of bin tree node.
 *
 *  @author Chang-Yu Huang
 *  @version 2024-10-14
 */
public interface BinTreeNode
{

    /**
     * Return if the node is a leaf node.
     * @return Is the node a leaf.
     */
    public boolean isLeaf();

    /**
     * Insert a new seminar by its location.
     * @param world Bounding box of the world in the current layer.
     * @param location Location of the seminar.
     * @param seminar The seminar to insert.
     * @param xLayer Is current layer a division of x or y.
     * @return Root of the subtree after the insertion.
     */
    public BinTreeNode insert(
        Box world, Point location, Seminar seminar, boolean xLayer);

    /**
     * Remove a seminar in the tree.
     * @param world Bounding box of the world in the current layer.
     * @param location Location of the seminar.
     * @param seminar The seminar to insert.
     * @param xLayer Is current layer a division of x or y.
     * @return Root of the subtree after the deletion.
     */
    public BinTreeNode delete(
        Box world, Point location, Seminar seminar, boolean xLayer);

    /**
     * Search for seminars in range.
     * @param origin Origin of the searching circle.
     * @param radius Radius of the searching circle.
     * @param world Bounding box of the world in the current layer.
     * @param xLayer Is current layer a division of x or y.
     * @param seminars Currently found seminars.
     * @return A list of seminars in range and number of visited node.
     */
    public SearchResult search(
        Point origin, int radius, Box world, boolean xLayer,
        Seminar[] seminars);

    /**
     * Print the current node with some leading space.
     * @param height Height of the bin tree.
     * @param level Level of the current node.
     */
    public void print(int height, int level);

    /**
     * Package the printed string with leading space.
     * @param s String to be packaged.
     * @param numOfSpaces Number of leading spaces / 4.
     * @return Packaged string.
     */
    String packageString(String s, int numOfSpaces);

    /**
     * Get the height of the subtree which this node is the root.
     * @return Height of the subtree.
     */
    public int getHeight();

}
