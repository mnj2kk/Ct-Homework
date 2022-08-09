package expression;

public class L0 implements MyExpression {
    MyExpression obj;

    public L0(MyExpression obj) {
        this.obj = obj;
    }

    @Override
    public String toString() {
        return "l0(" + obj + ")";
    }

    @Override
    public int evaluate(int x) {
        return Integer.numberOfLeadingZeros(x);
    }

    @Override
    public String toMiniString() {
        return MyExpression.super.toMiniString();
    }

    @Override
    public int evaluate(int x, int y, int z) {
        return Integer.numberOfLeadingZeros(obj.evaluate(x, y, z));
    }
}
