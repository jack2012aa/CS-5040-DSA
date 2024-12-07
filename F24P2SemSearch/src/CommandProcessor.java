// -------------------------------------------------------------------------

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * A class parses commands and calls {@link Controller}.
 *
 *  @author Chang-Yu Huang
 *  @version 2024-09-26
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
                String[] keywordsArray = new String[0];
                this.controller.insert(newID, title, date, length, x, y, cost,
                    keywords.toArray(keywordsArray), description);
                break;
            case "delete":
                this.controller.delete(Integer.valueOf(oneLine.next()));
                break;
            case "search":
                // Get search type.
                switch (oneLine.next())
                {
                    case "ID":
                        int id = Integer.valueOf(oneLine.next());
                        this.controller.searchByID(id);
                        break;
                    case "cost":
                        int costLowerBound = Integer.valueOf(oneLine.next());
                        int costUpperBound = Integer.valueOf(oneLine.next());
                        this.controller.searchByCost(costLowerBound,
                            costUpperBound);
                        break;
                    case "date":
                        String dateLowerBound = oneLine.next();
                        String dateUpperBound = oneLine.next();
                        this.controller.searchByDate(dateLowerBound,
                            dateUpperBound);
                        break;
                    case "keyword":
                        String keyword = oneLine.next();
                        this.controller.searchByKeyword(keyword);
                        break;
                    case "location":
                        int originX = Integer.valueOf(oneLine.next());
                        int originY = Integer.valueOf(oneLine.next());
                        int radius = Integer.valueOf(oneLine.next());
                        this.controller.searchByLocation(originX, originY,
                            radius);
                        break;
                    default:
                        throw new IllegalArgumentException("Invalid command "
                            + command);
                }
                break;
            case "print":
                // Get print type.
                switch (oneLine.next())
                {
                    case "ID":
                        this.controller.printID();
                        break;
                    case "date":
                        this.controller.printDate();
                        break;
                    case "cost":
                        this.controller.printCost();
                        break;
                    case "keyword":
                        this.controller.printKeyword();
                        break;
                    case "location":
                        this.controller.printLocation();
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

    /**
     * Do nothing.
     */
    public void end()
    {
        return;
    }

}
