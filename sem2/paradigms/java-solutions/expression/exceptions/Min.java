package expression.exceptions;

import expression.MyExpression;
import expression.generic.BinaryOperators;

public class Min extends CheckedBinaryOperation {

    public Min(MyExpression firstPart, MyExpression secondPart) {
        super(firstPart, secondPart, BinaryOperators.MIN);
    }

    @Override
    public int evaluate(int x) {
        int start = firstPart.evaluate(x);
        int end = secondPart.evaluate(x);
        if (start > end) {
            return end;
        } else {
            return start;
        }
    }

    @Override
    public int evaluate(int x, int y, int z) {
        int start = firstPart.evaluate(x, y, z);
        int end = secondPart.evaluate(x, y, z);
        if (start > end) {
            return end;
        } else {
            return start;
        }
    }
}
