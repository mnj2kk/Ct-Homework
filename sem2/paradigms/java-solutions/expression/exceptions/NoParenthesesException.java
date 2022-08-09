package expression.exceptions;

public class NoParenthesesException extends ParsingException {
    public NoParenthesesException(String msg) {
        super(String.format("No %s brackets", msg));
    }
}
