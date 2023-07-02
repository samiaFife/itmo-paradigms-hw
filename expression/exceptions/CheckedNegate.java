package expression.exceptions;

import expression.exceptions.AbstractCheckedUnary;
import expression.CommonInterface;

public class CheckedNegate extends AbstractCheckedUnary implements CommonInterface {
    public CheckedNegate(CommonInterface expr) {
        super(expr, "-");
    }

    @Override
    public String toString() {
        return "-(" + expr.toString() + ")";
    }

    @Override
    public int calc(int x) throws CalculateException {
        if (OverflowChecking.negateOverflow(x)) {
            throw new CalculateException("Overflow caused by doing operation: -(-2147483648)");
        }
        return -x;
    }
}
