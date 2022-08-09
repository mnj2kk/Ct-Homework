package expression.generic;

import expression.exceptions.*;
import expression.generic.evaluators.Evaluator;
import expression.generic.operations.Const;
import expression.generic.operations.*;
import expression.parser.BaseParser;
import expression.parser.StringCharSource;

import java.util.Map;

import static expression.exceptions.ParseState.FIRST;
import static expression.exceptions.ParseState.LAST;

public class ExpressionParser<T> extends BaseParser {
    private Evaluator<T> evaluator;
    private final Map<Character, BinaryOperators> operatorPriority2 = Map.of('+', BinaryOperators.ADD, '-', BinaryOperators.SUBTRACT);
    private final Map<Character, BinaryOperators> operatorPriority1 = Map.of('*', BinaryOperators.MULTIPLY, '/', BinaryOperators.DIVIDE);
    private final Map<String, BinaryOperators> wordPriority2 = Map.of("min", BinaryOperators.MIN, "max", BinaryOperators.MAX);
    private ParseState state;

    public GenericExpression<T> parse(String expression, Evaluator<T> evaluator) throws ParsingException {
        this.evaluator = evaluator;
        setSource(new StringCharSource(expression));
        GenericExpression<T> res = parseExpression();
        if (source.hasNext() && ch != END) {
            if (ch == ')') {
                throw new NoParenthesesException("opening");
            } else {
                throw new WrongSymbolException(ch);
            }
        }
        return res;
    }

    private GenericExpression<T> term() throws ParsingException {
        state = FIRST;
        GenericExpression<T> firstPart = parseUnaryOperation();
        while (source.hasNext()) {
            skipWhitespace();
            BinaryOperators operator = operatorPriority1.get(ch);
            if (operator != null) {
                take();
                skipWhitespace();
                state = LAST;
                GenericExpression<T> secondPart = parseUnaryOperation();
                firstPart = parseBinaryPriority1Operation(firstPart, secondPart, operator);
            } else {
                break;
            }
        }
        return firstPart;
    }

    private GenericExpression<T> parseExpression() throws ParsingException {
        state = FIRST;
        GenericExpression<T> firstPart = term();
        while (source.hasNext()) {
            skipWhitespace();
            BinaryOperators operator = operatorPriority2.get(ch);
            String s = buildWord();
            if (!s.equals("") && operator == null) {
                operator = wordPriority2.get(s);
            }
            if (operator != null) {
                if (s.equals("")) {
                    take();
                }
                skipWhitespace();
                state = LAST;
                GenericExpression<T> secondPart = term();
                firstPart = parseBinaryPriority2Operation(firstPart, secondPart, operator);
            } else {

                break;
            }
        }
        return firstPart;

    }

    private GenericExpression<T> parseUnaryOperation() throws ParsingException {
        skipWhitespace();
        if (take('(')) {
            skipWhitespace();
            GenericExpression<T> expression = parseExpression();
            skipWhitespace();
            if (!source.hasNext()) {
                throw new NoParenthesesException("closing");
            }
            expect(')');
            return expression;
        } else if (take('c')) {
            if (buildWord().equals("ount")) {
                return new Count<>(parseUnaryOperation());
            }
        } else if (take('-')) {
            skipWhitespace();
            if (between('0', '9')) {
                return parseConst(true);
            }
            return new UnaryMinus<>(parseUnaryOperation());
        } else if (between('0', '9')) {
            return parseConst(false);
        } else if (between('x', 'z')) {
            return parseVariable();
        }

        throw new NoArgumentException(state.toString());

    }

    private GenericExpression<T> parseBinaryPriority2Operation(GenericExpression<T> leftPart, GenericExpression<T> rightPart, BinaryOperators operator) {
        switch (operator) {
            case ADD:
                return new Add<>(leftPart, rightPart);
            case SUBTRACT:
                return new Subtract<>(leftPart, rightPart);
            default:
                throw error("No such operator");
            case MIN:
                return new expression.generic.operations.Min<>(leftPart, rightPart);
            case MAX:
                return new expression.generic.operations.Max<>(leftPart, rightPart);
        }
    }

    private GenericExpression<T> parseBinaryPriority1Operation(GenericExpression<T> leftPart, GenericExpression<T> rightPart, BinaryOperators operator) {
        switch (operator) {
            case DIVIDE:
                return new Divide<>(leftPart, rightPart);
            case MULTIPLY:
                return new Multiply<>(leftPart, rightPart);
            default:
                throw error("No such operator");
        }
    }

    private GenericExpression<T> parseVariable() {
        Variable<T> variable = new Variable<>(ch);
        take();
        return variable;
    }

    private GenericExpression<T> parseConst(boolean hasUnaryMinus) throws WrongSymbolException {
        return new Const<>(readNumber(hasUnaryMinus));
    }

    private T readNumber(boolean hasUnaryMinus) throws WrongSymbolException {
        StringBuilder sb = new StringBuilder();
        if (hasUnaryMinus) {
            sb.append("-");
        }

        while (between('0', '9')) {
            sb.append(take());
        }
        if (between('a', 'z')) {
            throw new WrongSymbolException(ch);
        }
        skipWhitespace();
        if (between('0', '9')) {
            throw new SpacesInNumbersException();
        }
        try {
            return evaluator.convertFromString(sb.toString());
        } catch (NumberFormatException exception) {
            return null;
        }

    }

}
