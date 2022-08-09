package expression.generic.operations;

import expression.generic.GenericExpression;
import expression.generic.evaluators.Evaluator;

public class Const<T> implements GenericExpression<T> {
    private final T value;

    public Const(T value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return String.valueOf(value);
    }


    @Override
    public boolean equals(Object o) {
        if (o instanceof Const) {
            return o.toString().equals(this.toString());
        }
        return false;
    }

    @Override
    public String toMiniString() {
        return GenericExpression.super.toMiniString();
    }

    @Override
    public T evaluate(T x, T y, T z, Evaluator<T> evaluator) {
        return value;
    }

    @Override
    public T evaluate(T x, Evaluator<T> evaluator) {
        return value;
    }
}
