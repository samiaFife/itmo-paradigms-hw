package expression.expandedExpression;

import expression.exceptions.CalculateException;
import expression.exceptions.DivisionByZeroException;

import java.math.BigInteger;

public class LongExpression implements ExpandedExpression<Long> {
    @Override
    public Long add(Long a, Long b) throws CalculateException {
        return a + b;
    }

    @Override
    public Long subtract(Long a, Long b) throws CalculateException {
        return a - b;
    }

    @Override
    public Long multiply(Long a, Long b) throws CalculateException {
        return a * b;
    }

    @Override
    public Long divide(Long a, Long b) throws CalculateException {
        if (b == 0) throw new DivisionByZeroException();
        return a / b;
    }

    @Override
    public Long negate(Long a) throws CalculateException {
        return -a;
    }

    @Override
    public Long clear(Long a, Long b) throws CalculateException {
        return null;
    }

    @Override
    public Long count(Long a) throws CalculateException {
        return null;
    }

    @Override
    public Long set(Long a, Long b) throws CalculateException {
        return null;
    }

    @Override
    public Long parseNum(String source) throws NumberFormatException {
        return Long.parseLong(source);
    }

    @Override
    public Long mod(Long a, Long b) throws CalculateException {
        if (b == 0) throw new DivisionByZeroException();
        return a % b;
    }

    @Override
    public Long square(Long a) throws CalculateException {
        return a * a;
    }

    @Override
    public Long abs(Long a) throws CalculateException {
        return Math.abs(a);
    }
}
