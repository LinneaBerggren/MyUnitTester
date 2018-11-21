import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Application development in Java, 5DV135-ht178
 * Assignment 1: MyUnitTester
 * Last Edited: 2018-11-08
 * @author Linnea Berggren, id15lbn
 *
 * Controller handles the communication between the view and the model.
 * When the run button in the gui is pressed the input (class name) is called from the view,
 * the testclass will be tested and the test result is presesented in the gui.
 *
 */
public class Controller {

    private View view;
    private ClassTester model;
    private String input;

    /**
     * Constructor
     */
    public Controller(View view, ClassTester model){

        this.view = view;
        this.model = model;
        ActionListener actionList = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                input = view.getInput();

                model.validationTest(input);

                view.PrintOutput(model.getTextOutput());
            }
        };
        view.addActionListener(actionList);

    }

    /**
     * Get the user input from the view
     * @param input from textfield
     * @return String

    public String getInput(String input){
        return input;
    }
     */

    /**
     * Get the test result from model
     * @return String

    public String setTestOutput(){
        return model.getTextOutput();
    }
     */



}
