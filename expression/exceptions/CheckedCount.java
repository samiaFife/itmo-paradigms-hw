package expression.exceptions;

import expression.exceptions.AbstractCheckedUnary;
import expression.CommonInterface;

public class CheckedCount extends AbstractCheckedUnary implements CommonInterface {
    public CheckedCount(CommonInterface expr) {
        super(expr, "count");
    }

    @Override
    public String toString() {
        return "count(" + expr.toString() + ")";
    }

    @Override
    public int calc(int x) throws CalculateException {

        return Integer.bitCount(x);
    }

    @Override
    public double calc(double x) {
        return -x;
    }

}
