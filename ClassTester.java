import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Application development in Java, 5DV135-ht178
 * Assignment 1: MyUnitTester
 * Last Edited: 2018-11-08
 * @author Linnea Berggren, id15lbn
 *
 * Classtester is the model in this program.
 * It contains all the logic that test the test class.
 *      - Checks if the class implements the TestClass
 *      - Checks if the class constructor has no parameter
 *      - Checks if the class is a interface
 *      - Do a setUp and tearDown if the methods exists
 * The result is saved in a string variable and can be
 * called from the controller.
 */
public class ClassTester {

    private String className;
    private Class<?> testClass;
    private String textOutput;
    private Method[] methods;
    private int setUp;
    private int tearDown;
    private int successCount;
    private int failCount;
    private int exceptionFailCount;
    private Boolean validated;

    /**
     * Constructor
     */
    public ClassTester(){
        validated = false;
        this.textOutput = "";
    }

    /**
     * Check if a class is a valid testclass
     * @param className
     * @return True if test class is valid, else false
     */
    public Boolean validationTest(String className){

        this.className = className;
        try {
            testClass = Class.forName(className);
            if ( !isTestClassInterface() && hasParameters() && implementsTestClass()){
                methods = this.testClass.getMethods();
                validated = true;
                runTestMethods();
                return true;
            }

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            textOutput += ("Could not find class: " + className + "\n");
        }
        return false;
    }


    /**
     * Checks if the class is an interface
     * @return True if it is, false if not.
     */
    private Boolean isTestClassInterface(){

        try {
            testClass = Class.forName(className);
            if (testClass.isInterface()){
                textOutput += ("Class is an Interface and can not be instanced\n");
                return true;
            }
            return false;

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            textOutput += ("Could not find class: " + className + "\n");
        }
        return false;
    }


    /**
     * Checks if the given class have constructor
     * that does not take any parameters.
     * @return True if it has a nullary costructor.
     * If the class constructor takes in parameters
     * or if class is not found return false.
     */
    private Boolean hasParameters(){

        try {
            testClass = Class.forName(className);
            Constructor<?>[] constructors = testClass.getConstructors();
            boolean valid = false;

            for (Constructor con: constructors){
                if (con.isVarArgs() == false){
                    valid = true;
                }
            }
            if (!valid) {
                textOutput += ("Could not find a constructor without arguments\n");
            }
            return valid;

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            textOutput += ("Could not find class: " + className + "\n");
        }
        return false;
    }

    /**
     * Checks if the class implements the interface TestClass.
     * @return True if it implements TestClass.
     * False if it's not.
     */
    private boolean implementsTestClass() {
        try {
            testClass = Class.forName(className);
        } catch (ClassNotFoundException e) {
            textOutput += ("Could not find class: " + className + "\n");
            e.printStackTrace();
        }
        Class<?>[] interfaces = testClass.getInterfaces();

        for (Class<?> anInterface : interfaces) {
            if (anInterface.getName() == "se.umu.cs.unittest.TestClass") {
                return true;
            }
        }
        textOutput += ("Class does not implement the interface TestClass.\n");
        return false;
    }


    /**
     * Checks if the testclass contains a valid setUp method
     * @return true if setUp exists and is valid, else false
     */
    private Boolean hasSetUp(){
        for (int i = 0; i < methods.length; i++){
            if((methods[i].getName().compareTo("setUp") == 0) &&
                    (methods[i].getParameterCount() == 0)){
                setUp = i;
                return true;
            }
        }
        return false;
    }

    /**
     * Checks if the test class contains a tearDown method
     * If tearDown not exists it should not be seen as an exception
     * @return true if tearDown exists, else false
     */
    private Boolean hasTearDown(){
        for (int i = 0; i < methods.length; i++){
            if((methods[i].getName().compareTo("tearDown") == 0) &&
                    (methods[i].getParameterCount() == 0)){
                tearDown = i;
                return true;
            }
        }
        return false;
    }


    /**
     * Runs the tests in the Test Class if the class is a valid test class.
     * For every test method in Test Class:
     *     - Make a setUp if possible
     *     - Run the test method if it is a valid test method. Call runMethod()
     *     - Tear down if possible
     * Print the result. Call printResultSummarize()
     * Restart all result counters. Call restartCounters()
     */
    private void runTestMethods(){
        if(validated){

            try {
                Object testCls = testClass.newInstance();

                for(int i = 0; i < methods.length; i++ ){
                    String testMethodName = methods[i].getName();

                    if (hasSetUp()) {
                        methods[setUp].invoke(testCls);
                    }
                    if(testMethodName.startsWith("test") && (methods[i].getParameterCount() == 0)
                            && (methods[i].getReturnType() == boolean.class)){
                        runMethod(testCls, testMethodName, i);
                    }
                    if (hasTearDown()) {
                        methods[tearDown].invoke(testCls);
                    }
                }
            } catch (InstantiationException e) {
                textOutput += ("The class could not be initialized\n");
            } catch (IllegalAccessException e) {
                textOutput += ("Could not get access to the class or it's constructor.\n");
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }

            makeResultSummarize();
            restartCounters();

        }
    }

    /**
     * Runs the actual test method by
     * Run the test
     * Print the result
     * @param testCls
     * @param testMethodName
     * @param testMethodIndex
     */
    private void runMethod(Object testCls, String testMethodName, int testMethodIndex){

        try {
            Boolean result = (Boolean) methods[testMethodIndex].invoke(testCls);

            if(result){
                textOutput += testMethodName + ": SUCCESS \n" ;
                successCount++;
            }

            if(!result){
                textOutput += testMethodName + ": FAIL \n";
                failCount++;
            }

        } catch (InvocationTargetException e) {
            textOutput += testMethodName + ": FAIL BY EXCEPTION " + e.getCause()+"\n";
            exceptionFailCount++;

        }   catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    /**
     * Make a summarize of the tests results
     */
    private void makeResultSummarize() {
        textOutput = textOutput.concat(
                "\n" + successCount + " Tests " +
                        "succeeded\n" + failCount + " Tests " +
                        "failed\n" + exceptionFailCount + " Tests" +
                        " failed" + " by exceptions\n\n"
        );
    }

    /**
     * Restart all test result counters.
     */
    private void restartCounters(){
        successCount = 0;
        failCount = 0;
        exceptionFailCount = 0;
    }


    /**
     * Put the test result in a temp variable.
     * Reset the output String.
     * @return the result as a String.
     */
    public String getTextOutput() {
        String temp = textOutput;
        textOutput= "";
        return temp;
    }
}
