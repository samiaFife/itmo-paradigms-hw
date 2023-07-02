package expression.exceptions;

public class BracketSequenceException extends ParseException {
    public BracketSequenceException(char bracket) {
        super(new StringBuilder("Not enough '").append(bracket).append("\' brackets").toString());
    }
}
