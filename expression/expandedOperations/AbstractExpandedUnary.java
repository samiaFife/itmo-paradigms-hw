package expression.expandedOperations;

import expression.exceptions.CalculateException;
import expression.expandedExpression.ExpandedExpression;
import expression.generic.TripleExpression;

public abstract class AbstractExpandedUnary<T> implements TripleExpression<T> {
    TripleExpression<T> expr;
    ExpandedExpression<T> expanded;
    String operationSymbol;

    public AbstractExpandedUnary(TripleExpression<T> expr, ExpandedExpression<T> expanded, String symbol) {
        this.expr = expr;
        this.expanded = expanded;
        this.operationSymbol = symbol;
    }

    @Override
    public int hashCode() {
        return 31 * expr.hashCode() + this.getClass().hashCode();
    }

    public T evaluate(T x, T y, T z) throws CalculateException {
        T res;
        res = calc(expr.evaluate(x, y, z));
        return res;
    }

    public T calc(T x) throws CalculateException {
        return calcImpl(x);
    }

    public abstract T calcImpl(T x) throws CalculateException;

    @Override
    public String toString() {
        return "(" + expr.toString() + ")";
    }


    @Override
    public boolean equals(Object expr) {
        if (this == expr) return true;
        if (expr == null || expr.getClass() != this.getClass()) return false;
        return this.expr.equals(((AbstractExpandedUnary<?>) expr).expr)
                && this.operationSymbol.equals(((AbstractExpandedUnary<?>) expr).operationSymbol);
    }
}
