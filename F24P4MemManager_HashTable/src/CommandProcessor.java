// -------------------------------------------------------------------------

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * A class parsing commands.
 *
 *  @author Chang-Yu Huang
 *  @version 2024-11-12
 */
public class CommandProcessor
{

    /** A controller controls the database. */
    private DatabaseController controller;
    /** A file scanner. */
    private Scanner scanner;

    /**
     * Do the test.
     * @param control A initialized controller.
     * @param file Path to the input file.
     */
    public CommandProcessor(DatabaseController control, String file)
    {
        try
        {
            this.controller = control;
            this.scanner = new Scanner(new File(file));
        }
        catch (FileNotFoundException e)
        {
            e.printStackTrace();
        }

    }

    // ----------------------------------------------------------
    /**
     * Read lines in the source file and conduct commands.
     */
    public void interpretAllLines()
    {
        while (this.scanner.hasNextLine())
        {
            this.interpretLine(new Scanner(this.scanner.nextLine()),
                this.scanner);
        }
    }


    // ----------------------------------------------------------
    /**
     * Read a line as a command and conduct it.
     * @param oneLine The command.
     * @param remainingInputLines Remaining lines in the input file. Unused in
     * this method.
     */
    public void interpretLine(
        Scanner oneLine,
        Scanner remainingInputLines)
    {
        if (!oneLine.hasNext())
        {
            return;
        }
        oneLine.useDelimiter("\\s+");

        // First line is the command.
        String command = oneLine.next();
        switch (command)
        {
            case "insert":
                int newID = Integer.valueOf(oneLine.next());
                String title = remainingInputLines.nextLine().trim();
                Scanner infos = new Scanner(remainingInputLines.nextLine());
                infos.useDelimiter("\\s+");
                String date = infos.next();
                int length = Integer.valueOf(infos.next());
                short x = Short.valueOf(infos.next());
                short y = Short.valueOf(infos.next());
                int cost = Integer.valueOf(infos.next());
                Scanner keywordScanner = new Scanner(
                    remainingInputLines.nextLine());
                keywordScanner.useDelimiter("\\s+");
                ArrayList<String> keywords = new ArrayList<String>();
                while (keywordScanner.hasNext())
                {
                    keywords.add(keywordScanner.next());
                }
                String description = remainingInputLines.nextLine().trim();
                description = description.replaceAll("\\s+", " ");
                String[] keywordsArray = new String[0];
                this.controller.insert(newID, new Seminar(newID, title, date,
                    length, x, y, cost, keywords.toArray(keywordsArray),
                    description));
                break;
            case "delete":
                this.controller.delete(Integer.valueOf(oneLine.next()));
                break;
            case "search":
                int id = Integer.valueOf(oneLine.next());
                this.controller.search(id);
                break;
            case "print":
                // Get print type.
                switch (oneLine.next())
                {
                    case "hashtable":
                        this.controller.printHashTable();
                        break;
                    case "blocks":
                        this.controller.printMemoryManager();
                        break;
                    default:
                        throw new IllegalArgumentException("Invalid command "
                            + command);
                }
                break;
            default:
                throw new IllegalArgumentException("Invalid command "
                    + command);
        }
    }

}
