package expression.exceptions;

// :NOTE: should be checked
// :NOTE: no positions, not clear enough
public class ParsingException extends Exception {
    public ParsingException(String msg) {
        super(msg);
    }
}
