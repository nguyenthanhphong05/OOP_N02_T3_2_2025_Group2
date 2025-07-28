package review;

public class Caller {
    private Incrementable callbackReference;
    public Caller(Incrementable cb) {
        callbackReference = cb;
    }
    public void go() {
        callbackReference.increment();
    }
}
