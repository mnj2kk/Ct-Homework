package expression.exceptions;

public class NoOpeningBracketsException extends ParserException{
    NoOpeningBracketsException(){
        this.message="No opening parenthesis";
    }
}
