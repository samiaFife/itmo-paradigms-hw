package expression.expandedOperations;

import expression.exceptions.CalculateException;
import expression.expandedExpression.ExpandedExpression;
import expression.generic.TripleExpression;

public class ExpandedDivide<T> extends AbstractExpandedBinary<T> {
    public ExpandedDivide(TripleExpression<T> expr1, TripleExpression<T> expr2, ExpandedExpression<T> expanded) {
        super(expr1, expr2, expanded, "/");
    }

    public T calcImpl(T a, T b) throws CalculateException {
        return expanded.divide(a, b);
    }
}
