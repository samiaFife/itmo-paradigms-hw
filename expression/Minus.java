package expression;

public class Minus extends AbstractUnary implements CommonInterface {
    public Minus(CommonInterface expr) {
        super(expr, "-");
    }

    @Override
    public String toString() {
        return "-(" + expr.toString() +")";
    }

    @Override
    public int calc(int x) {
        return -x;
    }

    @Override
    public double calc(double x) {
        return -x;
    }
}
