package expression.exceptions;

public abstract class ParserException extends RuntimeException{
    protected String message;
    @Override
    public String getMessage() {
        return message;
    }

}
