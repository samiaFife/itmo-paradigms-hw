package expression;

public class Add extends AbstractBinary implements CommonInterface {

    public Add(CommonInterface a, CommonInterface b) {
        super(a, b, "+");
    }

    @Override
    public int calc(int a, int b) {
        return a + b;
    }

}