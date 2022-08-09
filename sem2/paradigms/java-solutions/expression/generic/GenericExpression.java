package expression.generic;

import expression.ToMiniString;
import expression.generic.evaluators.Evaluator;

public interface GenericExpression<T> extends ToMiniString {
    T evaluate(T x, Evaluator<T> evaluator);

    T evaluate(T x, T y, T z, Evaluator<T> evaluator);
}
