// -------------------------------------------------------------------------

import student.TestCase;

/**
 * Test of {@link CommandProcessor}.
 *
 *  @author Chang-Yu Huang
 *  @version 2024-09-26
 */
public class CommandProcessorTest
    extends TestCase
{
    //~ Fields ................................................................
    private Controller controller;
    private CommandProcessor commandProcessor;

    //~Public  Methods ........................................................
    /**
     * Create the controller.
     */
    public void setUp()
    {
        this.controller = new Controller(127);
    }

    /**
     * A general test.
     */
    public void testGeneral()
    {
        commandProcessor = new CommandProcessor(this.controller,
            "solutionTestData/test1.txt");
        this.commandProcessor.end();
    }

}
