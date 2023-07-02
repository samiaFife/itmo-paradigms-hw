package expression.exceptions;

import expression.CommonInterface;

public class CheckedSet extends AbstractCheckedBinary implements CommonInterface {
    public CheckedSet(CommonInterface expr1, CommonInterface expr2) {
        super(expr1, expr2, "set");
    }

    @Override
    public int calc(int a, int b) {
        return a | (1 << b);
    }
}
