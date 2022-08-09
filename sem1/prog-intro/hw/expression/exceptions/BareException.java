package expression.exceptions;

public class BareException extends ParserException {
    public BareException(char s) {
        this.message = "Bare Exception " + s;

    }
}
