// -------------------------------------------------------------------------
/**
 * A controller class to control the song database.
 *
 *  @author Chang-Yu Huang
 *  @version 2024-09-05
 */
public class Controller
{
    //~ Fields ................................................................
    /** Hash table saving artist nodes. */
    private Hash artistsTable;
    /** Hash table saving song nodes. */
    private Hash songsTable;
    /** Graph saving every node and edges. */
    private Graph fullGraph;

    /**
     * Type of remove().
     *  @author Chang-Yu Huang
     *  @version 2024-09-04
     */
    public enum RemoveType
    {
        /** Remove an artist. */
        ARTIST,
        /** Remove a song. */
        SONG
    }

    /**
     *  Type of print().
     *  @author Chang-Yu Huang
     *  @version 2024-09-04
     */
    public enum PrintType
    {
        /** Print artists in the hash table. */
        ARTIST,
        /** Print songs in the hash table. */
        SONG,
        /** Print the graph. */
        GRAPH
    }

    //~ Constructors ..........................................................
    /**
     * Create a Controller with appointed capacity.
     * @param capacity Capacity of hash tables and the graph.
     */
    public Controller(int capacity)
    {
        this.artistsTable = new Hash(capacity);
        this.songsTable = new Hash(capacity);
        this.fullGraph = new Graph(capacity);
    }

    //~Public  Methods ........................................................
    /**
     * Insert an artist and his/her song into the database.
     * @param artist Name of the artist.
     * @param song Name of the song.
     * @return Printed string.
     */
    public String insert(String artist, String song)
    {

        String allPrintedString = "";
        String printedString;
        int initialCapacity;

        // Find artist and song in hash tables.
        Node artistNode = this.artistsTable.find(artist);
        Node songNode = this.songsTable.find(song);

        // If the artist/song does not exist, create a new node in the graph to
        // represent it and insert it into the hash table.
        if (artistNode == null)
        {
            artistNode = this.fullGraph.newNode();
        }
        if (songNode == null)
        {
            songNode = this.fullGraph.newNode();
        }

        // Print the result of insertion.
        initialCapacity = this.artistsTable.getCapacity();
        if (this.artistsTable.insert(artist, artistNode))
        {
            if (initialCapacity != this.artistsTable.getCapacity())
            {
                printedString = "Artist hash table size doubled.";
                System.out.println(printedString);
                allPrintedString = allPrintedString + "\n" + printedString;
            }
            printedString = String.format(
                "|%s| is added to the Artist database.", artist);
            System.out.println(printedString);
            allPrintedString = allPrintedString + "\n" + printedString;
        }

        initialCapacity = this.songsTable.getCapacity();
        if (this.songsTable.insert(song, songNode))
        {
            if (initialCapacity != this.songsTable.getCapacity())
            {
                printedString = "Song hash table size doubled.";
                System.out.println(printedString);
                allPrintedString = allPrintedString + "\n" + printedString;
            }
            printedString = String.format(
                "|%s| is added to the Song database.", song);
            System.out.println(printedString);
            allPrintedString  = allPrintedString + "\n" + printedString;
        }

        // Add an edge between artist and song.
        if (!this.fullGraph.addEdge(artistNode, songNode))
        {
            printedString = String.format(
                "|%s<SEP>%s| duplicates a record already in the database.",
                artist, song);
            System.out.println(printedString);
            allPrintedString = allPrintedString + "\n" + printedString;
        }

        if (allPrintedString.length() > 1)
        {
            // Slice the \n in the front.
            return allPrintedString.substring(1);
        }
        return allPrintedString;

    }

    /**
     * Remove an artist in the database.
     * @param artist Name of the artist.
     * @return Printed String.
     */
    String removeArtist(String artist)
    {

        String printedString;

        // Find the artist in the hash table.
        Node artistNode = this.artistsTable.find(artist);

        // If artist doesn't exist, print and return.
        if (artistNode == null)
        {
            printedString = String.format(
                "|%s| does not exist in the Artist database.", artist);
            System.out.println(printedString);
            return printedString;
        }

        // Remove it from the graph.
        this.fullGraph.removeNode(artistNode);
        // Remove it from the hash table.
        this.artistsTable.remove(artist);
        printedString = String.format(
            "|%s| is removed from the Artist database.", artist);
        System.out.println(printedString);
        return printedString;

    }

    /**
     * Remove a song in the database.
     * @param song Name of the song.
     * @return Printed String.
     */
    String removeSong(String song)
    {

        String printedString;

        // Find the song in the hash table.
        Node songNode = this.songsTable.find(song);

        // If the song doesn't exist, print and return.
        if (songNode == null)
        {
            printedString = String.format(
                "|%s| does not exist in the Song database.", song);
            System.out.println(printedString);
            return printedString;
        }

        // Remove it from the graph.
        this.fullGraph.removeNode(songNode);
        // Remove it from the hash table.
        this.songsTable.remove(song);
        printedString = String.format(
            "|%s| is removed from the Song database.", song);
        System.out.println(printedString);
        return printedString;

    }

    /**
     * Remove an artist or a song in the database.
     * @param type Type of the node which you want to remove.
     * @param name Key of the node.
     * @return Printed String.
     */
    public String remove(RemoveType type, String name)
    {
        if (type == RemoveType.ARTIST)
        {
            return this.removeArtist(name);
        }
        return this.removeSong(name);
    }

    /**
     * Print the database by different method.
     * @param type Method to print the database.
     * @return Printed String.
     */
    public String print(PrintType type)
    {
        if (type == PrintType.ARTIST)
        {
            String printedString = this.artistsTable.print();
            System.out.println(String.format(
                "total artists: %d", this.artistsTable.getSize()));
            printedString = printedString + "\n" + String.format(
                "total artists: %d", this.artistsTable.getSize());
            return printedString;
        }
        if (type == PrintType.SONG)
        {
            String printedString = this.songsTable.print();
            System.out.println(String.format(
                "total songs: %d", this.songsTable.getSize()));
            printedString = printedString + "\n" + String.format(
                "total songs: %d", this.songsTable.getSize());
            return printedString;
        }
        return this.fullGraph.print();
    }


}
