import javax.swing.*;

/**
 * Application development in Java, 5DV135-ht178
 * Assignment 1: MyUnitTester
 * Last Edited: 2018-11-08
 * @author Linnea Berggren, id15lbn
 *
 * Main method that runs the program.
 * Design pattern MVC is implemented
 *      - Model: contains the logic of testing a test class
 *      - View: the gui that takes input and present output to a user
 *      - Controller: handles the communication between the view and the model
 */

public class MyUnitTester {

    public static void main( String[] args ) {

        SwingUtilities.invokeLater(() -> {
            View gui = new View("My Unit Tester");
            gui.show();

            ClassTester model = new ClassTester();

            new Controller(gui, model);
        });

    }
}

