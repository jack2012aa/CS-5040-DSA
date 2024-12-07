import student.TestCase;

/**
 * Test the main function.
 * @author Chang-Yu Huang
 * @version 10-17-2024
 */
public class SemSearchTest extends TestCase {
    /**
     * Sets up the tests that follow. In general, used for initialization
     */
    public void setUp() {
        // Nothing here
    }


    /**
     * Get code coverage of the class declaration.
     */
    public void testMInitx()
    {
        SemSearch sem = new SemSearch();
        assertNotNull(sem);
        String[] args = {"100", "solutionTestData/P2_sampleInput.txt"};
        SemSearch.main(args);
    }
}

