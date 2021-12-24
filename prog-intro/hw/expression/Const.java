package expression;

import java.util.Objects;

public class Const implements MyExpression {
    private final int value;

    public Const(int value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return String.valueOf(value);
    }



    @Override
    public boolean equals(Object o) {
        if(o instanceof Const){
            return o.hashCode()==this.hashCode();
        }
        return false;
    }

    @Override
    public int hashCode() {
        return Integer.hashCode(value);
    }

    @Override
    public String toMiniString() {
        return MyExpression.super.toMiniString();
    }

    @Override
    public int evaluate(int x, int y, int z) {
        return value ;
    }

    @Override
    public int evaluate(int x) {
        return value;
    }
}
