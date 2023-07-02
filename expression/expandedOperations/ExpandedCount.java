package expression.expandedOperations;

import expression.exceptions.CalculateException;
import expression.expandedExpression.ExpandedExpression;
import expression.generic.TripleExpression;

public class ExpandedCount<T> extends AbstractExpandedUnary<T> {
    public ExpandedCount(TripleExpression<T> expr, ExpandedExpression<T> expanded) {
        super(expr, expanded, "count");
    }

    @Override
    public String toString() {
        return "count(" + expr.toString() + ")";
    }

    @Override
    public T calcImpl(T x) throws CalculateException {
        return expanded.count(x);
    }

}
