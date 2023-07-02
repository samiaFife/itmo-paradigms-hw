package expression.expandedExpression;

import expression.exceptions.CalculateException;
import expression.exceptions.DivisionByZeroException;

import java.math.BigInteger;

public class ByteExpression implements ExpandedExpression<Byte> {
    @Override
    public Byte add(Byte a, Byte b) throws CalculateException {
        return (byte) (a + b);
    }

    @Override
    public Byte subtract(Byte a, Byte b) throws CalculateException {
        return (byte) (a - b);
    }

    @Override
    public Byte multiply(Byte a, Byte b) throws CalculateException {
        return (byte) (a * b);
    }

    @Override
    public Byte divide(Byte a, Byte b) throws CalculateException {
        if (b == 0) throw new DivisionByZeroException();
        return (byte) (a / b);
    }

    @Override
    public Byte negate(Byte a) throws CalculateException {
        return (byte) (-a);
    }

    @Override
    public Byte clear(Byte a, Byte b) throws CalculateException {
        return null;
    }

    @Override
    public Byte count(Byte a) throws CalculateException {
        return null;
    }

    @Override
    public Byte set(Byte a, Byte b) throws CalculateException {
        return null;
    }

    @Override
    public Byte parseNum(String source) throws NumberFormatException {
        return (byte) (Integer.parseInt(source));
    }

    @Override
    public Byte mod(Byte a, Byte b) throws CalculateException {
        if (b == 0) throw new DivisionByZeroException();
        return (byte) (a % b);
    }

    @Override
    public Byte square(Byte a) throws CalculateException {
        return (byte)(a * a);
    }

    @Override
    public Byte abs(Byte a) throws CalculateException {
        return a < 0 ? negate(a) : a;
    }
}
