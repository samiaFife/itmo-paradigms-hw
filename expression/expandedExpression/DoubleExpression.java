package expression.expandedExpression;

import expression.exceptions.CalculateException;
import expression.exceptions.DivisionByZeroException;

import java.math.BigInteger;

public class DoubleExpression implements ExpandedExpression<Double> {
    @Override
    public Double add(Double a, Double b) throws CalculateException {
        return a + b;
    }

    @Override
    public Double subtract(Double a, Double b) throws CalculateException {
        return a - b;
    }

    @Override
    public Double multiply(Double a, Double b) throws CalculateException {
        return a * b;
    }

    @Override
    public Double divide(Double a, Double b) throws CalculateException {
        return a / b;
    }

    @Override
    public Double negate(Double a) throws CalculateException {
        return -a;
    }

    @Override
    public Double clear(Double a, Double b) throws CalculateException {
        return null;
    }

    @Override
    public Double count(Double a) throws CalculateException {
        return null;
    }

    @Override
    public Double set(Double a, Double b) throws CalculateException {
        return null;
    }

    @Override
    public Double parseNum(String source) throws NumberFormatException {
        return Double.parseDouble(source);
    }

    @Override
    public Double mod(Double a, Double b) throws CalculateException {
        return a % b;
    }

    @Override
    public Double square(Double a) throws CalculateException {
        return a * a;
    }

    @Override
    public Double abs(Double a) throws CalculateException {
        return Math.abs(a);
    }
}
