package expression.exceptions;

public class MissingOperandException extends ParseException {
    public MissingOperandException(int type) {
        super("Missing operand in " + (type == 1 ? "unary" : "binary") + " operation");
    }
}
