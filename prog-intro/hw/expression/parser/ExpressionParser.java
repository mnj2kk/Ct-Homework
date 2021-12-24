package expression.parser;

import expression.*;

import java.util.Map;

public class ExpressionParser extends BaseParser implements Parser {
    Map<Character, BinaryOperators> operatorPriority2 = Map.of('+', BinaryOperators.ADD, '-', BinaryOperators.SUBTRACT);
    Map<Character, BinaryOperators> operatorPriority1 = Map.of('*', BinaryOperators.MULTIPLY, '/', BinaryOperators.DIVIDE);

    @Override
    public MyExpression parse(String expression) {
        setSource(new StringCharSource(expression));
        return parseExpression();
    }

    private MyExpression term() {
        MyExpression firstPart = parseUnaryOperation();
        while (source.hasNext()) {
            skipWhitespace();
            BinaryOperators operator = operatorPriority1.get(ch);
            if (operator != null) {
                take();
                skipWhitespace();
                MyExpression secondPart = parseUnaryOperation();
                firstPart = parseBinaryPriority1Operation(firstPart, secondPart, operator);
            } else {
                break;
            }
        }
        return firstPart;
    }

    private MyExpression parseExpression() {
        MyExpression firstPart = term();
        while (source.hasNext()) {
            skipWhitespace();
            BinaryOperators operator = operatorPriority2.get(ch);
            if (operator != null) {
                take();
                skipWhitespace();
                MyExpression secondPart = term();
                firstPart= parseBinaryPriority2Operation(firstPart, secondPart, operator);
            } else {
                break;
            }
        }
        return firstPart;

    }

    private MyExpression parseUnaryOperation() {
        skipWhitespace();
        if (take('(')) {
            skipWhitespace();
            MyExpression expression = parseExpression();
            skipWhitespace();
            expect(')');
            return expression;
        } else if (take('-')) {
            skipWhitespace();
            if (between('0', '9')) {
                return parseConst(true);
            }
            return new UnaryMinus(parseUnaryOperation());
        } else if (between('0', '9')) {
            return parseConst(false);
        } else if (between('x', 'z')) {
            return parseVariable();
        }
        else if(take('t')){
            expect('0');
            return new T0(parseUnaryOperation());
        }
        else if(take('l')){
            expect('0');
            return new L0(parseUnaryOperation());
        }
        throw error("Something get wrong " + (int) ch);

    }

    private MyExpression parseBinaryPriority2Operation(MyExpression leftPart, MyExpression rightPart, BinaryOperators operator) {
        switch (operator) {
            case ADD:
                return new Add(leftPart, rightPart);
            case SUBTRACT:
                return new Subtract(leftPart, rightPart);
            default:
                throw error("No such operator");
        }
    }

    private MyExpression parseBinaryPriority1Operation(MyExpression leftPart, MyExpression rightPart, BinaryOperators operator) {
        switch (operator) {
            case DIVIDE:
                return new Divide(leftPart, rightPart);
            case MULTIPLY:
                return new Multiply(leftPart, rightPart);
            default:
                throw error("No such operator");
        }
    }

    private MyExpression parseVariable() {
        Variable variable = new Variable(ch);
        take();
        return variable;
    }

    private MyExpression parseConst(boolean hasUnaryMinus) {
        return new Const(readNumber(hasUnaryMinus));
    }

    private int readNumber(boolean hasUnaryMinus) {
        StringBuilder sb = new StringBuilder();
        if (hasUnaryMinus) {
            sb.append("-");
        }

        while (between('0', '9')) {
            sb.append(take());
        }
        try {
            return Integer.parseInt(sb.toString());
        } catch (NumberFormatException exception) {
            throw error(exception.getMessage());
        }

    }
}
