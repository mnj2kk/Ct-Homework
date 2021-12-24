package expression.exceptions;

public class DivisionByZeroException extends ExpressionException {
    public DivisionByZeroException() {
        this.message= "division by zero";
    }
}
