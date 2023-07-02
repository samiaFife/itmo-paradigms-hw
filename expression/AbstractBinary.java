package expression;

public abstract class AbstractBinary implements CommonInterface {
    private final String operationSymbol;
    CommonInterface expr1, expr2;

    public AbstractBinary(CommonInterface expr1, CommonInterface expr2, String operationSymbol) {
        this.expr1 = expr1;
        this.expr2 = expr2;
        this.operationSymbol = operationSymbol;
    }

    @Override
    public String toString() {
        return "(" + expr1.toString() + " " + operationSymbol + " " + expr2.toString() + ")";
    }

    @Override
    public boolean equals(Object expr) {
        if (this == expr) return true;
        if (expr == null || expr.getClass() != this.getClass()) return false;
        return expr1.equals(((AbstractBinary) expr).expr1) && expr2.equals(((AbstractBinary) expr).expr2)
                && operationSymbol.equals(((AbstractBinary) expr).operationSymbol);
    }

    public int evaluate(int x, int y, int z) {
        return calc(expr1.evaluate(x, y, z), expr2.evaluate(x, y, z));
    }

    @Override
    public int hashCode() {
        return ((31 * expr1.hashCode() + this.getClass().hashCode() + expr1.getClass().hashCode()) + expr2.hashCode()
                + 31 * expr2.getClass().hashCode());
    }

    public int calc(int a, int b) {
        return 0;
    }
}