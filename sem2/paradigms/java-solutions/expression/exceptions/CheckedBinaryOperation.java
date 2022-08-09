package expression.exceptions;

import expression.MyExpression;
import expression.generic.BinaryOperators;

abstract public class CheckedBinaryOperation implements MyExpression {
    MyExpression firstPart;
    MyExpression secondPart;
    BinaryOperators OPERATOR;

    // :NOTE: evaluation of arguments is common everywhere
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
}
