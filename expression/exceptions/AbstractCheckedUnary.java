package expression.exceptions;

import expression.CommonInterface;

public abstract class AbstractCheckedUnary implements CommonInterface {
    CommonInterface expr;
    String operationSymbol;

    public AbstractCheckedUnary(CommonInterface expr, String symbol) {
        this.expr = expr;
        this.operationSymbol = symbol;
    }

    @Override
    public int hashCode() {
        return 31 * expr.hashCode() + this.getClass().hashCode();
    }

    @Override
    public boolean equals(Object expr) {
        if (this == expr) return true;
        if (expr == null || expr.getClass() != this.getClass()) return false;
        return this.expr.equals(((AbstractCheckedUnary) expr).expr)
                && this.operationSymbol.equals(((AbstractCheckedUnary) expr).operationSymbol);
    }

    @Override
    public int evaluate(int x, int y, int z) {
        int res = 0;
        try {
            res = calc(expr.evaluate(x, y, z));
            return res;
        } catch (CalculateException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public int calc(int x) throws CalculateException{
        return 0;
    }

    public double calc(double x) {
        return 0;
    }

}
