package expression.generic.operations;

import expression.generic.BinaryOperation;
import expression.generic.BinaryOperators;
import expression.generic.GenericExpression;
import expression.generic.evaluators.Evaluator;

public class Max<T> extends BinaryOperation<T> {
    public Max(GenericExpression<T> x, GenericExpression<T> y) {
        super(x, y, BinaryOperators.MAX);
    }

    @Override
    public T evaluate(T x, Evaluator<T> evaluator) {
        return evaluator.max(firstPart.evaluate(x, evaluator), secondPart.evaluate(x, evaluator));
    }

    @Override
    public T evaluate(T x, T y, T z, Evaluator<T> evaluator) {
        return evaluator.max(firstPart.evaluate(x, y, z, evaluator), secondPart.evaluate(x, y, z, evaluator));
    }
}
