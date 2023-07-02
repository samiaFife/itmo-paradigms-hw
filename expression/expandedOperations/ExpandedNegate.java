package expression.expandedOperations;

import expression.exceptions.CalculateException;
import expression.expandedExpression.ExpandedExpression;
import expression.generic.TripleExpression;

public class ExpandedNegate<T> extends AbstractExpandedUnary<T> {
    public ExpandedNegate(TripleExpression<T> expr, ExpandedExpression<T> expanded) {
        super(expr, expanded,"-");
    }

    @Override
    public String toString() {
        return "-(" + expr.toString() + ")";
    }

    @Override
    public T calcImpl(T x) throws CalculateException {
        return expanded.negate(x);
    }
}
