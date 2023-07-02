package expression.expandedOperations;

import expression.exceptions.CalculateException;
import expression.expandedExpression.ExpandedExpression;
import expression.generic.TripleExpression;

public class ExpandedClear<T> extends AbstractExpandedBinary<T> {
    public ExpandedClear(TripleExpression<T> expr1, TripleExpression<T> expr2, ExpandedExpression<T> expanded) {
        super(expr1, expr2, expanded, "clear");
    }

    @Override
    public T calcImpl(T a, T b) throws CalculateException {
        return expanded.clear(a, b);
    }
}
