import student.TestCase;

/**
 * @author Chang-Yu Huang
 * @version 2024-09-05
 */
public class HashTest extends TestCase {

    /** Tested hash table. */
    private Hash hash;
    /** Initial size of the table. */
    private int initialCapacity = 1;
    /** Data */
    private Node[] data = {
        new Node(0), new Node(1), new Node(2),
        new Node(3), new Node(4)};
    /** Key of the Data. */
    private String[] keys = {"replica", "Gift", "soramimi", "Love together",
        "Light Dance"};

    /**
     * Create a hash table and insert some data..
     */
    public void setUp()
    {
        this.hash = new Hash(this.initialCapacity);
        assertEquals(this.initialCapacity, this.hash.getCapacity());
        for (int i = 0; i < this.data.length; i++)
        {
            assertTrue(this.hash.insert(this.keys[i], this.data[i]));
        }
    }

    /** Test constructor. */
    public void testConstructor()
    {
        try
        {
            this.hash = new Hash(-1);
            fail("Should throw an exception.");
        }
        catch (IllegalArgumentException ex)
        {
            // Pass.
        }
    }

    /** Test probe(). */
    public void testProbe()
    {
        int initialIndex = 2;
        for (int steps = 0; steps < 4; steps++)
        {
            assertEquals(
                (initialIndex + steps * steps) % this.hash.getCapacity(),
                this.hash.probe(initialIndex, steps)
            );
        }
    }

    /**
     * Test findIndex() when there is a key name "Tombstone". The function
     * should check is it a real Tombstone or not.
     */
    public void testFindIndex()
    {

        // Insert two key with same hash value.
        assertEquals(Hash.h("Cindy", this.hash.getCapacity()),
            Hash.h("Tombstone", this.hash.getCapacity()));

        this.hash.insert("Cindy", this.data[0]);
        // Get the correct index of "Tombstone"
        int index = this.hash.findInsertIndex("Tombstone");
        this.hash.insert("Tombstone", this.data[1]);

        // Remove the first one.
        this.hash.remove("Cindy");
        assertEquals(index, this.hash.findIndex("Tombstone"));

    }

    /**
     * Test findInsertIndex() by:
     * 1. If the first possible index is empty -> choose the index.
     * 2. If the first possible index is occupied -> choose another.
     * 3. If the first possible index is a tombstone -> choose the index.
     */
    public void testFindInsertIndex()
    {

        this.hash = new Hash(10);

        // Case 1
        assertEquals(this.hash.findInsertIndex(this.keys[0]),
            Hash.h(this.keys[0], 10));

        this.hash.insert(this.keys[0], this.data[0]);

        // Case 2
        assertFalse(this.hash.findInsertIndex(this.keys[0]) ==
            Hash.h(this.keys[0], 10));

        this.hash.remove(this.keys[0]);
        // Case 3
        assertEquals(this.hash.findInsertIndex(this.keys[0]),
            Hash.h(this.keys[0], 10));

    }

    /**
     * Test insert() by:
     * 1. Check table's capacity.
     * 2. Check Record existence.
     * 3. Check size.
     * 4. Check capacity.
     */
    public void testInsert()
    {

        // Check already inserted data.
        for (int i = 0; i < this.data.length; i++)
        {
            assertEquals(this.hash.find(this.keys[i]), this.data[i]);
        }
        assertEquals(this.data.length, this.hash.getSize());
        assertEquals(16, this.hash.getCapacity());

        // Test the hash table from size 1 so clear records inside.
        this.hash = new Hash(1);

        // Initial capacity is 1. After the first insertion the capacity should
        // grow to 2.
        assertTrue(this.hash.insert(this.keys[0], this.data[0]));
        assertEquals(2, this.hash.getCapacity());
        assertEquals(this.hash.find(this.keys[0]), this.data[0]);

        // Inserting a duplicate key will return false.
        assertFalse(this.hash.insert(this.keys[0], this.data[1]));
        assertEquals(2, this.hash.getCapacity());

        // If resize, tombstones will be removed.
        this.hash.remove(this.keys[0]);
        this.hash.insert(this.keys[4], this.data[4]);
        this.hash.insert(this.keys[2], this.data[2]);
        assertEquals(2, this.hash.getSize());
        assertEquals(4, this.hash.getCapacity());
        assertTrue(this.hash.noTombstone());

    }

    /**
     * Test remove() by:
     * 1. Remove exist Records.
     * 2. Check size.
     * 3. Check existence.
     */
    public void testRemove()
    {
        for (String key: this.keys)
        {
            assertTrue(this.hash.remove(key));
            assertNull(this.hash.find(key));
        }
        assertEquals(0, this.hash.getSize());
        assertFalse(this.hash.noTombstone());

        // Remove a key doesn't exist.
        assertFalse(this.hash.remove("Night fishing"));

        // Test insertion after remove.
        int index = Hash.h(this.keys[0], this.hash.getCapacity());
        assertEquals(index, this.hash.findInsertIndex(this.keys[0]));
        assertTrue(this.hash.insert(this.keys[0], this.data[0]));
        assertEquals(index, this.hash.findIndex(this.keys[0]));

    }

    /** Test print(). */
    public void testPrint()
    {
        this.hash.print();
        assertEquals(
            "0: |soramimi|\r\n"
            + "3: |Love together|\r\n"
            + "7: |Gift|\r\n"
            + "11: |replica|\r\n"
            + "14: |Light Dance|",
            this.hash.print());
    }


    /**
     * Check out the sfold method
     *
     * @throws Exception
     *             either a IOException or FileNotFoundException
     */
    public void testSfold() throws Exception
    {
        assertTrue(Hash.h("a", 10000) == 97);
        assertTrue(Hash.h("b", 10000) == 98);
        assertTrue(Hash.h("aaaa", 10000) == 1873);
        assertTrue(Hash.h("aaab", 10000) == 9089);
        assertTrue(Hash.h("baaa", 10000) == 1874);
        assertTrue(Hash.h("aaaaaaa", 10000) == 3794);
        assertTrue(Hash.h("Long Lonesome Blues", 10000) == 4635);
        assertTrue(Hash.h("Long   Lonesome Blues", 10000) == 4159);
        assertTrue(Hash.h("long Lonesome Blues", 10000) == 4667);
    }
}
