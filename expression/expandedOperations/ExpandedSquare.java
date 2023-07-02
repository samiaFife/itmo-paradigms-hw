package expression.expandedOperations;

import expression.exceptions.CalculateException;
import expression.expandedExpression.ExpandedExpression;
import expression.generic.TripleExpression;

public class ExpandedSquare<T> extends AbstractExpandedUnary<T> {
    public ExpandedSquare(TripleExpression<T> expr, ExpandedExpression<T> expanded) {
        super(expr, expanded,"^2");
    }

    @Override
    public String toString() {
        return "(" + expr.toString() + ")^2";
    }

    @Override
    public T calcImpl(T x) throws CalculateException {
        return expanded.square(x);
    }
}
