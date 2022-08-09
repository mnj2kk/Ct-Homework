package expression;
public class Subtract extends BinaryOperation{

    public Subtract(MyExpression x, MyExpression y) {
        super(x, y, BinaryOperators.SUBTRACT);
    }
    @Override
    public int evaluate(int x) {
        return super.firstPart.evaluate(x)-super.secondPart.evaluate(x);
    }

    @Override
    public int evaluate(int x, int y, int z) {
        return super.firstPart.evaluate(x,y,z)-super.secondPart.evaluate(x,y,z);
    }
}
