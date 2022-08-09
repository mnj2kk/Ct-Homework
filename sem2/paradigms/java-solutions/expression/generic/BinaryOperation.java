package expression.generic;

abstract public class BinaryOperation<T> implements GenericExpression<T> {
    protected GenericExpression<T> firstPart;
    protected GenericExpression<T> secondPart;
    protected BinaryOperators OPERATOR;

    protected BinaryOperation(GenericExpression<T> firstPart, GenericExpression<T> secondPart, BinaryOperators operator) {
        this.firstPart = firstPart;
        this.secondPart = secondPart;
        this.OPERATOR = operator;
    }

    @Override
    public String toString() {
        return "(" + firstPart + " " + OPERATOR.getReadableName() + " " + secondPart + ')';
    }

    @Override
    public String toMiniString() {
        return toString();
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof BinaryOperation) {
            return this.toString().equals(o.toString());
        }
        return false;
    }

}
