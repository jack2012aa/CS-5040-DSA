
/**
 * An application to store seminars and can search them using id, keywords,
 * location, and date. The application uses binary search trees and a bintree
 * to store the searching structure.
 */

/**
 * The class containing the main method.
 *
 * @author Chang-Yu Huang
 * @version 10-17-2024
 */

// On my honor:
// - I have not used source code obtained from another current or
//   former student, or any other unauthorized source, either
//   modified or unmodified.
//
// - All source code and documentation used in my program is
//   either my original work, or was derived by me from the
//   source code published in the textbook for this course.
//
// - I have not discussed coding details about this project with
//   anyone other than my partner (in the case of a joint
//   submission), instructor, ACM/UPE tutors or the TAs assigned
//   to this course. I understand that I may discuss the concepts
//   of this program with other students, and that another student
//   may help me debug my program so long as neither of us writes
//   anything during the discussion or modifies any computer file
//   during the discussion. I have violated neither the spirit nor
//   letter of this restriction.


public class SemSearch {
    /**
     * @param args
     *     Command line parameters
     */
    public static void main(String[] args) {
        // This is the main file for the program.
        Controller controller = new Controller(Integer.valueOf(args[0]));
        CommandProcessor processor = new CommandProcessor(controller, args[1]);
        processor.end();
    }
}
