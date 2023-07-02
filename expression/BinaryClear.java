package expression;

public class BinaryClear extends AbstractBinary implements CommonInterface {
    public BinaryClear(CommonInterface expr1, CommonInterface expr2) {
        super(expr1, expr2, "clear");
    }

    @Override
    public int calc(int a, int b) {
        return a & (~(1 << b));
    }
}
