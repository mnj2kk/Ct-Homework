package expression.parser;

public class BaseParser {
    private static final char END = 'e';
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
    protected boolean expect(String expected){
        for (int i = 0; i < expected.length(); i++) {
            if (!test(expected.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    protected void expect(char expected) {
        if (!take(expected)) {
            throw source.error(String.format("Excepted '%s' , found '%s'", expected, ch));
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


}
