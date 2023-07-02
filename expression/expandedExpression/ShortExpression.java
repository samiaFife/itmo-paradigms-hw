package expression.expandedExpression;

import expression.exceptions.CalculateException;
import expression.exceptions.DivisionByZeroException;

import java.math.BigInteger;

public class ShortExpression implements ExpandedExpression<Short> {
    @Override
    public Short add(Short a, Short b) throws CalculateException {
        return (short) (a + b);
    }

    @Override
    public Short subtract(Short a, Short b) throws CalculateException {
        return (short) (a - b);
    }

    @Override
    public Short multiply(Short a, Short b) throws CalculateException {
        return (short) (a * b);
    }

    @Override
    public Short divide(Short a, Short b) throws CalculateException {
        if (b == 0) throw new DivisionByZeroException();
        return (short) (a / b);
    }

    @Override
    public Short negate(Short a) throws CalculateException {
        return (short) (-a);
    }

    @Override
    public Short clear(Short a, Short b) throws CalculateException {
        return null;
    }

    @Override
    public Short count(Short a) throws CalculateException {
        return null;
    }

    @Override
    public Short set(Short a, Short b) throws CalculateException {
        return null;
    }

    @Override
    public Short parseNum(String source) throws NumberFormatException {
        return (short) (Integer.parseInt(source));
    }

    @Override
    public Short mod(Short a, Short b) throws CalculateException {
        if (b == 0) throw new DivisionByZeroException();
        return (short) (a % b);
    }

    @Override
    public Short square(Short a) throws CalculateException {
        return (short) (a * a);
    }

    @Override
    public Short abs(Short a) throws CalculateException {
        return a < 0 ? negate(a) : a;
    }
}
