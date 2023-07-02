package expression;

public class Multiply extends AbstractBinary implements CommonInterface {
    public Multiply(CommonInterface a, CommonInterface b) {
        super(a, b, "*");
    }

    @Override
    public int calc(int a, int b) {
        return a * b;
    }
}