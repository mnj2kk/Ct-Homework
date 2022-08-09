package expression;

public class UnaryMinus implements MyExpression {
    final MyExpression obj;

    public UnaryMinus(MyExpression obj) {
        this.obj = obj;
    }

    @Override
    public int evaluate(int x) {
        return -obj.evaluate(x);
    }

    @Override
    public String toMiniString() {
        return MyExpression.super.toMiniString();
    }

    @Override
    public int evaluate(int x, int y, int z) {
        return -obj.evaluate(x, y, z);
    }

    @Override
    public String toString() {
        return "-(" + obj + ')';
    }
}
