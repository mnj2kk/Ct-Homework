package markdown;

import java.util.List;

abstract class MarkdownClass implements MarkdownElement {

    protected static final String EMPHASIS = "*";
    protected static final String STRONG = "__";
    protected static final String STRIKEOUT = "~";
    protected static final String NONE = "";

    List<MarkdownElement> elements;
    String appendValue;

    @Override
    final public StringBuilder toMarkdown(StringBuilder strBuilder) {
        strBuilder.append(appendValue);
        for (MarkdownElement element : elements) {
            strBuilder.append(element.toMarkdown(new StringBuilder()));
        }
        return strBuilder.append(appendValue);
    }
}
