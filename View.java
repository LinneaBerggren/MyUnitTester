import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Application development in Java, 5DV135-ht178
 * Assignment 1: MyUnitTester
 * Last Edited: 2018-11-08
 * @author Linnea Berggren, id15lbn
 *
 * View is the GUI (window) where the user can interact with the program.
 * The GUI is built up by a JFrame and three JPanels.
 */
public class View {

    private JFrame frame;
    private JButton button;
    private JTextField textField;
    private JButton clearButton;
    public JTextArea textAreaOutput;


    //Should only be called on EDT
    public View(String title) {
        frame = new JFrame(title);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setPreferredSize(new Dimension(600, 400));

        JPanel upperPanel = buildUpperPanel();
        JPanel middlePanel = buildMiddlePanel();
        JPanel lowerPanel = buildLowerPanel();

        frame.add(upperPanel, BorderLayout.NORTH);
        frame.add(middlePanel, BorderLayout.CENTER);
        frame.add(lowerPanel, BorderLayout.SOUTH);

        frame.pack();
    }

    //Should only be called on EDT
    public void show() {
        frame.setVisible(true);
    }

    /**
     * Components of the upper panel
     *      - Title
     *      - Text field for user input
     *      - Run button
     * @return JPanel
     */
    private JPanel buildUpperPanel() {
        JPanel upperPanel = new JPanel();
        upperPanel.setBorder(BorderFactory.createTitledBorder("Write in the name of the test program that you will run"));
        upperPanel.setLayout(new BorderLayout());

        textField = new JTextField("Name of test");
        upperPanel.add(textField, BorderLayout.CENTER);

        button = new JButton("Run");
        button.setHorizontalAlignment(SwingConstants.LEFT);
        //button.addActionListener(ActionListener actionList);

        upperPanel.add(button,BorderLayout.LINE_END);

        return upperPanel;
    }

    public void addActionListener(ActionListener actionList){
        //button.addActionListener(new ButtonListener(textField,fromInputField));
        button.addActionListener(actionList);

    }


    /**
     * Components of middle panel
     *      - Title
     *      - Text area for test result output
     *      - Scrollbar
     * @return JPanel
     */
    private JPanel buildMiddlePanel() {
        JPanel middlePanel = new JPanel();

        middlePanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
        middlePanel.setBorder(BorderFactory.createTitledBorder("Test Result"));
        middlePanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        middlePanel.setLayout(new BorderLayout());

        textAreaOutput = new JTextArea();
        textAreaOutput.setEditable(false);
        JScrollPane scroll = new JScrollPane(textAreaOutput);

        scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        scroll.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        middlePanel.add(scroll, BorderLayout.CENTER);

        return middlePanel;
    }

    /**
     * Components of lower panel
     *      - Button to clear the test result
     *      - Then the button is pressed the test results is reset
     * @return
     */
    private JPanel buildLowerPanel() {
        JPanel lowerPanel = new JPanel();
        clearButton = new JButton("Clear");

        ActionListener clearBtnListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                textAreaOutput.setText("");
            }
        };
        clearButton.addActionListener(clearBtnListener);

        lowerPanel.add(clearButton);

        return lowerPanel;
    }

    /**
     * Take the output string with test result from controller
     * and put it to the JTextArea textAreaOutput in GUI.
     * @param output
     */
    public void PrintOutput(String output){
          textAreaOutput.append(output);
    }

    /**
     * Get the user input from the text field.
     * @return String with input data
     */
    public String getInput(){
       return textField.getText();
    }

}


