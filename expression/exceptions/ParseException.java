package expression.exceptions;

public class ParseException extends Exception {

    public ParseException(String message) {
        super(new StringBuilder(message).toString());
    }
}
