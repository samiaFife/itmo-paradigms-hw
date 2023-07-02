package expression;

public class BinarySet extends AbstractBinary implements CommonInterface {
    public BinarySet(CommonInterface expr1, CommonInterface expr2) {
        super(expr1, expr2, "set");
    }

    @Override
    public int calc(int a, int b) {
        return a | (1 << b);
    }
}
