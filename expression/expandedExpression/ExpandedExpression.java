package expression.expandedExpression;

import expression.exceptions.CalculateException;

public interface ExpandedExpression<T> {
    T add(T a, T b) throws CalculateException;
    T subtract(T a, T b) throws CalculateException;
    T multiply(T a, T b) throws CalculateException;
    T divide(T a, T b) throws CalculateException;
    T negate(T a) throws CalculateException;
    T clear(T a, T b) throws CalculateException;
    T count(T a) throws CalculateException;
    T set(T a, T b) throws CalculateException;
    T parseNum(String source) throws NumberFormatException;
    T mod(T a, T b) throws CalculateException;
    T square(T a) throws CalculateException;
    T abs(T a) throws CalculateException;
}
