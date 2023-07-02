package expression.generic;

import expression.exceptions.CalculateException;

public interface TripleExpression<T> {
    T evaluate(T x, T y, T z) throws CalculateException;
    String toString();
}
