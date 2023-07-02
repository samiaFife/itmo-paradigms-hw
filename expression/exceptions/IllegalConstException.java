package expression.exceptions;

public class IllegalConstException extends ParseException {
    public IllegalConstException(StringBuilder sb) {
        super("Illegal const found: " + sb.toString());
    }
}
