// -------------------------------------------------------------------------
/**
 *  A class to control an external sort.
 *
 *  @author Chang-Yu Huang
 *  @version 2024-10-30
 */
public class SortController
{
    //~ Fields ................................................................
    /** Input file. */
    private ByteFileRD inputFile;
    /** Run file. */
    private ByteFileRD runFile;
    /** A linked list storing records used for initializing a heap. */
    private LinkedList<Record> heapBuffer;
    /** Runs information of replacementSelection. */
    private LinkedList<Run> runs;

    //~ Constructors ..........................................................
    /**
     * Create a sort controller to sort inputFile and use runFile as temporary
     * storage.
     * @param inputFile Path to the input file.
     * @param runFile Path to the runFile.
     */
    public SortController(String inputFile, String runFile)
    {
        this.inputFile = new ByteFileRD(inputFile, 0);
        this.runFile = new ByteFileRD(runFile, 0);
    }

    /**
     * Conduct a single run of selection.
     */
    private Run selectionHelper()
    {
        // Read heap buffer if necessary.
        if (this.heapBuffer.getSize() == 0)
        {
            for (int i = 0; i < 8 * ByteFile.RECORDS_PER_BLOCK
                && this.inputFile.hasNext(); i++)
            {
                this.heapBuffer.insertTail(this.inputFile.read());
            }
        }
        Record[] heapArr = new Record[this.heapBuffer.getSize()];
        for (int i = 0; i < heapArr.length; i++)
        {
            heapArr[i] = this.heapBuffer.popFront();
        }
        MinHeap<Record> heap = new MinHeap<Record>(
            heapArr, heapArr.length, heapArr.length);
        this.heapBuffer = new LinkedList<Record>();

        // Store the start position and number of records in this run.
        long start = this.runFile.getPosition();
        int noR = 0;

        // Repeat while there is something in the heap and we have something to
        // read in the input file.
        while (heap.heapSize() != 0 && inputFile.hasNext())
        {

            // Move the top of the heap.
            Record top = heap.removeMin();
            this.runFile.write(top);
            // Get the front of the input buffer.
            Record front = this.inputFile.read();
            // Put the front of the input buffer to a proper place.
            if (front.getKey() >= top.getKey())
            {
                heap.insert(front);
            }
            else
            {
                this.heapBuffer.insertTail(front);
            }
            noR++;
        }

        // Put remain records in the heap to the run file.
        while (heap.heapSize() != 0)
        {
            runFile.write(heap.removeMin());
            noR++;
        }
        // Flush run file.
        runFile.flush();
        // Return run information
        return new Run(noR,
            new ByteFileRD(this.runFile.getPath(), start));
    }

    /**
     * Conduct a single run of merge which at most merge 8 runs.
     * @param output Output file.
     * @return Run information.
     */
    private Run mergeHelper(ByteFileRD outputFile)
    {

        // Calculate how many blocks are in this run.
        int runSize = Math.min(8, this.runs.getSize());

        // Create an array to quickly get the remain element in each run.
        Run[] runsArr = new Run[runSize];

        // Create a heap of RecordRunPair.
        RecordRunPair[] heapStarter = new RecordRunPair[runSize];
        for (int i = 0; i < runSize; i++)
        {
            runsArr[i] = this.runs.popFront();
            heapStarter[i] = new RecordRunPair(runsArr[i].getNext(), i);
        }
        MinHeap<RecordRunPair> heap = new MinHeap<RecordRunPair>(
            heapStarter, runSize, runSize);

        // Store run information.
        long start = outputFile.getPosition();
        int noR = 0;

        // While there is something in the heap:
        while (heap.heapSize() != 0)
        {
            // Put the top record to the output.
            RecordRunPair current = heap.removeMin();
            outputFile.write(current.getRecord());
            // Put a new RecordRunPair back to heap if available.
            if (runsArr[current.getRunID()].hasNext())
            {
                heap.insert(new RecordRunPair(
                    runsArr[current.getRunID()].getNext(),
                    current.getRunID()));
            }
            noR++;
        }

        for (Run run: runsArr)
        {
            run.close();
        }
        outputFile.flush();
        // Return run information.
        return new Run(noR, new ByteFileRD(outputFile.getPath(), start));
    }

    //~Public  Methods ........................................................
    /**
     * Sort the input file by runs. Result will be stored in the run file.
     * I separate the replacement selection and merge step because it is easier
     * to debug.
     */
    public void replacementSelection()
    {
        // Reset input and run file.
        this.runFile.clear();
        this.runFile.reset();
        this.inputFile.reset();

        // Start selection and store run information.
        this.heapBuffer = new LinkedList<Record>();
        this.runs = new LinkedList<Run>();
        while (this.inputFile.hasNext() || this.heapBuffer.getSize() != 0)
        {
            runs.insertTail(this.selectionHelper());
        }
        // Free buffer.
        this.heapBuffer = null;
    }

    /**
     * Merge runs of replacement selection. Result will be stored in the input
     * file.
     */
    public void merge()
    {
        ByteFileRD outputFile = this.inputFile;

        do
        {
            outputFile.clear();
            outputFile.reset();
            LinkedList<Run> mergedRuns = new LinkedList<Run>();
            while (this.runs.getSize() > 0)
            {
                mergedRuns.insertTail(this.mergeHelper(outputFile));
            }
            this.runs = mergedRuns;
            outputFile = (outputFile == this.inputFile)
                ? this.runFile : this.inputFile;
        } while (this.runs.getSize() > 1);

        if (outputFile == this.inputFile)
        {
            runFile.reset();
            this.inputFile.clear();
            this.inputFile.reset();
            while (runFile.hasNext())
            {
                this.inputFile.write(runFile.read());
            }
        }
    }

    /**
     * Close files.
     */
    public void close()
    {
        this.inputFile.close();
        this.runFile.close();
    }

    /**
     * Print input file.
     */
    public void print()
    {
        this.inputFile.print();
    }

}
