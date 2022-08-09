package expression.generic.operations;

import expression.generic.GenericExpression;
import expression.generic.evaluators.Evaluator;

import java.util.Objects;

public class Variable<T> implements GenericExpression<T> {
    private final String v;

    public Variable(String v) {
        this.v = v;
    }

    public Variable(Character ch) {
        v = String.valueOf(ch);
    }

    @Override
    public T evaluate(T x, T y, T z, Evaluator<T> evaluator) {
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
        return GenericExpression.super.toMiniString();
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof Variable) {
            return o.hashCode() == this.hashCode();
        }
        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hash(v);
    }

    @Override
    public T evaluate(T x, Evaluator<T> evaluator) {
        return x;
    }
}
