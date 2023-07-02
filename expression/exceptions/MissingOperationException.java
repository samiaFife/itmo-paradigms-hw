package expression.exceptions;

public class MissingOperationException extends ParseException {
    public MissingOperationException() {
        super("Missing operation in expression between operands");
    }
}
