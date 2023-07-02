package expression.expandedOperations;

import expression.exceptions.CalculateException;
import expression.expandedExpression.ExpandedExpression;
import expression.generic.TripleExpression;

public class ExpandedAbs<T> extends AbstractExpandedUnary<T> {
    public ExpandedAbs(TripleExpression<T> expr, ExpandedExpression<T> expanded) {
        super(expr, expanded,"abs");
    }

    @Override
    public String toString() {
        return "|" + expr.toString() + "|";
    }

    @Override
    public T calcImpl(T x) throws CalculateException {
        return expanded.abs(x);
    }
}
