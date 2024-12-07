import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import student.TestCase;

/**
 * @author Chang-Yu Huang
 * @version 2024-12-03
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

    /**
     * Full parser test
     *
     * @throws IOException
     */
    public void testparserfull() throws IOException {
        String[] args = new String[3];
        args[0] = "512";
        args[1] = "4";
        args[2] = "P4Sample_input.txt";

        SemManager.main(args);
        String output = systemOut().getHistory();
        String referenceOutput = readFile("P4Sample_output.txt");
        assertEquals(referenceOutput, output);
    }

}
