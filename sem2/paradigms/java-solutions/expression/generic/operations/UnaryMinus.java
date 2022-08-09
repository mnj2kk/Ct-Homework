package expression.generic.operations;

import expression.generic.GenericExpression;
import expression.generic.evaluators.Evaluator;

public class UnaryMinus<T> implements GenericExpression<T> {
    final GenericExpression<T> obj;

    public UnaryMinus(GenericExpression<T> obj) {
        this.obj = obj;
    }

    @Override
    public T evaluate(T x, Evaluator<T> evaluator) {
        return evaluator.negate(obj.evaluate(x, evaluator));
    }

    @Override
    public String toMiniString() {
        return GenericExpression.super.toMiniString();
    }

    @Override
    public T evaluate(T x, T y, T z, Evaluator<T> evaluator) {
        return evaluator.negate(obj.evaluate(x, y, z, evaluator));
    }

    @Override
    public String toString() {
        return "-(" + obj + ')';
    }
}
