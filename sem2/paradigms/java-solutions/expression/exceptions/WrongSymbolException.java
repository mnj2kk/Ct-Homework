package expression.exceptions;

public class WrongSymbolException extends ParsingException {
    public WrongSymbolException(char msg) {
        super(String.format("%s symbol", msg));
    }
}
