package expression.exceptions;

public class OverflowException extends CalculateException {
    public OverflowException(int a, int b, String operation) {
        super(new StringBuilder("Overflow caused by doing operation: ").append(a).append(" ").append(operation)
                .append(" ").append(b).toString());
    }
}
