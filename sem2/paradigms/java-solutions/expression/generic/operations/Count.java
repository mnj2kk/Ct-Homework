package expression.generic.operations;

import expression.generic.GenericExpression;
import expression.generic.evaluators.Evaluator;

public class Count<T> implements GenericExpression<T> {
    GenericExpression<T> value;

    public Count(GenericExpression<T> x) {
        value = x;
    }

    @Override
    public T evaluate(T x, Evaluator<T> evaluator) {
        return evaluator.count(value.evaluate(x, evaluator));
    }

    @Override
    public T evaluate(T x, T y, T z, Evaluator<T> evaluator) {
        return evaluator.count(value.evaluate(x, y, z, evaluator));
    }

    @Override
    public String toString() {
        return "count " + value;
    }
}
