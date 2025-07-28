import test.OuterClassTest;
import test.TestCallback;
import test.TestStudent;

public class App {
    public static void main(String[] args) throws Exception {
        TestStudent.testStudent();
        OuterClassTest oct = new OuterClassTest();
        OuterClassTest.InnerClassTest ict = oct.new InnerClassTest();
        System.out.println(ict);
        TestCallback.testCallback();
    }
}