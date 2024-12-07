// -------------------------------------------------------------------------

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;

/**
 * A class parses commands and calls {@link Controller}.
 *
 *  @author Chang-Yu Huang
 *  @version 2024-09-16
 */
public class CommandProcessor
    implements Interpreter
{

    /** A controller controls the database. */
    private Controller controller;

    /**
     * Do the test.
     * @param control A initialized controller.
     * @param file Path to the input file.
     */
    public CommandProcessor(Controller control, String file)
    {
        try
        {
            this.controller = control;
            Scanner scanner = new Scanner(new File(file));
            PrintWriter writer = new PrintWriter(System.out, true);
            this.interpretAllLines(scanner, writer);
        }
        catch (FileNotFoundException e)
        {
            e.printStackTrace();
        }

    }

    // ----------------------------------------------------------
    /**
     * Read lines in the source file and conduct commands.
     * @param input Scanner of the input file.
     * @param output Method to output results. Unused in this method.
     */
    public void interpretAllLines(Scanner input, PrintWriter output)
    {
        while (input.hasNextLine())
        {
            this.interpretLine(new Scanner(input.nextLine()), output, input);
        }
    }


    // ----------------------------------------------------------
    /**
     * Read a line as a command and conduct it.
     * @param oneLine The command.
     * @param output Method to output results. Unused in this method.
     * @param remainingInputLines Remaining lines in the input file. Unused in
     * this method.
     */
    public void interpretLine(
        Scanner oneLine,
        PrintWriter output,
        Scanner remainingInputLines)
    {
        oneLine.useDelimiter(" ");
        // First line is the command.
        String command = oneLine.next();
        switch (command)
        {
            case "insert":
                // Separate artist and song name.
                oneLine.useDelimiter("<SEP>");
                // Remove the leading space.
                String artist = oneLine.next().substring(1);
                String song = oneLine.next();
                this.controller.insert(artist, song.trim());
                break;
            case "remove":
                // Get remove type.
                String removeTypeString = oneLine.next();
                Controller.RemoveType removeType;
                if (removeTypeString.equals("artist"))
                {
                    removeType = Controller.RemoveType.ARTIST;
                }
                else {
                    removeType = Controller.RemoveType.SONG;
                }
                // Remove the leading space.
                this.controller.remove(removeType,
                    oneLine.nextLine().substring(1));
                break;
            case "print":
                // Get print type.
                String printTypeString = oneLine.next();
                Controller.PrintType printType;
                if (printTypeString.equals("artist"))
                {
                    printType = Controller.PrintType.ARTIST;
                }
                else if (printTypeString.equals("song"))
                {
                    printType = Controller.PrintType.SONG;
                }
                else
                {
                    printType = Controller.PrintType.GRAPH;
                }
                this.controller.print(printType);
                break;
            default:
                throw new IllegalArgumentException("Invalid command "
            + command);
        }
    }

    /**
     * Do nothing.
     */
    public void end()
    {
        return;
    }

}
