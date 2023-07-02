package expression.expandedOperations;

import expression.exceptions.CalculateException;
import expression.expandedExpression.ExpandedExpression;
import expression.generic.TripleExpression;

public abstract class AbstractExpandedBinary<T> implements TripleExpression<T> {
    private final String operationSymbol;
    ExpandedExpression<T> expanded;
    TripleExpression<T> expr1, expr2;

    public AbstractExpandedBinary(TripleExpression<T> expr1, TripleExpression<T> expr2, ExpandedExpression<T> expanded, String operationSymbol) {
        this.expr1 = expr1;
        this.expr2 = expr2;
        this.expanded = expanded;
        this.operationSymbol = operationSymbol;
    }

    @Override
    public String toString() {
        return "(" + expr1.toString() + " " + operationSymbol + " " + expr2.toString() + ")";
    }

    public T evaluate(T x, T y, T z) throws CalculateException {
            T res = calc(expr1.evaluate(x, y, z), expr2.evaluate(x, y, z));
            return res;

    }

    @Override
    public boolean equals(Object expr) {
        if (this == expr) return true;
        if (expr == null || expr.getClass() != this.getClass()) return false;
        return expr1.equals(((AbstractExpandedBinary<?>) expr).expr1) && expr2.equals(((AbstractExpandedBinary<?>) expr).expr2)
                && operationSymbol.equals(((AbstractExpandedBinary<?>) expr).operationSymbol);
    }

    @Override
    public int hashCode() {
        return ((31 * expr1.hashCode() + this.getClass().hashCode() + expr1.getClass().hashCode()) + expr2.hashCode()
                + 31 * expr2.getClass().hashCode());
    }

    public T calc(T a, T b) throws CalculateException {
        return calcImpl(a, b);
    }
    public abstract T calcImpl(T a, T b) throws CalculateException;
}