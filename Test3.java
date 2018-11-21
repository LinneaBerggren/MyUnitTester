import se.umu.cs.unittest.TestClass;
/**
 * Created by Linnea on 2018-11-20.
 * A testclass that have a constructor with parameters
 */


public class Test3 implements TestClass {
    private MyInt myInt;

    public Test3(String text) {
    }

    public void setUp() {
        myInt=new MyInt();
    }

    public void tearDown() {
        myInt=null;
    }

    //Test that should succeed
    public boolean testInitialisation() {
        return myInt.value()==0;
    }

    //Test that should succeed
    public boolean testIncrement() {
        myInt.increment();
        myInt.increment();
        return myInt.value()==2;

    }

    //Test that should succeed
    public boolean testDecrement() {
        myInt.increment();
        myInt.decrement();
        return myInt.value()==0;
    }

    //Test that should fail
    public boolean testFailingByException() {
        myInt=null;
        myInt.decrement();
        return true;

    }

    //Test that should fail
    public boolean testFailing() {
        return false;

    }


}








