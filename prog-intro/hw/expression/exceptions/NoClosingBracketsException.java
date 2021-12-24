package expression.exceptions;

public class NoClosingBracketsException extends ParserException{
    NoClosingBracketsException(){
        this.message="No closing parenthesis";
    }
}
