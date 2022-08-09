package expression.exceptions;

import expression.BinaryOperation;
import expression.BinaryOperators;
import expression.MyExpression;

import java.util.Objects;

abstract public class CheckedBinaryOperation implements MyExpression {
    MyExpression firstPart;
    MyExpression secondPart;
    BinaryOperators OPERATOR;

    protected CheckedBinaryOperation(MyExpression firstPart, MyExpression secondPart, BinaryOperators operator) {
        this.firstPart = firstPart;
        this.secondPart = secondPart;
        this.OPERATOR = operator;
    }

    @Override
    final public String toString() {
        return "(" + firstPart + " " + OPERATOR.getReadableName() + " " + secondPart + ')';
    }

    @Override
    final public String toMiniString() {
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
