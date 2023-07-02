package expression.generic;

import expression.exceptions.CalculateException;
import expression.exceptions.ParseException;
import expression.expandedExpression.*;

import java.util.Map;

public class GenericTabulator implements Tabulator {
    private final Map<String, ExpandedExpression<?>> mods = Map.of(
            "i", new IntegerExpression(),
            "d", new DoubleExpression(),
            "bi", new BigIntegerExpression(),
            "u", new UncheckedIntegerExpression(),
            "l", new LongExpression(),
            "s", new ShortExpression(),
            "f", new FloatExpression(),
            "b", new ByteExpression()
    );

    public Object[][][] tabulate(String mode, String expression, int x1, int x2, int y1, int y2, int z1, int z2) throws Exception {
        ExpandedExpression<?> calcMode = mods.get(mode);
        if (calcMode != null) {
            return tabImpl(calcMode, expression, x1, x2, y1, y2, z1, z2);
        } else {
            throw new RuntimeException("No such calculation mode");
        }
    }

    private <T> Object[][][] tabImpl(ExpandedExpression<T> calcMode, String expression, int x1, int x2, int y1, int y2, int z1, int z2) throws ParseException {
        ExpressionParser<T> parser = new ExpressionParser<>(calcMode);
        TripleExpression<T> parsed = parser.parse(expression);
        Object[][][] table = new Object[x2 - x1 + 1][y2 - y1 + 1][z2 - z1 + 1];
        for (int i = 0; i < x2 - x1 + 1; i++) {
            for (int j = 0; j < y2 - y1 + 1; j++) {
                for (int k = 0; k < z2 - z1 + 1; k++) {
                    T x = calcMode.parseNum(Integer.toString(x1 + i));
                    T y = calcMode.parseNum(Integer.toString(y1 + j));
                    T z = calcMode.parseNum(Integer.toString(z1 + k));
                    try {
                        T res = parsed.evaluate(x, y, z);
                        table[i][j][k] = res;
                    } catch (CalculateException e) {
                        table[i][j][k] = null;
                    }
                }
            }
        }
        return table;
    }
}
