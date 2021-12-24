package expression.exceptions;

import expression.BinaryOperators;
import expression.MyExpression;

public class CheckedDivide extends CheckedBinaryOperation{
    protected CheckedDivide(MyExpression firstPart, MyExpression secondPart) {
        super(firstPart, secondPart, BinaryOperators.CHECKED_DIVIDE);
    }

    @Override
    public int evaluate(int x) {
        int left = firstPart.evaluate(x);
        int right = secondPart.evaluate(x);
        checkIsNotDividingByZero(left,right);
        checkOverflow(left,right);
        return left/right;
    }

    @Override
    public int evaluate(int x, int y, int z) {
        int left = firstPart.evaluate(x,y,z);
        int right = secondPart.evaluate(x,y,z);
        checkIsNotDividingByZero(left,right);
        checkOverflow(left,right);
        return left/right;
    }
    private void checkIsNotDividingByZero(int left,int right) {
        if(right==0 ){
            throw new ExpressionException();
        }
    }
    public void checkOverflow(int left, int right) {
        if(left==Integer.MIN_VALUE && right ==-1) {
            throw new OverflowException();
        }
    }

}
