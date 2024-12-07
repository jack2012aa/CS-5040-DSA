// -------------------------------------------------------------------------

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import student.TestCase;

/**
 * Test {@link CommandProcessor}
 *
 *  @author Chang-Yu Huang
 *  @version 2024-12-03
 */
public class CommandProcessorTest
    extends TestCase
{

    /**
     * Read contents of a file into a string
     *
     * @param path
     *            File name
     * @return the string
     * @throws IOException
     */
    static String readFile(String path) throws IOException {
        byte[] encoded = Files.readAllBytes(Paths.get(path));
        return new String(encoded);
    }

    /**
     * Test command processor.
     * @throws IOException
     */
    public void testCommandProcessor() throws IOException
    {
        DatabaseController controller = new DatabaseController(512, 4);
        CommandProcessor processor = new CommandProcessor(controller,
            "P4Sample_input.txt");
        processor.interpretAllLines();
        assertEquals(readFile("P4Sample_output.txt"),
            systemOut().getHistory());
    }

}
