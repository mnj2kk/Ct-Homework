package expression.exceptions;

public class NoArgumentException extends ParsingException {
    public NoArgumentException(String argument) {
        super(String.format("No %s argument", argument));
    }
}
