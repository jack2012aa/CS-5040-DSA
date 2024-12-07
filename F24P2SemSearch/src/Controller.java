// -------------------------------------------------------------------------
/**
 * A class controlling trees.
 *
 *  @author Chang-Yu Huang
 *  @version 2024-09-27
 */
public class Controller
{
    //~ Fields ................................................................
    /** A BST ordered by id. */
    private BST<Integer> idTree = new BST<Integer>();
    /** A BST ordered by datetime string. */
    private BST<String> datetimeTree = new BST<String>();
    /** A BST ordered by cost. */
    private BST<Integer> costTree = new BST<Integer>();
    /** A BST ordered by keyword. */
    private BST<String> keywordTree = new BST<String>();
    /** A bin tree ordered by location. */
    private BinTree locationTree;
    /** Maximum edge of the location. */
    private int edge;

    //~ Constructors ..........................................................
    /**
     * Create a controller to manage seminars happens in a range.
     * @param length Length of the square range.
     */
    public Controller(int length)
    {
        // Only have valid input.
        this.edge = length - 1;
        this.locationTree = new BinTree(new Box(0, this.edge, 0, this.edge));
    }

    //~Public  Methods ........................................................
    /**
     * Create a Seminar using arguments, insert into trees and print insertion
     * result.
     * @param id ID of the seminar.
     * @param title Title of the seminar.
     * @param datetime Datetime of the seminar.
     * @param length Length of the seminar.
     * @param x X coordinate of the seminar.
     * @param y Y coordinate of the seminar.
     * @param cost Cost of the seminar.
     * @param keywords Keyword list.
     * @param description Description of the seminar.
     */
    public void insert(int id, String title, String datetime, int length,
        short x, short y, int cost, String[] keywords, String description)
    {

        // Check id.
        if (this.idTree.search(id).length != 0)
        {
            System.out.println(String.format("Insert FAILED - There is already "
                + "a record with ID %d", id));
            return;
        }

        // Check location.
        if (x > this.edge || y > this.edge || x < 0 || y < 0)
        {
            System.out.println(String.format(
                "Insert FAILED - Bad x, y coordinates: %d, %d", x, y));
            return;
        }

        // Create seminar.
        Seminar seminar = new Seminar(id, title, datetime, length, x, y, cost,
            keywords, description);

        // Insert into trees.
        this.idTree.insert(seminar.id(), seminar);
        this.datetimeTree.insert(seminar.date(), seminar);
        this.costTree.insert(seminar.cost(), seminar);
        for (String keyword: seminar.keywords())
        {
            this.keywordTree.insert(keyword, seminar);
        }
        this.locationTree.insert(seminar);

        // Print result.
        System.out.println(String.format("Successfully inserted record with "
            + "ID %d", id));
        System.out.println(seminar);
    }

    /**
     * Remove a seminar from trees.
     * @param id ID of the seminar.
     */
    public void delete(int id)
    {

        // Find the seminar in the idTree.
        Seminar[] seminars = this.idTree.search(id);

        // If it doesn't exist, return.
        if (seminars.length == 0)
        {
            System.out.println("Delete FAILED -- There is no record with ID "
                + id);
            return;
        }

        // Delete it from trees.
        Seminar seminar = seminars[0];
        this.idTree.delete(seminar.id(), seminar);
        this.datetimeTree.delete(seminar.date(), seminar);
        this.costTree.delete(seminar.cost(), seminar);
        for (String keyword: seminar.keywords())
        {
            this.keywordTree.delete(keyword, seminar);
        }
        this.locationTree.delete(seminar);

        // Print result.
        System.out.println(String.format("Record with ID %d successfully "
            + "deleted from the database", seminar.id()));

    }

    /**
     * Search by id and print the seminar.
     * @param id ID of the seminar.
     */
    public void searchByID(int id)
    {
        Seminar[] seminars = this.idTree.search(id);
        if (seminars.length == 0)
        {
            System.out.println(String.format("Search FAILED -- There is no "
                + "record with ID %s", id));
            return;
        }
        System.out.println(String.format("Found record with ID %d:", id));
        System.out.println(seminars[0]);
    }

    /**
     * Search by cost range and print seminars in the range.
     * @param lowerBound Lower bound of the cost.
     * @param upperBound Upper bound of the cost.
     */
    public void searchByCost(int lowerBound, int upperBound)
    {
        Seminar[] seminars = this.costTree.searchInRange(lowerBound,
            upperBound);
        System.out.println(String.format("Seminars with costs in range "
            + "%d to %d:", lowerBound, upperBound));
        for (Seminar seminar: seminars)
        {
            System.out.println(seminar);
        }
        System.out.println(String.format("%d nodes visited in this search",
            this.costTree.getCount()));
    }

    /**
     * Search by date range and print seminars in the range.
     * @param lowerBound Lower bound of the cost.
     * @param upperBound Upper bound of the cost.
     */
    public void searchByDate(String lowerBound, String upperBound)
    {
        Seminar[] seminars = this.datetimeTree.searchInRange(lowerBound,
            upperBound);
        System.out.println(String.format("Seminars with dates in range "
            + "%s to %s:", lowerBound, upperBound));
        for (Seminar seminar: seminars)
        {
            System.out.println(seminar);
        }
        System.out.println(String.format("%d nodes visited in this search",
            this.datetimeTree.getCount()));
    }

    /**
     * Search by keyword and print seminars that fit the keyword.
     * @param keyword Keyword of seminars.
     */
    public void searchByKeyword(String keyword)
    {
        System.out.println(String.format("Seminars matching keyword %s:",
            keyword));
        Seminar[] seminars = this.keywordTree.search(keyword);
        for (Seminar seminar: seminars)
        {
            System.out.println(seminar);
        }
    }

    /**
     * Search by location and print seminars in the range.
     * @param x x-coordinate of the origin.
     * @param y y-coordinate of the origin.
     * @param radius Radius of the range.
     */
    public void searchByLocation(int x, int y, int radius)
    {
        System.out.println(String.format(
            "Seminars within %d units of %d, %d:", radius, x, y));
        SearchResult result = this.locationTree.search(
            new Point(x, y), radius);
        for (Seminar seminar: result.getSeminars())
        {
            System.out.println(String.format(
                "Found a record with key value %d at %d, %d",
                seminar.id(), seminar.x(), seminar.y()));
        }
        System.out.println(result.getCount()
            + " nodes visited in this search");
    }

    /**
     * Print the datetime tree.
     */
    public void printDate()
    {
        System.out.println("Date Tree:");
        this.datetimeTree.print();
    }

    /**
     * Print the keyword tree.
     */
    public void printKeyword()
    {
        System.out.println("Keyword Tree:");
        this.keywordTree.print();
    }

    /**
     * Print the cost tree/
     */
    public void printCost()
    {
        System.out.println("Cost Tree:");
        this.costTree.print();
    }

    /**
     * Print the id tree.
     */
    public void printID()
    {
        System.out.println("ID Tree:");
        this.idTree.print();
    }

    /**
     * Print the location tree.
     */
    public void printLocation()
    {
        System.out.println("Location Tree:");
        this.locationTree.print();
    }

}
