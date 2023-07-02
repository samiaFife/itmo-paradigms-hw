package expression;

public abstract class AbstractUnary implements CommonInterface {
    CommonInterface expr;
    String operationSymbol;

    public AbstractUnary(CommonInterface expr, String symbol) {
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
        return this.expr.equals(((AbstractUnary) expr).expr)
                && this.operationSymbol.equals(((AbstractUnary) expr).operationSymbol);
    }

    @Override
    public int evaluate(int x, int y, int z) {
        return calc(expr.evaluate(x, y, z));
    }

    public int calc(int x) {
        return 0;
    }

    public double calc(double x) {
        return 0;
    }

}
