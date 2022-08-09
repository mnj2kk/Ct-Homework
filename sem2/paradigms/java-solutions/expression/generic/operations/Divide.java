package expression.generic.operations;

import expression.generic.BinaryOperation;
import expression.generic.BinaryOperators;
import expression.generic.GenericExpression;
import expression.generic.evaluators.Evaluator;

public class Divide<T> extends BinaryOperation<T> {
    public Divide(GenericExpression<T> x, GenericExpression<T> y) {
        super(x, y, BinaryOperators.DIVIDE);
    }

    @Override
    public T evaluate(T x, Evaluator<T> evaluator) {
        return evaluator.divide(firstPart.evaluate(x, evaluator), secondPart.evaluate(x, evaluator));
    }

    @Override
    public T evaluate(T x, T y, T z, Evaluator<T> evaluator) {
        return evaluator.divide(firstPart.evaluate(x, y, z, evaluator), secondPart.evaluate(x, y, z, evaluator));
    }
}
