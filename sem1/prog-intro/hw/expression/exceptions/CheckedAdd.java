package expression.exceptions;

import expression.BinaryOperators;
import expression.MyExpression;


public class CheckedAdd extends CheckedBinaryOperation {
    protected CheckedAdd(MyExpression firstPart, MyExpression secondPart) {
        super(firstPart, secondPart, BinaryOperators.CHECKED_ADD);
    }

    @Override
    public int evaluate(int x) {
        int left = firstPart.evaluate(x);
        int right = secondPart.evaluate(x);
        checkOverflow(left,right);
        return left + right;
    }

    @Override
    public int evaluate(int x, int y, int z) {
        int left = firstPart.evaluate(x, y, z);
        int right = secondPart.evaluate(x, y, z);
        checkOverflow(left,right);
        return left + right;
    }

    private void checkOverflow(int left,int right) {
        if ((right > 0 && Integer.MAX_VALUE - right < left) || (right < 0 && Integer.MIN_VALUE - right > left)) {
            throw new OverflowException();
        }
    }
}
