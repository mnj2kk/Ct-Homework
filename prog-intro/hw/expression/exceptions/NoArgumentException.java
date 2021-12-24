package expression.exceptions;

public class NoArgumentException extends ParserException{
    public NoArgumentException(String s) {
        this.message=" No " +s + " argument";
    }
}
