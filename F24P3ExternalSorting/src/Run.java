// -------------------------------------------------------------------------
/**
 * A class representing a run of replacement selection or multi-way merge.
 *
 *  @author Chang-Yu Huang
 *  @version 2024-10-30
 */
public class Run
{
    //~ Fields ................................................................
    /** The file it's stored in. */
    private ByteFileRD source;
    /** How many records are in this run. */
    private int noR;
    /** How many records in this run are read. */
    private int used;

    //~ Constructors ..........................................................
    /**
     * Create a run which start at position and has noR records.
     * @param noR Number of records in the run.
     * @param source The file the run is stored in.
     */
    public Run(int noR, ByteFileRD source)
    {
        this.noR = noR;
        this.used = 0;
        this.source = source;
    }

    //~Public  Methods ........................................................
    /**
     * Has further record in the run to be read.
     * @return Is there any remain record.
     */
    public boolean hasNext()
    {
        return this.used < this.noR;
    }

    /**
     * Return the next record in the run if available.
     * @return The next record in the run.
     */
    public Record getNext()
    {
        if (!this.hasNext())
        {
            return null;
        }
        this.used++;
        return this.source.read();
    }

    /**
     * Return the number of records in this run.
     * @return Number of records in this run.
     */
    public int getNumberOfRecord()
    {
        return this.noR;
    }

    /**
     * Close the file.
     */
    public void close()
    {
        this.source.close();
    }

}
