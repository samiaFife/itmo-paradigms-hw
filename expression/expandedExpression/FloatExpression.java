package expression.expandedExpression;

import expression.exceptions.CalculateException;
import expression.exceptions.DivisionByZeroException;

import java.math.BigInteger;

public class FloatExpression implements ExpandedExpression<Float> {
    @Override
    public Float add(Float a, Float b) throws CalculateException {
        return (a + b);
    }

    @Override
    public Float subtract(Float a, Float b) throws CalculateException {
        return (a - b);
    }

    @Override
    public Float multiply(Float a, Float b) throws CalculateException {
        return (a * b);
    }

    @Override
    public Float divide(Float a, Float b) throws CalculateException {
        return (a / b);
    }

    @Override
    public Float negate(Float a) throws CalculateException {
        return (-a);
    }

    @Override
    public Float clear(Float a, Float b) throws CalculateException {
        return null;
    }

    @Override
    public Float count(Float a) throws CalculateException {
        return null;
    }

    @Override
    public Float set(Float a, Float b) throws CalculateException {
        return null;
    }

    @Override
    public Float parseNum(String source) throws NumberFormatException {
        return (Float.parseFloat(source));
    }

    @Override
    public Float mod(Float a, Float b) throws CalculateException {
        if (b == 0) throw new DivisionByZeroException();
        return (a % b);
    }

    @Override
    public Float square(Float a) throws CalculateException {
        return a * a;
    }

    @Override
    public Float abs(Float a) throws CalculateException {
        return Math.abs(a);
    }
}
