package expression;

import java.util.Objects;

public class Variable implements MyExpression{
    private final  String v;
    public Variable(String v){
        this.v=v;
    }

    public Variable(Character ch) {
        v = String.valueOf(ch);
    }

    @Override
    public int evaluate(int x,int y,int z) {
        switch (v) {
            case "x":
                return x;
            case "y":
                return y;
            case "z":
                return z;
            default:
                throw new AssertionError("No such variable");
        }
    }

    @Override
    public String toString() {
        return v;
    }

    @Override
    public String toMiniString() {
        return MyExpression.super.toMiniString();
    }
    @Override
    public boolean equals(Object o) {
        if(o instanceof Variable){
            return o.hashCode()==this.hashCode();
        }
        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hash(v);
    }

    @Override
    public int evaluate(int x) {
        return x;
    }
}
