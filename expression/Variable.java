package expression;

public class Variable implements CommonInterface {
    private final String var;

    public Variable(String x) {
        this.var = x;
    }

    @Override
    public String toString() {
        return var;
    }

    @Override
    public boolean equals(Object expr) {
        if (this == expr) return true;
        return expr != null && this.getClass() == expr.getClass() && this.var.equals(((Variable) expr).var);
    }

    @Override
    public int hashCode() {
        return var.hashCode();
    }

    @Override
    public int evaluate(int x, int y, int z) {
        switch (var) {
            case "x" -> {
                return x;
            }
            case "y" -> {
                return y;
            }
            case "z" -> {
                return z;
            }
        }
        return -1;
    }
}
