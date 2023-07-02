package expression.expandedExpression;

import expression.exceptions.CalculateException;
import expression.exceptions.DivisionByZeroException;

import java.math.BigInteger;

public class BigIntegerExpression implements ExpandedExpression<BigInteger> {
    @Override
    public BigInteger add(BigInteger a, BigInteger b) throws CalculateException {
        return a.add(b);
    }

    @Override
    public BigInteger subtract(BigInteger a, BigInteger b) throws CalculateException {
        return a.subtract(b);
    }

    @Override
    public BigInteger multiply(BigInteger a, BigInteger b) throws CalculateException {
        return a.multiply(b);
    }

    @Override
    public BigInteger divide(BigInteger a, BigInteger b) throws CalculateException {
        if (b.equals(BigInteger.ZERO)) throw new DivisionByZeroException();
        return a.divide(b);
    }

    @Override
    public BigInteger negate(BigInteger a) throws CalculateException {
        BigInteger sum = BigInteger.ZERO;
        return sum.subtract(a);
    }

    @Override
    public BigInteger clear(BigInteger a, BigInteger b) throws CalculateException {
        return null;
    }

    @Override
    public BigInteger count(BigInteger a) throws CalculateException {
        return null;
    }

    @Override
    public BigInteger set(BigInteger a, BigInteger b) throws CalculateException {
        return null;
    }

    @Override
    public BigInteger parseNum(String source) throws NumberFormatException {
        return new BigInteger(source);
    }

    @Override
    public BigInteger mod(BigInteger a, BigInteger b) throws CalculateException {
        if (b.equals(BigInteger.ZERO)) throw new DivisionByZeroException();
        if (b.signum() < 0) throw new CalculateException("Negative modulus");
        return a.mod(b);
    }

    @Override
    public BigInteger square(BigInteger a) throws CalculateException {
        return a.multiply(a);
    }

    @Override
    public BigInteger abs(BigInteger a) throws CalculateException {
        return a.signum() < 0 ? negate(a) : a;
    }
}
