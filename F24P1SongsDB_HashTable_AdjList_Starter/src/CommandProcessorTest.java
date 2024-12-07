// -------------------------------------------------------------------------

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import student.TestCase;

/**
 * Tests of {@link CommandProcessor}.
 *
 *  @author Chang-Yu Huang
 *  @version 2024-09-16
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
     * Test interpretAllLines.
     * @throws Exception
     */
    public void testInterpretAllLines() throws Exception
    {
        CommandProcessor processor = new CommandProcessor(
            new Controller(1),
            "solutionTestData/myTestInput.txt"
        );
        // Actual output from your System console
        String actualOutput = systemOut().getHistory();

        // Expected output from file
        String expectedOutput = readFile(
            "solutionTestData/myTestOutput.txt");

        assertEquals(expectedOutput, actualOutput);
        processor.end();

    }

}
