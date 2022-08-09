package expression.generic;


import expression.exceptions.OverflowException;
import expression.exceptions.ParsingException;
import expression.generic.evaluators.BigIntegerEvaluator;
import expression.generic.evaluators.CheckedIntegerEvaluator;
import expression.generic.evaluators.DoubleEvaluator;
import expression.generic.evaluators.Evaluator;

import java.util.Map;

public class GenericTabulator implements Tabulator {
    private final static Map<String, Evaluator<?>> EVALUATOR_MAP =
            Map.of(
                    "bi", new BigIntegerEvaluator(),
                    "d", new DoubleEvaluator(),
                    "i", new CheckedIntegerEvaluator());

    @Override
    public Object[][][] tabulate(String mode, String in, int x1, int x2, int y1, int y2, int z1, int z2) throws ParsingException {
        return tabulateImpl(EVALUATOR_MAP.get(mode), in, x1, x2, y1, y2, z1, z2);
    }

    public <T> Object[][][] tabulateImpl(Evaluator<T> evaluator, String in, int x1, int x2, int y1, int y2, int z1, int z2) throws ParsingException {
        ExpressionParser<T> parser = new ExpressionParser<>();
        GenericExpression<T> expression = parser.parse(in, evaluator);
        Object[][][] arr = new Object[x2 - x1 + 2][y2 - y1 + 2][z2 - z1 + 2];
        for (int i = x1; i <= x2; i++) {
            for (int j = y1; j <= y2; j++) {
                for (int k = z1; k <= z2; k++) {
                    try {
                        arr[i - x1][j - y1][k - z1] = expression.evaluate(evaluator.convert(i), evaluator.convert(j), evaluator.convert(k), evaluator);
                    } catch (OverflowException | ArithmeticException ignored) {

                    }
                }
            }
        }
        return arr;
    }
}
