package expression;

public class Divide extends AbstractBinary implements CommonInterface {
    public Divide(CommonInterface a, CommonInterface b) {
        super(a, b, "/");
    }

    @Override
    public int calc(int a, int b) {
        return a / b;
    }

}