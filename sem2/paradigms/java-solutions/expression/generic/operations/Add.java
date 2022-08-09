package expression.generic.operations;

import expression.generic.BinaryOperation;
import expression.generic.BinaryOperators;
import expression.generic.GenericExpression;
import expression.generic.evaluators.Evaluator;

public class Add<T> extends BinaryOperation<T> {
    public Add(GenericExpression<T> x, GenericExpression<T> y) {
        super(x, y, BinaryOperators.ADD);
    }

    @Override
    public T evaluate(T x, Evaluator<T> evaluator) {
        return evaluator.add(firstPart.evaluate(x, evaluator), secondPart.evaluate(x, evaluator));
    }

    @Override
    public T evaluate(T x, T y, T z, Evaluator<T> evaluator) {
        return evaluator.add(firstPart.evaluate(x, y, z, evaluator), secondPart.evaluate(x, y, z, evaluator));
    }
}
