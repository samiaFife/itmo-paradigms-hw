package expression.exceptions;

import expression.CommonInterface;

public class CheckedClear extends AbstractCheckedBinary implements CommonInterface {
    public CheckedClear(CommonInterface expr1, CommonInterface expr2) {
        super(expr1, expr2, "clear");
    }

    @Override
    public int calc(int a, int b) {
        return a & (~(1 << b));
    }
}
