package expression.exceptions;

import expression.MyExpression;

public class CheckedNegate implements MyExpression {
    public MyExpression v;

    public CheckedNegate(MyExpression v) {
        this.v = v;
    }

    @Override
    public int evaluate(int x) {
        int res = v.evaluate(x);
        checkOverflow(res);
        return -res;
    }

    @Override
    public int evaluate(int x, int y, int z) {
        int res = v.evaluate(x, y, z);
        checkOverflow(res);
        return -res;
    }

    void checkOverflow(int x) {
        if (x == Integer.MIN_VALUE) {
            throw new OverflowException();
        }
    }

    @Override
    public String toString() {
        return "-(" + v + ")";
    }
}
