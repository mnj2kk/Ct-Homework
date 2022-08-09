package expression;

import expression.exceptions.ParserException;

public class MiddleSymbolException extends ParserException {
    public MiddleSymbolException(){
        this.message="Middle symbol";
    }

}
