package expression;

public class Subtract extends AbstractBinary implements CommonInterface {
    public Subtract(CommonInterface a, CommonInterface b) {
        super(a, b, "-");
    }

    @Override
    public int calc(int a, int b) {
        return a - b;
    }
}