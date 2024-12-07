// -------------------------------------------------------------------------

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * Read or write records from/into a binary file in a sequential way.
 *
 *  @author Chang-Yu Huang
 *  @version 2024-10-30
 */
public class ByteFileRD
{
    //~ Fields ................................................................
    /** Target file. */
    private RandomAccessFile file;
    /** Input buffer. */
    private ByteBuffer inputBuffer =
        ByteBuffer.allocate(ByteFile.BYTES_PER_BLOCK);
    /** Output buffer. */
    private ByteBuffer outputBuffer =
        ByteBuffer.allocate(ByteFile.BYTES_PER_BLOCK);
    /** File path. */
    private String path;
    /** File channel. */
    private FileChannel channel;

    //~ Constructors ..........................................................
    /**
     * Create a random access file start at position.
     * @param path Path to the file.
     * @param position Start position.
     */
    public ByteFileRD(String path, long position)
    {
        try
        {
            this.file = new RandomAccessFile(path, "rw");
            this.file.seek(position);
            this.path = path;
            this.channel = this.file.getChannel();
            this.inputBuffer.flip();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    //~Public  Methods ........................................................
    /**
     * Read a record.
     * @return A record.
     */
    public Record read()
    {
        if (!this.inputBuffer.hasRemaining())
        {
            try
            {
                this.inputBuffer.clear();
                int read = this.channel.read(this.inputBuffer);
                if (read == -1)
                {
                    return null;
                }
                this.inputBuffer.flip();
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }
        return new Record(this.inputBuffer.getLong(),
            this.inputBuffer.getDouble());
    }

    /**
     * Write a record to the current block. It may not be flushed.
     * @param record Record to be written.
     */
    public void write(Record record)
    {
        this.outputBuffer.putLong(record.getID());
        this.outputBuffer.putDouble(record.getKey());
        if (this.outputBuffer.remaining() < Record.BYTES)
        {
            this.flush();
        }
    }

    /**
     * Flush the buffer into the file.
     */
    public void flush()
    {
        try
        {
            this.outputBuffer.flip();
            this.channel.write(this.outputBuffer);
            this.outputBuffer.clear();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    /**
     * Clear the content in the file.
     */
    public void clear()
    {
        try
        {
            this.file.setLength(0);
            this.outputBuffer = ByteBuffer.allocate(ByteFile.BYTES_PER_BLOCK);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    /**
     * Reset the pointer to the beginning of the file.
     */
    public void reset()
    {
        try
        {
            this.file.seek(0);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    /**
     * Close the file.
     */
    public void close()
    {
        try
        {
            this.file.close();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    /**
     * Get the path of the file.
     * @return The path of the file.
     */
    public String getPath()
    {
        return this.path;
    }

    /**
     * Check remaining in the file.
     * @return Return true if there is remaining data.
     */
    public boolean hasNext()
    {
        try
        {
            return (this.file.getFilePointer() < this.file.length())
                || this.inputBuffer.hasRemaining();
        }
        catch (IOException e)
        {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Get the current position in the file.
     * @return Current position in the file.
     */
    public long getPosition()
    {
        try
        {
            return this.file.getFilePointer();
        }
        catch (IOException e)
        {
            e.printStackTrace();
            return 0;
        }
    }

    /**
     * Print the first element of each block and reset the pointer.
     */
    public void print()
    {
        this.reset();
        try
        {
            int counter = 0;
            while (this.file.getFilePointer() < this.file.length()) {
                if (counter != 0 && counter % 5 == 0) {
                    System.out.println();
                }
                System.out.print(this.file.readLong() + " " +
                    this.file.readDouble() + " ");
                counter++;
                this.file.seek(this.file.getFilePointer() +
                    ByteFile.BYTES_PER_BLOCK - Record.BYTES);
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        this.reset();
    }
}
