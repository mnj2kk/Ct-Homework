package expression.exceptions;

public enum ParseState {
    FIRST("first"), LAST("last");
    final String text;

    ParseState(String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return text;
    }
}
