package expression.exceptions;

import expression.*;
import expression.parser.BaseParser;
import expression.parser.StringCharSource;

import java.util.Map;

import static expression.exceptions.ParseState.FIRST;
import static expression.exceptions.ParseState.LAST;

public class ExpressionParser extends BaseParser implements TripleParser {
    Map<Character, BinaryOperators> operatorPriority2 = Map.of('+', BinaryOperators.ADD, '-', BinaryOperators.SUBTRACT);
    Map<Character, BinaryOperators> operatorPriority1 = Map.of('*', BinaryOperators.MULTIPLY, '/', BinaryOperators.DIVIDE);
    ParseState state;

    @Override
    public MyExpression parse(String expression) throws ParsingException {
        setSource(new StringCharSource(expression));
        MyExpression res = parseExpression();
        if (source.hasNext() && ch != END) {
            if (ch == ')') {
                throw new NoParenthesesException("opening");
            } else {
                throw new WrongSymbolException(ch);
            }
        }
        return res;
    }

    private MyExpression term() throws ParsingException {
        state = FIRST;
        MyExpression firstPart = parseUnaryOperation();
        while (source.hasNext()) {
            skipWhitespace();
            BinaryOperators operator = operatorPriority1.get(ch);
            if (operator != null) {
                take();
                skipWhitespace();
                state = LAST;
                MyExpression secondPart = parseUnaryOperation();
                firstPart = parseBinaryPriority1Operation(firstPart, secondPart, operator);
            } else {
                break;
            }
        }
        return firstPart;
    }

    private MyExpression parseExpression() throws ParsingException {
        state = FIRST;
        MyExpression firstPart = term();
        while (source.hasNext()) {
            skipWhitespace();
            BinaryOperators operator = operatorPriority2.get(ch);
            StringBuilder str = new StringBuilder();
            while (between('a', 'z')) {
                str.append(ch);
                take();
            }
            String s = str.toString();
            if (!s.equals("")) {
                if (s.equals("min")) {
                    operator = BinaryOperators.MIN;
                } else if (s.equals("max")) {
                    operator = BinaryOperators.MAX;
                }
            }
            if (operator != null) {
                if (s.equals("")) {
                    take();
                }
                skipWhitespace();
                state = LAST;
                MyExpression secondPart = term();
                firstPart = parseBinaryPriority2Operation(firstPart, secondPart, operator);
            } else {

                break;
            }
        }
        return firstPart;

    }

    private MyExpression parseUnaryOperation() throws ParsingException {
        skipWhitespace();
        if (take('(')) {
            skipWhitespace();
            MyExpression expression = parseExpression();
            skipWhitespace();
            if (!source.hasNext()) {
                throw new NoParenthesesException("closing");
            }
            expect(')');
            return expression;
        } else if (take('-')) {
            skipWhitespace();
            if (between('0', '9')) {
                return parseConst(true);
            }
            return new CheckedNegate(parseUnaryOperation());
        } else if (between('0', '9')) {
            return parseConst(false);
        } else if (between('x', 'z')) {
            return parseVariable();
        } else if (take('t')) {
            expect('0');
            spaceCheck();
            return new T0(parseUnaryOperation());
        } else if (take('l')) {
            expect('0');
            spaceCheck();
            return new L0(parseUnaryOperation());
        }

        throw new NoArgumentException(state.toString());

    }

    private MyExpression parseBinaryPriority2Operation(MyExpression leftPart, MyExpression rightPart, BinaryOperators operator) {
        switch (operator) {
            case ADD:
                return new CheckedAdd(leftPart, rightPart);
            case SUBTRACT:
                return new CheckedSubtract(leftPart, rightPart);
            case MIN:
                return new Min(leftPart, rightPart);
            case MAX:
                return new Max(leftPart, rightPart);
            default:
                throw error("No such operator");
        }
    }

    private MyExpression parseBinaryPriority1Operation(MyExpression leftPart, MyExpression rightPart, BinaryOperators operator) {
        switch (operator) {
            case DIVIDE:
                return new CheckedDivide(leftPart, rightPart);
            case MULTIPLY:
                return new CheckedMultiply(leftPart, rightPart);
            default:
                throw error("No such operator");
        }
    }

    private MyExpression parseVariable() {
        Variable variable = new Variable(ch);
        take();
        return variable;
    }

    private MyExpression parseConst(boolean hasUnaryMinus) throws ParsingException {
        return new Const(readNumber(hasUnaryMinus));
    }

    private int readNumber(boolean hasUnaryMinus) throws ParsingException {
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
            return Integer.parseInt(sb.toString());
        } catch (NumberFormatException exception) {
            if (hasUnaryMinus) {
                throw new ConstantOverflowException("1");
            } else {
                throw new ConstantOverflowException("2");
            }
        }

    }

    private void spaceCheck() throws ParsingException {
        if (!isWhiteSpace() && ch != '(' && ch != ')' && ch != '-') {
            if (take(END)) {
                throw new NoArgumentException(state.toString());
            } else {
                throw new WrongSymbolException(ch);
            }
        }
    }
}
