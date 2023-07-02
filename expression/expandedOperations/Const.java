package expression.expandedOperations;

import expression.generic.TripleExpression;

import java.util.Objects;

public class Const<T> implements TripleExpression<T> {
    private final T num;

    public Const(T x) {
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
        return expr != null && this.getClass() == expr.getClass() && (Objects.equals(this.num, ((Const<?>) expr).num));
    }

    @Override
    public int hashCode() {
        return (Integer) num;
    }

    @Override
    public T evaluate(T x, T y, T z) {
        return num;
    }
}
