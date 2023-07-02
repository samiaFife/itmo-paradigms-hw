package expression.expandedOperations;

import expression.exceptions.CalculateException;
import expression.expandedExpression.ExpandedExpression;
import expression.generic.TripleExpression;

public class ExpandedSet<T> extends AbstractExpandedBinary<T> {
    public ExpandedSet(TripleExpression<T> expr1, TripleExpression<T> expr2, ExpandedExpression<T> expanded) {
        super(expr1, expr2, expanded, "set");
    }

    public T calcImpl(T a, T b) throws CalculateException {
        return expanded.set(a, b);
    }
}
