package expression.exceptions;

public class DivisionByZeroException extends CalculateException {
    public DivisionByZeroException() {
        super("Division by zero");
    }
}
