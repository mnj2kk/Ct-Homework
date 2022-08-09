package expression.generic.evaluators;


import expression.exceptions.OverflowException;

public class CheckedIntegerEvaluator implements Evaluator<Integer> {

    @Override
    public Integer add(Integer a, Integer b) {
        if (b >= 0 && a > Integer.MAX_VALUE - b || b < 0 && a < Integer.MIN_VALUE - b) {
            throw new OverflowException();
        }
        return a + b;
    }

    @Override
    public Integer subtract(Integer a, Integer b) {
        if ((b > 0 && Integer.MIN_VALUE + b > a) || (b < 0 && Integer.MAX_VALUE + b < a)) {
            throw new OverflowException();

        }
        return a - b;
    }

    @Override
    public Integer multiply(Integer a, Integer b) {
        int res = a * b;
        if ((a != 0 && b != 0) && (res / b != a || res / a != b)) {
            throw new OverflowException();

        }
        return res;
    }

    @Override
    public Integer divide(Integer a, Integer b) {
        if (b == 0 || (a == Integer.MIN_VALUE && b == -1)) {
            throw new OverflowException();
        }
        return a / b;
    }

    @Override
    public Integer negate(Integer a) {
        if (a == Integer.MIN_VALUE) {
            throw new OverflowException();

        }
        return -a;
    }

    @Override
    public Integer count(Integer a) {
        return Integer.bitCount(a);
    }

    @Override
    public Integer min(Integer a, Integer b) {
        return Integer.min(a, b);
    }

    @Override
    public Integer max(Integer a, Integer b) {
        return Integer.max(a, b);
    }

    @Override
    public Integer convert(int i) {
        return i;
    }

    @Override
    public Integer convertFromString(String s) {
        return Integer.parseInt(s);
    }

}
