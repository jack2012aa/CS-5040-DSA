import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import student.TestCase;

/**
 * Test the main function.
 *
 * @author Chang-Yu Huang
 * @version 10-17-2024
 */
public class ProblemSpecTest extends TestCase {
    /**
     * Sets up the tests that follow. In general, used for initialization
     */
    public void setUp() {
        // Nothing here
    }


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

    // ----------------------------------------------------------


    /**
     * Example 1: This method runs on a command sample IO file
     * You will write similar test cases
     * using different text files
     *
     * @throws IOException
     */
    public void testPostedSample() throws IOException {
        // Setting up all the parameters
        String[] args = new String[2];
        args[0] = "128";
        args[1] = "P2_sampleInput.txt";

        // Invoke main method of our Graph Project
        SemSearch.main(args);

        // Actual output from your System console
        String output = systemOut().getHistory();

        // Expected output from file
        String referenceOutput = readFile(
            "solutionTestData/P2_sampleOutput.txt");

        assertEquals(referenceOutput, output);
    }


    /**
     * Example 2: This method runs on a command sample IO file
     * You will write similar test cases
     * using different text files
     *
     * @throws IOException
     */
    public void testPostedSyntaxSample() throws IOException {
        // Setting up all the parameters
        String[] args = new String[2];
        args[0] = "128";
        args[1] = "P2_syntaxInsertInput.txt";

        // Invoke main method of our Graph Project
        SemSearch.main(args);

        // Actual output from your System console
        String output = systemOut().getHistory();

        // Expected output from file
        String referenceOutput = readFile(
            "solutionTestData/P2_syntaxInsertOutput.txt");

        // Compare the two outputs
        assertEquals(referenceOutput, output);
    }
}
