package expression.generic;

import expression.generic.evaluators.Evaluator;

public class Multiply<T> extends BinaryOperation<T> {
    public Multiply(GenericExpression<T> x, GenericExpression<T> y) {
        super(x, y, BinaryOperators.MULTIPLY);
    }

    @Override
    public T evaluate(T x, Evaluator<T> evaluator) {
        return evaluator.multiply(firstPart.evaluate(x, evaluator), secondPart.evaluate(x, evaluator));
    }

    @Override
    public T evaluate(T x, T y, T z, Evaluator<T> evaluator) {
        return evaluator.multiply(firstPart.evaluate(x, y, z, evaluator), secondPart.evaluate(x, y, z, evaluator));
    }
}
