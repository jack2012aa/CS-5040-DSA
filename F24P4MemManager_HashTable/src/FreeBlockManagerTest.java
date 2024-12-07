// -------------------------------------------------------------------------

import student.TestCase;

/**
 * Test {@link FreeBlockManager}
 *
 *  @author Chang-Yu Huang
 *  @version 2024-11-10
 */
public class FreeBlockManagerTest
    extends TestCase
{
    //~ Fields ................................................................
    /** Tested manager. */
    private FreeBlockManager manager;
    /** Test data. */
    private int initialSize = 16;
    private Handle[] initialHandles = {
        new Handle(128, 128),
        new Handle(512, 512),
        new Handle(16, 112),
        new Handle(16, 16),
    };
    private Handle[] moreHandles = {
        new Handle(200, 100),
        new Handle(400, 100),
        new Handle(500, 112),
        new Handle(2048, 16)
    };

    //~Public  Methods ........................................................
    /**
     * Reassign the manager.
     */
    public void setUp()
    {
        this.manager = new FreeBlockManager(initialSize);
        for (Handle hande: this.initialHandles)
        {
            this.manager.free(hande);
        }
    }

    /**
     * Test free() through:
     * 1. Check print structure.
     * 2. Free adjacent handle.
     * 3. Free not adjacent handle.
     * 4. Free handle smaller than the last handle.
     * 5. Free handle larger than the last handle.
     */
    public void testFree()
    {

        // Check print structure.
        this.manager.print();
        String printed = systemOut().getHistory();
        assertEquals("(0,256) -> (512,512)\n", printed);

        // Free adjacent handle.
        this.manager.free(this.moreHandles[0]);
        systemOut().clearHistory();
        this.manager.print();
        printed = systemOut().getHistory();
        assertEquals("(0,300) -> (512,512)\n", printed);

        // Free not adjacent handle.
        this.manager.free(this.moreHandles[1]);
        systemOut().clearHistory();
        this.manager.print();
        printed = systemOut().getHistory();
        assertEquals("(0,300) -> (400,100) -> (512,512)\n", printed);

        // Free handle smaller than the last handle.
        this.manager.free(this.moreHandles[2]);
        systemOut().clearHistory();
        this.manager.print();
        printed = systemOut().getHistory();
        assertEquals("(0,300) -> (400,624)\n", printed);

        // Free handle larger than the last handle.
        this.manager.free(this.moreHandles[3]);
        systemOut().clearHistory();
        this.manager.print();
        printed = systemOut().getHistory();
        assertEquals("(0,300) -> (400,624) -> (2048,16)\n", printed);

    }

    /**
     * Test hasSpace() through:
     * 1. Find a small space.
     * 2. Find a large space.
     */
    public void testHasSpace()
    {

        // Find a small space.
        assertTrue(this.manager.hasSpace(512));

        // Find a large space.
        assertTrue(!this.manager.hasSpace(1024));

    }

    /**
     * Test allocate() through:
     * 1. Allocate a small space.
     * 2. Allocate a medium space (= a block).
     * 3. Allocate a large space.
     * 4. Use up every space.
     */
    public void testAllocate()
    {

        // Allocate a small space.
        Handle result = this.manager.allocate(130);
        assertEquals(0, result.getStart());
        assertEquals(130, result.getSize());
        this.manager.print();
        String printed = systemOut().getHistory();
        assertEquals("(130,126) -> (512,512)\n", printed);

        // Allocate a medium space (= a block).
        result = this.manager.allocate(512);
        assertEquals(512, result.getStart());
        assertEquals(512, result.getSize());
        systemOut().clearHistory();
        this.manager.print();
        printed = systemOut().getHistory();
        assertEquals("(130,126)\n", printed);

        // Allocate a large space.
        result = this.manager.allocate(512);
        assertNull(result);

        // Use up every space.
        result = this.manager.allocate(126);
        assertEquals(130, result.getStart());
        assertEquals(126, result.getSize());
        systemOut().clearHistory();
        this.manager.print();
        printed = systemOut().getHistory();
        assertEquals("There are no freeblocks in the memory pool.\n", printed);
        assertNull(this.manager.allocate(1));
        assertTrue(!this.manager.hasSpace(1));
        this.manager.free(result);
        systemOut().clearHistory();
        this.manager.print();
        printed = systemOut().getHistory();
        assertEquals("(130,126)\n", printed);


    }


}
