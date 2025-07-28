package test;
import review.Callee;
import review.Caller;
public class TestCallback {
    public static void testCallback(){
       Callee c = new Callee();
       c.increment();
       Caller caller = new Caller(c.getCallbackReference());
       caller.go();
       caller.go();
    }
}
