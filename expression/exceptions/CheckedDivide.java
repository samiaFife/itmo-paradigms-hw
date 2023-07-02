package expression.exceptions;

import expression.CommonInterface;

public class CheckedDivide extends AbstractCheckedBinary implements CommonInterface {
    public CheckedDivide(CommonInterface expr1, CommonInterface expr2) {
        super(expr1, expr2, "/");
    }

    @Override
    public int calc(int a, int b) throws CalculateException {
        if (b == 0) throw new DivisionByZeroException();
        boolean flag = OverflowChecking.divideOverflow(a, b);
        if (flag) throw new OverflowException(a, b, "/");
        return a / b;
    }
}
