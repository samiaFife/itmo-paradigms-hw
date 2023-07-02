package expression.exceptions;

import expression.CommonInterface;

public class CheckedAdd extends AbstractCheckedBinary implements CommonInterface {

    public CheckedAdd(CommonInterface a, CommonInterface b) {
        super(a, b, "+");
    }

    @Override
    public int calc(int a, int b) throws CalculateException {
        boolean flag = OverflowChecking.addOverflow(a, b);
        if (flag) throw new OverflowException(a, b, "+");
        return a + b;
    }

}