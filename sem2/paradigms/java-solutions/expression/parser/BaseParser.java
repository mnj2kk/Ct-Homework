package expression.parser;

import expression.exceptions.WrongSymbolException;

public class BaseParser {
    protected static final char END = '\0';
    protected CharSource source;
    protected char ch;

    public BaseParser() {
    }

    protected void setSource(CharSource source) {
        this.source = source;
        ch = source.next();
    }

    public boolean test(final char expected) {
        return ch == expected;
    }

    protected boolean between(char st, char end) {
        return st <= ch && ch <= end;
    }

    protected char take() {
        final char result = ch;
        ch = source.hasNext() ? source.next() : END;
        return result;
    }

    protected boolean take(final char expected) {
        if (test(expected)) {
            take();
            return true;
        }
        return false;
    }

    protected boolean expect(String expected) {
        for (int i = 0; i < expected.length(); i++) {
            if (!test(expected.charAt(i))) {
                return false;
            }
            if (i < expected.length() - 1) {
                take();
            }
        }
        return true;
    }

    protected void expect(char expected) throws WrongSymbolException {
        if (!take(expected) && ch != END) {
            throw new WrongSymbolException(ch);
        }
    }

    protected IllegalArgumentException error(String message) {
        return source.error(message);
    }

    protected void skipWhitespace() {
        while (isWhiteSpace()) {
            take();
        }
    }

    protected boolean isWhiteSpace() {
        return test('\t') | test(' ') | test('\n') |
                test('\u000B') | test('\f') | test('\r')
                | test('\u2029');
    }

    protected String buildWord() {
        StringBuilder str = new StringBuilder();
        while (between('a', 'z')) {
            str.append(ch);
            take();
        }
        return str.toString();
    }


}
