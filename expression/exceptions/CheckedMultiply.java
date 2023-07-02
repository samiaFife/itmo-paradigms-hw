package expression.exceptions;

import expression.CommonInterface;

public class CheckedMultiply extends AbstractCheckedBinary implements CommonInterface {
    public CheckedMultiply(CommonInterface expr1, CommonInterface expr2) {
        super(expr1, expr2, "*");
    }

    @Override
    public int calc(int a, int b) throws CalculateException {
        boolean flag = OverflowChecking.multiplyOverflow(a, b);
        if (flag) throw new OverflowException(a, b, "*");
        return a * b;
    }
}
