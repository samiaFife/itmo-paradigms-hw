package expression;

import java.util.Objects;

public class Const implements CommonInterface {
    private final int num;

    public Const(int x) {
        this.num = x;
    }

    public String toString() {
        String s = "";
        s += num;
        return s;
    }

    @Override
    public boolean equals(Object expr) {
        if (this == expr) return true;
        return expr != null && this.getClass() == expr.getClass() && (Objects.equals(this.num, ((Const) expr).num));
    }

    @Override
    public int hashCode() {
        return num;
    }

    @Override
    public int evaluate(int x, int y, int z) {
        return num;
    }
}
