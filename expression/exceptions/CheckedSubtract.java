package expression.exceptions;

import expression.CommonInterface;

public class CheckedSubtract extends AbstractCheckedBinary implements CommonInterface {
    public CheckedSubtract(CommonInterface expr1, CommonInterface expr2) {
        super(expr1, expr2, "-");
    }

    @Override
    public int calc(int a, int b) throws CalculateException {
        boolean flag = OverflowChecking.subtractOverflow(a, b);
        if (flag) throw new OverflowException(a, b, "-");
        return a - b;
    }
}
