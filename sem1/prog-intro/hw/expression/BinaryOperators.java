package expression;

public enum BinaryOperators {
    SUBTRACT("-"),MULTIPLY("*"),DIVIDE("/"),ADD("+"),CHECKED_ADD("+"),CHECKED_DIVIDE("/"),CHECKED_SUBTRACT("-"),CHECKED_MULTIPLY("*"),MIN("min"),MAX("max"),MINMAX("m");
    private final String readableName;
    BinaryOperators(String s) {
        this.readableName=s;
    }

    public String getReadableName() {
        return readableName;
    }
}
