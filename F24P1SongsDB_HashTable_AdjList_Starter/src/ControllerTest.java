// -------------------------------------------------------------------------

import student.TestCase;

/**
 * Test {@link Controller}
 *
 *  @author Chang-Yu Huang
 *  @version 2024-09-05
 */
public class ControllerTest
    extends TestCase
{
    //~ Fields ................................................................
    /** Tested Controller. */
    private Controller controller;
    /** Name of artists. */
    private String[] artists = {
        "Vaundy",
        "Sakanaction",
        "Nona Reeves"};
    /** Name of songs. */
    private String[] songs = {
        "replica",
        "Light dance",
        "Love together"
    };
    private int initialCapacity = 10;

    //~Public  Methods ........................................................
    /**
     * Insert some artists and songs.
     */
    public void setUp()
    {
        this.controller = new Controller(this.initialCapacity);
        for (int i = 0; i < this.artists.length; i++)
        {
            assertEquals(
                String.format(
                    "|%s| is added to the Artist database.\n"
                    + "|%s| is added to the Song database.",
                    this.artists[i], this.songs[i]),
                this.controller.insert(this.artists[i], this.songs[i])
            );
        }
    }

    /**
     * Test insert() through:
     * 1. Check inserted data.
     * 2. Insert duplicate data.
     * 3. Insert new edge.
     * 4. Insert until capacity changes.
     */
    public void testInsert()
    {

        // Check inserted data.
        assertEquals(
            "There are 3 connected components\n"
            + "The largest connected component has 2 elements",
            this.controller.print(Controller.PrintType.GRAPH));
        assertEquals(
            "2: |Sakanaction|\n"
            + "6: |Vaundy|\n"
            + "7: |Nona Reeves|\n"
            + "total artists: 3",
            this.controller.print(Controller.PrintType.ARTIST));
        assertEquals("1: |replica|\n"
            + "8: |Light dance|\n"
            + "9: |Love together|\n"
            + "total songs: 3",
            this.controller.print(Controller.PrintType.SONG));

        // Insert duplicate data.
        assertEquals(
            String.format(
                "|%s<SEP>%s| duplicates a record already in the database."
                , this.artists[0], this.songs[0]),
            this.controller.insert(this.artists[0], this.songs[0])
        );

        // Insert new edge.
        assertEquals("",
            this.controller.insert(this.artists[0], this.songs[1]));

        // Insert until capacity changes.
        // Current capacity: 10, current size: 3
        this.controller.insert("King gnu", "Hakujitsu");
        this.controller.insert("amiyon", "Harunohi");
        assertEquals(
            "Artist hash table size doubled.\n" +
            "|YOASOBI| is added to the Artist database.\n" +
            "Song hash table size doubled.\n" +
            "|Idol| is added to the Song database.",
            this.controller.insert("YOASOBI", "Idol"));

    }

    /**
     * Test removeArtist through:
     * 1. Remove an artist who doesn't exist in the database.
     * 2. Remove a highly connected artist and check the graph.
     */
    public void testRemoveArtist()
    {

        // Remove an artist who doesn't exist in the database.
        assertEquals(
            "|Fuji Kaze| does not exist in the Artist database.",
            this.controller.removeArtist("Fuji Kaze"));

        // Remove a highly connected artist.
        // Add more edges.
        this.controller.insert("Nona Reeves", "Yasumo, ONCE MORE");
        this.controller.insert("Nona Reeves", "DJ! DJ!");
        String songTable = this.controller.print(Controller.PrintType.SONG);
        assertEquals(
            "|Nona Reeves| is removed from the Artist database.",
            this.controller.removeArtist("Nona Reeves"));
        // Song table should not change.
        assertEquals(
            songTable,
            this.controller.print(Controller.PrintType.SONG));
        // Graph: Vaundy <-> replica, Sakanaction <-> Light dance
        // Love together, Yasumo ONCE MORE, DJ! DJ!
        assertEquals(
            "There are 5 connected components\n"
            + "The largest connected component has 2 elements",
            this.controller.print(Controller.PrintType.GRAPH)
        );

    }

    /**
     * Test removeSong through:
     * 1. Remove a song who doesn't exist in the database.
     * 2. Remove a highly connected song and check the graph.
     */
    public void testRemoveSong()
    {

        // Remove a song who doesn't exist in the database.
        assertEquals(
            "|ENJOYEE!| does not exist in the Song database.",
            this.controller.removeSong("ENJOYEE!"));

        // Remove a highly connected song.
        // Add more edges.
        this.controller.insert("Sakanaction", "Love together");
        this.controller.insert("Vaundy", "Love together");
        String artistTable = this.controller.print(Controller.PrintType.ARTIST);
        assertEquals(
            "|Love together| is removed from the Song database.",
            this.controller.removeSong("Love together"));
        // Artist table should not change.
        assertEquals(
            artistTable,
            this.controller.print(Controller.PrintType.ARTIST));
        // Graph: Vaundy <-> replica, Sakanaction <-> Light dance
        // Nona Reeves
        assertEquals(
            "There are 3 connected components\n"
            + "The largest connected component has 2 elements",
            this.controller.print(Controller.PrintType.GRAPH)
        );

    }

    /**
     * Test remove() by make sure it calls removeArtist or removeSong.
     */
    public void testRemove()
    {
        assertEquals(
            "|Love together| is removed from the Song database.",
            this.controller.remove(Controller.RemoveType.SONG, "Love together")
        );
        assertEquals(
            "|Nona Reeves| is removed from the Artist database.",
            this.controller.remove(Controller.RemoveType.ARTIST, "Nona Reeves")
        );
    }

    // print() is tested in previous tests.

}
