/**
 *  A data class to be stored in heap when merging runs after replacement
 *  selection.
 *
 *  @author Chang-Yu Huang
 *  @version 2024-10-30
 */
public class RecordRunPair
    implements Comparable<RecordRunPair>
{
    //~ Fields ................................................................
    /** Record. */
    private Record record;
    /** Index of the run in the array. */
    private int runID;

    //~ Constructors ..........................................................
    /**
     * Create an instance.
     * @param record Record.
     * @param runID Index of the run in the array.
     */
    public RecordRunPair(Record record, int runID)
    {
        this.record = record;
        this.runID = runID;
    }
    //~Public  Methods ........................................................

    /**
     * Get the current value of record.
     * @return The value of record for this object.
     */
    public Record getRecord()
    {
        return record;
    }

    // ----------------------------------------------------------
    /**
     * Set the value of record for this object.
     * @param record The new value for record.
     */
    public void setRecord(Record record)
    {
        this.record = record;
    }

    // ----------------------------------------------------------
    /**
     * Get the current value of runID.
     * @return The value of runID for this object.
     */
    public int getRunID()
    {
        return runID;
    }

    // ----------------------------------------------------------
    /**
     * Set the value of runID for this object.
     * @param runID The new value for runID.
     */
    public void setRunID(int runID)
    {
        this.runID = runID;
    }

    // ----------------------------------------------------------
    /**
     * {@inheritDoc}
     */
    @Override
    public int compareTo(RecordRunPair o)
    {
        return this.record.compareTo(o.getRecord());
    }

}
