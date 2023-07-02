package expression.expandedOperations;

import expression.exceptions.CalculateException;
import expression.expandedExpression.ExpandedExpression;
import expression.generic.TripleExpression;

public class ExpandedSubtract<T> extends AbstractExpandedBinary<T> {
    public ExpandedSubtract(TripleExpression<T> expr1, TripleExpression<T> expr2, ExpandedExpression<T> expanded) {
        super(expr1, expr2, expanded, "-");
    }

    public T calcImpl(T a, T b) throws CalculateException {
//        boolean flag = OverflowChecking.subtractOverflow(a, b);
//        if (flag) throw new OverflowException(a, b, "-");
//        return a - b;
        return expanded.subtract(a, b);
    }
}
