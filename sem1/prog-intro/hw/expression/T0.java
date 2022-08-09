package expression;

public class T0 implements MyExpression{
    MyExpression obj;
    public T0(MyExpression obj){
        this.obj=obj;
    }
    @Override
    public int evaluate(int x) {
        return Integer.numberOfTrailingZeros(obj.evaluate(x));
    }
    @Override
    public String toString() {
        return "t0(" +obj +")";
    }

    @Override
    public String toMiniString() {
        return MyExpression.super.toMiniString();
    }

    @Override
    public int evaluate(int x, int y, int z) {
        return Integer.numberOfTrailingZeros(obj.evaluate(x,y,z));
    }
}
