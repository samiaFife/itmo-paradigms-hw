package expression.expandedOperations;

import expression.exceptions.CalculateException;
import expression.expandedExpression.ExpandedExpression;
import expression.generic.TripleExpression;

public class ExpandedMod<T> extends AbstractExpandedBinary<T> {
    public ExpandedMod(TripleExpression<T> expr1, TripleExpression<T> expr2, ExpandedExpression<T> expanded) {
        super(expr1, expr2, expanded,"mod");
    }

    @Override
    public String toString() {
        return "(" + expr1.toString() + " mod " + expr2.toString() + ")";
    }

    @Override
    public T calcImpl(T x, T y) throws CalculateException {
        return expanded.mod(x, y);
    }
}
