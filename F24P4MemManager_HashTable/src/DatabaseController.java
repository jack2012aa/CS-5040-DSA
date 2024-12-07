// -------------------------------------------------------------------------
/**
 * A database to store Seminar.
 *
 *  @author Chang-Yu Huang
 *  @version 2024-11-12
 */
public class DatabaseController
{
    //~ Fields ................................................................
    /** Memory pool. */
    private MemoryManager memoryManager;
    /** Hash table. */
    private HashTable table;

    //~ Constructors ..........................................................
    /**
     * Create a database with fix memory size and hash table size.
     * @param memorySize Initial size of the memory in bytes.
     * @param tableSize How many record can be stored in the hash table.
     */
    public DatabaseController(int memorySize, int tableSize)
    {
        this.memoryManager = new MemoryManager(memorySize);
        this.table = new HashTable(tableSize);
    }

    //~Public  Methods ........................................................
    /**
     * Insert a new seminar record.
     * @param id Seminar id.
     * @param seminar The seminar to insert.
     */
    public void insert(int id, Seminar seminar)
    {

        // Find duplicate in the hash table.
        if (this.table.find(id) != null)
        {
            System.out.println(String.format("Insert FAILED - There is "
                + "already a record with ID %d", id));
            return;
        }

        // Insert the seminar into the memory pool.
        Handle handle = new Handle(1, 1);
        try
        {
            handle = this.memoryManager.insert(seminar.serialize());
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        // Insert the key and handle into the table.
        int initialCapacity = this.table.getCapacity();
        this.table.insert(new Record(id, handle));

        // Print insertion result.
        if (initialCapacity != this.table.getCapacity())
        {
            System.out.println("Hash table expanded to "
                + this.table.getCapacity() + " records");
        }
        System.out.println("Successfully inserted record with ID " + id);
        System.out.println(seminar);
        System.out.println("Size: " + handle.getSize());

    }

    /**
     * Delete a record from the database.
     * @param id The id of the seminar.
     */
    public void delete(int id)
    {

        // Find the record.
        Record record = this.table.find(id);
        if (record == null)
        {
            System.out.println("Delete FAILED -- There is no record with ID "
                + id);
            return;
        }

        // Delete the record from the hash table.
        this.table.remove(id);

        // Free the memory.
        this.memoryManager.remove(record.getValue());

        // Print result.
        System.out.println("Record with ID " + id
            + " successfully deleted from the database");

    }

    /**
     * Search a record in the database.
     * @param id The id of the seminar.
     */
    public void search(int id)
    {

        // Find the record.
        Record record = this.table.find(id);
        if (record == null)
        {
            System.out.println("Search FAILED -- There is no record with ID "
                + id);
            return;
        }

        // Get seminar in the memory pool.
        byte[] serializedSeminar = new byte[record.getValue().getSize()];
        this.memoryManager.get(serializedSeminar, record.getValue());
        Seminar seminar = new Seminar();
        try
        {
            seminar = Seminar.deserialize(serializedSeminar);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        // Print result.
        System.out.println("Found record with ID " + id + ":");
        System.out.println(seminar);

    }

    /**
     * Print records in the hash table.
     */
    public void printHashTable()
    {
        System.out.println("Hashtable:");
        this.table.print();
    }

    /**
     * Print free blocks in the memory pool.
     */
    public void printMemoryManager()
    {
        System.out.println("Freeblock List:");
        this.memoryManager.print();
    }

}
