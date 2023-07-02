package expression.expandedExpression;

import expression.exceptions.CalculateException;
import expression.exceptions.DivisionByZeroException;
import expression.exceptions.OverflowException;

public class UncheckedIntegerExpression implements ExpandedExpression<Integer> {

    @Override
    public Integer add(Integer a, Integer b) throws OverflowException {
        return a + b;
    }

    @Override
    public Integer subtract(Integer a, Integer b) throws OverflowException {
        return a - b;
    }

    @Override
    public Integer multiply(Integer a, Integer b) throws OverflowException {
        return a * b;
    }

    @Override
    public Integer divide(Integer a, Integer b) throws CalculateException {
        if (b == 0) throw new DivisionByZeroException();
        return a / b;
    }

    @Override
    public Integer negate(Integer a) throws CalculateException {
        return -a;
    }

    @Override
    public Integer clear(Integer a, Integer b) {
        return a & (~(1 << b));
    }

    @Override
    public Integer count(Integer a) {
        return Integer.bitCount(a);
    }

    @Override
    public Integer set(Integer a, Integer b) {
        return a | (1 << b);
    }

    @Override
    public Integer parseNum(String source) {
        return Integer.parseInt(source);
    }

    @Override
    public Integer mod(Integer a, Integer b) throws CalculateException {
        if (b == 0) throw new DivisionByZeroException();
        return a % b;
    }

    @Override
    public Integer square(Integer a) throws CalculateException {
        return a * a;
    }

    @Override
    public Integer abs(Integer a) throws CalculateException {
        return Math.abs(a);
    }
}
