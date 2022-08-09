package expression.exceptions;

public class ConstantOverflowException extends ParsingException {
    public ConstantOverflowException(String s) {
        super(String.format("Constant overflow %s", s));
    }
}
