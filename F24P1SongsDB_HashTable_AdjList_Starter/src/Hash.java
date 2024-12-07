/**
 * Hash table class
 *
 * @author Chang-Yu Huang
 * @version 2024-09-05
 */

public class Hash
{

    /** Elements in the hash table. */
    private Record[] allrecords;
    /** Number of elements in the hash table. */
    private int numberOfRecords;
    /** Tombstone in the hash table. */
    private Record tombstone = new Record("Tombstone", new Node(-1));

    /**
     * Create a hash table with capacity c.
     * @param c The initial capacity of the hash table. Must be greater than 0.
     * @throws IllegalArgumentException When c <= 0.
     */
    public Hash(int c) throws IllegalArgumentException
    {
        if (c <= 0)
        {
            String errorMessage = String.format(
                "c must be greater than 0. Got %d.", c);
            throw new IllegalArgumentException(errorMessage);
        }

        this.allrecords = new Record[c];
        this.numberOfRecords = 0;

    }

    /**
     * Quadratic probe the index.
     * @param index Initial index.
     * @param steps Steps of probing.
     * @return the quadratic probe of the index.
     */
    int probe(int index, int steps)
    {
        return (index + steps * steps) % this.allrecords.length;
    }

    /**
     * Search the index of the Record of key in the hash table.
     * @param key Key of the Record to search.
     * @return The index of the Record or -1.
     */
    int findIndex(String key)
    {
        int initialIndex = Hash.h(key, this.allrecords.length);
        int steps = 0;
        int currentIndex = this.probe(initialIndex, steps);

        // To avoid infinite loop, we have to remember which index is visited.
        boolean[] visited = new boolean[this.allrecords.length];

        while (this.allrecords[currentIndex] != null
            && !visited[currentIndex])
        {
            if (this.allrecords[currentIndex].getKey().equals(key)
                && this.allrecords[currentIndex] != this.tombstone)
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
    int findInsertIndex(String key)
    {
        int initialIndex = Hash.h(key, this.allrecords.length);
        int steps = 0;
        int currentIndex = this.probe(initialIndex, steps);
        // Loop until find a null or a tombstone.
        while (this.allrecords[currentIndex] != null
            && this.allrecords[currentIndex] != this.tombstone)
        {
            currentIndex = this.probe(initialIndex, ++steps);
        }
        return currentIndex;
    }

    /**
     * Check is there any tombstone in the table.
     * This function is used for mutation test.
     * @return True if there is no tombstone, else false.
     */
    boolean noTombstone()
    {
        for (Record record: this.allrecords)
        {
            if (record == this.tombstone)
            {
                return false;
            }
        }
        return true;
    }

    /**
     * Map record to key in the hash table.
     * If key exists, return false. Else return true.
     * @param key Key of the Record.
     * @param node Value of the Record.
     * @return True if insert successfully, else false.
     */
    public boolean insert(String key, Node node)
    {
        // Check exists by check index.
        if (this.findIndex(key) != -1)
        {
            return false;
        }

        // Although check first waste some time, but the Spec require to resize
        // before a real insertion.
        // Check capacity.
        if (this.numberOfRecords + 1 > this.allrecords.length / 2)
        {
            Record[] elements = this.allrecords.clone();
            this.allrecords = new Record[this.allrecords.length * 2];
            for (Record record: elements)
            {
                if (record != null && record != this.tombstone)
                {
                    this.allrecords[this.findInsertIndex(record.getKey())]
                        = record;
                }
            }
        }

        // Insert.
        int insertIndex = this.findInsertIndex(key);
        this.allrecords[insertIndex] = new Record(key, node);
        this.numberOfRecords++;
        return true;
    }

    /**
     * Remove a key in the hash table.
     * If key doesn't exist, return false. Else return true.
     * @param key Key of the Record.
     * @return True if remove successfully, else false.
     */
    public boolean remove(String key)
    {

        int possibleIndex = this.findIndex(key);
        // The index points to null if the key doesn't exist.
        if (possibleIndex == -1)
        {
            return false;
        }

        this.allrecords[possibleIndex] = this.tombstone;
        this.numberOfRecords--;
        return true;

    }

    /**
     * Find the Node which the key points to.
     * @param key Key of the Record.
     * @return The Node which the key points to, or null.
     */
    public Node find(String key)
    {
        if (this.findIndex(key) != -1)
        {
            return this.allrecords[this.findIndex(key)].getNode();
        }
        return null;
    }

    /**
     * Print every non-null Record in the hash table and its index.
     * @return String of every non-null key in the hash table and its index.
     */
    public String print()
    {
        // Count the number of Tombstone in the table.
        int numberOfTombstone = 0;
        for (Record record: this.allrecords)
        {
            if (record == this.tombstone)
            {
                numberOfTombstone++;
            }
        }
        String[] recordStringList = new String[this.numberOfRecords +
                                               numberOfTombstone];
        // Tail index of the list.
        int tail = 0;
        for (int i = 0; i < this.allrecords.length; i++)
        {
            if (this.allrecords[i] != null)
            {
                if (this.allrecords[i] != this.tombstone)
                {
                    recordStringList[tail++] = String.format(
                        "%d: |%s|", i, this.allrecords[i].getKey());
                }
                else
                {
                    recordStringList[tail++] = String.format(
                        "%d: TOMBSTONE", i);
                }
            }
        }
        String recordString = String.join("\n", recordStringList);
        if (!recordString.equals(""))
        {
            System.out.println(recordString);
        }
        return recordString;
    }

    /**
     * Get the current capacity of the hash table.
     * @return the capacity of the hash table.
     */
    public int getCapacity()
    {
        return this.allrecords.length;
    }

    /**
     * Get the numberOfRecords
     * @return numberOfRecords
     */
    public int getSize()
    {
        return this.numberOfRecords;
    }


    /**
     * Compute the hash function
     *
     * @param s
     *            The string that we are hashing
     * @param length
     *            Length of the hash table (needed because this method is
     *            static)
     * @return
     *         The hash function value (the home slot in the table for this key)
     */
    public static int h(String s, int length) {
        int intLength = s.length() / 4;
        long sum = 0;
        for (int j = 0; j < intLength; j++) {
            char[] c = s.substring(j * 4, (j * 4) + 4).toCharArray();
            long mult = 1;
            for (int k = 0; k < c.length; k++) {
                sum += c[k] * mult;
                mult *= 256;
            }
        }

        char[] c = s.substring(intLength * 4).toCharArray();
        long mult = 1;
        for (int k = 0; k < c.length; k++) {
            sum += c[k] * mult;
            mult *= 256;
        }

        return (int)(Math.abs(sum) % length);
    }
}
