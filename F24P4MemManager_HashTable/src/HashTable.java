/**
 * A hash table class modified from P1.
 *
 * @author Chang-Yu Huang
 * @version 2024-11-11
 */

public class HashTable
{

    /** When the table size has to expand. */
    public static final int BALANCE = 2;
    /** Elements in the hash table. */
    private Record[] allRecords;
    /** Number of elements in the hash table. */
    private int numberOfRecords;
    /** Tombstone in the hash table. */
    private Record tombstone = new Record(-1, null);

    /**
     * Create a hash table with capacity c.
     * @param c The initial capacity of the hash table. Must be greater than 0.
     */
    public HashTable(int c)
    {
        this.allRecords = new Record[c];
        this.numberOfRecords = 0;
    }

    /**
     * Hash function.
     * @param key Key.
     * @return Hash value.
     */
    private int hash(int key)
    {
        return key % this.allRecords.length;
    }

    /**
     * Quadratic probe the index.
     * @param index Initial index.
     * @param steps Steps of probing.
     * @return the quadratic probe of the index.
     */
    private int probe(int index, int steps)
    {
        return (index + (steps * steps + steps) / 2) % this.allRecords.length;
    }

    /**
     * Search the index of the Record of key in the hash table.
     * @param key Key of the Record to search.
     * @return The index of the Record or -1.
     */
    private int findIndex(int key)
    {
        int initialIndex = this.hash(key);
        int steps = 0;
        int currentIndex = this.probe(initialIndex, steps);

        // To avoid infinite loop, we have to remember which index is visited.
        boolean[] visited = new boolean[this.allRecords.length];

        while (this.allRecords[currentIndex] != null
            && !visited[currentIndex])
        {
            if (this.allRecords[currentIndex].getKey() == key
                && this.allRecords[currentIndex] != this.tombstone)
            {
                return currentIndex;
            }

            visited[currentIndex] = true;
            currentIndex = this.probe(initialIndex, ++steps);
        }
        return -1;
    }

    /**
     * Find a good place to add new Record.
     * @param key Key of the Record.
     * @return An index of null or Tombstone.
     */
    private int findInsertIndex(int key)
    {
        int initialIndex = this.hash(key);
        int steps = 0;
        int currentIndex = this.probe(initialIndex, steps);
        // Loop until find a null or a tombstone.
        while (this.allRecords[currentIndex] != null
            && this.allRecords[currentIndex] != this.tombstone)
        {
            currentIndex = this.probe(initialIndex, ++steps);
        }
        return currentIndex;
    }

    /**
     * Insert a new record into the table.
     * @param record The record.
     */
    public void insert(Record record)
    {

        // Check capacity.
        if (this.numberOfRecords + 1 > this.allRecords.length /
            HashTable.BALANCE)
        {
            Record[] elements = this.allRecords.clone();
            this.allRecords = new Record[this.allRecords.length * 2];
            for (Record r: elements)
            {
                if (r != null && r != this.tombstone)
                {
                    this.allRecords[this.findInsertIndex(r.getKey())]
                        = r;
                }
            }
        }

        // Insert.
        int insertIndex = this.findInsertIndex(record.getKey());
        this.allRecords[insertIndex] = record;
        this.numberOfRecords++;

    }

    /**
     * Remove a record in the hash table.
     * @param key The key of the record to be removed.
     */
    public void remove(int key)
    {

        int possibleIndex = this.findIndex(key);
        // The index points to null if the key doesn't exist.
        if (possibleIndex != -1)
        {
            this.allRecords[possibleIndex] = this.tombstone;
            this.numberOfRecords--;
        }

    }

    /**
     * Find the record which the key points to.
     * @param key Key of the Record.
     * @return Record in the hash table.
     */
    public Record find(int key)
    {
        if (this.findIndex(key) != -1)
        {
            return this.allRecords[this.findIndex(key)];
        }
        return null;
    }

    /**
     * Print every non-null Record in the hash table and its index.
     */
    public void print()
    {

        // Count the number of records.
        int count = 0;
        for (int i = 0; i < this.allRecords.length; i++)
        {
            if (this.allRecords[i] == null)
            {
                continue;
            }
            if (this.allRecords[i] == this.tombstone)
            {
                System.out.println(i + ": TOMBSTONE");
                continue;
            }
            System.out.println(i + ": " + this.allRecords[i].getKey());
            count++;
        }
        System.out.println("total records: " + count);

    }

    /**
     * Get the current capacity of the hash table.
     * @return the capacity of the hash table.
     */
    public int getCapacity()
    {
        return this.allRecords.length;
    }

    /**
     * Get the numberOfRecords
     * @return numberOfRecords
     */
    public int getSize()
    {
        return this.numberOfRecords;
    }

}
