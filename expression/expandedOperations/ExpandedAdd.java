package expression.expandedOperations;

import expression.exceptions.CalculateException;
import expression.expandedExpression.ExpandedExpression;
import expression.generic.TripleExpression;

public class ExpandedAdd<T> extends AbstractExpandedBinary<T> {

    public ExpandedAdd(TripleExpression<T> a, TripleExpression<T> b, ExpandedExpression<T> expanded) {
        super(a, b, expanded, "+");
    }

    public T calcImpl(T a, T b) throws CalculateException {
        return expanded.add(a, b);
    };

}