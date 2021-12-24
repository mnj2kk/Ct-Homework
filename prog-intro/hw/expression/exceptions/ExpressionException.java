package expression.exceptions;

public class ExpressionException extends RuntimeException{
    protected String message;
    @Override
    public String getMessage() {
        return message;
    }
}
