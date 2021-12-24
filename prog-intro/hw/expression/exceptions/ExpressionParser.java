package expression.exceptions;

import expression.*;
import expression.parser.BaseParser;
import expression.parser.StringCharSource;

import java.util.Map;

public class ExpressionParser extends BaseParser implements Parser {
    Map<BinaryOperators, Integer> operatorPriority = Map.of(BinaryOperators.CHECKED_MULTIPLY, 1, BinaryOperators.CHECKED_DIVIDE, 1,
            BinaryOperators.CHECKED_ADD, 2, BinaryOperators.CHECKED_SUBTRACT, 2, BinaryOperators.MAX, 0, BinaryOperators.MIN, 0);
    Map<Character, BinaryOperators> operatorPriorityName = Map.of('*', BinaryOperators.CHECKED_MULTIPLY, '/', BinaryOperators.CHECKED_DIVIDE,
            '+', BinaryOperators.CHECKED_ADD, '-', BinaryOperators.CHECKED_SUBTRACT,'m',BinaryOperators.MINMAX);

    @Override
    public MyExpression parse(String expression) {
        setSource(new StringCharSource(expression));
        MyExpression expr = parseExpression();
        if (ch == ')') {
            throw new NoOpeningBracketsException();
        }
        return expr;
    }

    private MyExpression term() {
        MyExpression firstPart = factor();
        while (source.hasNext()) {
            skipWhitespace();
            BinaryOperators operator = operatorPriorityName.get(ch);
            if (operator != null && operatorPriority.get(operator) == 1) {
                take();
                skipWhitespace();
                MyExpression secondPart = factor();
                firstPart = parseBinaryPriority1Operation(firstPart, secondPart, operator);
            } else {
                if (operator == null && ch != ')' && ch!='(') {
                    throw new MiddleSymbolException();
                }
                break;
            }
        }
        return firstPart;
    }
    private MyExpression factor(){
        MyExpression firstPart = parseUnaryOperation();
        while (source.hasNext()) {
            skipWhitespace();
            BinaryOperators operator = operatorPriorityName.get(ch);
            if (operator != null && operatorPriority.get(operator) == 0) {
                if (firstPart == null) {
                    throw new NoArgumentException("first");
                }
                take();
                skipWhitespace();
                MyExpression secondPart = parseUnaryOperation();
                if(secondPart==null) {
                    throw new NoArgumentException("second");
                }
                char ch = take();
                char ch1 = take();
                if((ch=='a' && ch1 =='x')){
                    return new Max(firstPart,secondPart);
                }
                if((ch=='i' && ch1 =='n')){
                    return new Min(firstPart,secondPart);
                }
            } else {
                if (firstPart == null) {
                    throw new NoArgumentException("second");
                }
                if (operator == null && ch != ')' && ch!='(') {
                    throw new MiddleSymbolException();
                }
                break;
            }
        }
        return firstPart;
    }

    private MyExpression parseExpression() {
        MyExpression firstPart = term();
        if (firstPart == null) {
            throw new NoArgumentException("first");
        }
        while (source.hasNext()) {
            skipWhitespace();
            BinaryOperators operator = operatorPriorityName.get(ch);

            if (operator != null && operatorPriority.get(operator) == 2) {
                take();
                skipWhitespace();
                MyExpression secondPart = term();
                if(secondPart==null){
                    throw  new NoArgumentException("second");
                }
                firstPart = parseBinaryPriority2Operation(firstPart, secondPart, operator);
            } else {
                if (operator == null && ch != ')' && ch !='(') {
                    throw new MiddleSymbolException();
                }
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
            if(expression==null){
                throw new NoArgumentException("in brackets");
            }
            skipWhitespace();
            if (!take(')')) {
                throw new NoClosingBracketsException();
            }
            return expression;
        } else if (take('-')) {
            skipWhitespace();
            if (between('0', '9')) {
                return parseConst(true);
            }
            CheckedNegate expr = new CheckedNegate(parseUnaryOperation());
            if(expr.v==null){
                throw new BareException('-');
            }
            return expr;
        } else if (between('0', '9')) {
            return parseConst(false);
        } else if (between('x', 'z')) {
            return parseVariable();
        } else if (take('t')) {
            expect('0');
            return new T0(parseUnaryOperation());
        } else if (take('l')) {
            expect('0');
            return new L0(parseUnaryOperation());
        } else if (take(')')) {
            throw new NoOpeningBracketsException();
        } else if (take('+')) {
            throw new BareException('+');
        } else if (take('-')) {
            throw new BareException('-');
        } else {
            return null;
        }
    }

    private MyExpression parseBinaryPriority2Operation(MyExpression leftPart, MyExpression rightPart, BinaryOperators operator) {
        switch (operator) {
            case CHECKED_ADD:
                return new CheckedAdd(leftPart, rightPart);
            case CHECKED_SUBTRACT:
                return new CheckedSubtract(leftPart, rightPart);
            case MINMAX:
            default:
                throw error("No such operator");
        }
    }

    private MyExpression parseBinaryPriority1Operation(MyExpression leftPart, MyExpression rightPart, BinaryOperators operator) {
        switch (operator) {
            case CHECKED_DIVIDE:
                return new CheckedDivide(leftPart, rightPart);
            case CHECKED_MULTIPLY:
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
        //if(between('a','z')){
          //  throw new MiddleSymbolException();
        //}
        skipWhitespace();
        if (between('0', '9')) {
            throw new SpacesInNumbers();
        }
        try {
            return Integer.parseInt(sb.toString());
        } catch (NumberFormatException exception) {
            if (hasUnaryMinus) {
                throw new ConstantOverflow1Exception();
            } else {
                throw new ConstantOverflow2Exception();
            }
        }

    }
}

