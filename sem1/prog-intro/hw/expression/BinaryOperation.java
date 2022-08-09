package expression;

import java.util.Objects;

abstract public class BinaryOperation implements MyExpression {
    protected MyExpression firstPart;
    protected MyExpression secondPart;
    protected BinaryOperators OPERATOR;

    protected BinaryOperation(MyExpression firstPart, MyExpression secondPart, BinaryOperators operator) {
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
        return MyExpression.super.toMiniString();
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof BinaryOperation) {
            BinaryOperation that = ((BinaryOperation) o);
            return this.getClass() == o.getClass() && this.lHashCode() == that.lHashCode() && this.rHashCode() == that.rHashCode();
        }
            return false;
    }

    @Override
    public int hashCode() {
        return Objects.hash(firstPart, secondPart, this.getClass());
    }

    public int lHashCode() {
        return Objects.hash(firstPart);
    }

    public int rHashCode() {
        return Objects.hash(secondPart);
    }
}
