// -------------------------------------------------------------------------
/**
 * A class to save serialized variable size data.
 *
 *  @author Chang-Yu Huang
 *  @version 2024-11-11
 */
public class MemoryManager
{
    //~ Fields ................................................................
    /** A memory pool. */
    private byte[] memoryPool;
    /** A doubly linked list managing free blocks. */
    private FreeBlockManager freeBlock;
    /** Extend size. */
    private int extendSize;

    //~ Constructors ..........................................................
    /**
     * Create a memory manager with initial size in byte.
     * @param size Initial size in byte.
     */
    public MemoryManager(int size)
    {
        this.memoryPool = new byte[size];
        this.freeBlock = new FreeBlockManager(size);
        this.extendSize = size;
    }

    //~Public  Methods ........................................................
    /**
     * Insert a block of space into the memory pool.
     * @param space Block to be inserted.
     * @return A handle of the block.
     */
    public Handle insert(byte[] space)
    {

        // Extend until has enough space.
        while (!this.freeBlock.hasSpace(space.length))
        {
            byte[] exted = new byte[this.memoryPool.length + this.extendSize];
            System.arraycopy(this.memoryPool, 0, exted, 0,
                this.memoryPool.length);
            this.freeBlock.free(new Handle(this.memoryPool.length,
                this.extendSize));
            this.memoryPool = exted;
            System.out.println(String.format("Memory pool expanded to %d "
                + "bytes", this.memoryPool.length));
        }

        // Copy the block to the pool.
        Handle handle = this.freeBlock.allocate(space.length);
        System.arraycopy(space, 0, this.memoryPool, handle.getStart(),
            handle.getSize());
        return handle;

    }

    /**
     * Remove a block from the memory pool.
     * @param handle A handle of the block.
     */
    public void remove(Handle handle)
    {
        // Free the space but don't waste time on erase it from the pool.
        this.freeBlock.free(handle);
    }

    /**
     * Get the content in handle and copy it into space.
     * @param space A byte array to get the content.
     * @param handle A handle of the block.
     * @return How many bytes are copied.
     */
    public int get(byte[] space, Handle handle)
    {
        System.arraycopy(memoryPool, handle.getStart(), space, 0,
            handle.getSize());
        return handle.getSize();
    }

    /**
     * Print free blocks.
     */
    public void print()
    {
        this.freeBlock.print();
    }

}
