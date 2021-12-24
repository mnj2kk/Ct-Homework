package expression.exceptions;

import expression.BinaryOperators;
import expression.MyExpression;

public class CheckedMultiply extends CheckedBinaryOperation {
    protected CheckedMultiply(MyExpression firstPart, MyExpression secondPart) {
        super(firstPart, secondPart, BinaryOperators.CHECKED_MULTIPLY);
    }

    @Override
    public int evaluate(int x) {
        int left = firstPart.evaluate(x);
        int right = secondPart.evaluate(x);
        checkOverflow(left,right);
        return left * right;

    }

    @Override
    public int evaluate(int x, int y, int z) {
        int left = firstPart.evaluate(x,y,z);
        int right = secondPart.evaluate(x,y,z);
        checkOverflow(left,right);
        return left * right;
    }

    public void checkOverflow(int left, int right) {
        int res = left*right;
        if ((left!=0 && right!=0 )&& (res/right!=left|| res/left!=right)) {
            throw new OverflowException();
        }
    }
}
