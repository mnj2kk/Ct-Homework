package expression.exceptions;

import expression.BinaryOperation;
import expression.BinaryOperators;
import expression.MyExpression;

public class Max extends BinaryOperation {

    public Max(MyExpression firstPart, MyExpression secondPart) {
        super(firstPart, secondPart,BinaryOperators.MAX);
    }

    @Override
    public int evaluate(int x) {
        int start =firstPart.evaluate(x) ;
        int end = secondPart.evaluate(x);
        if(start<end){
            return end;
        }
        else{
            return start;
        }
    }

    @Override
    public int evaluate(int x, int y, int z) {
        int start =firstPart.evaluate(x,y,z) ;
        int end = secondPart.evaluate(x,y,z);
        if(start<end){
            return end;
        }
        else{
            return start;
        }
    }
}
