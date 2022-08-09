package markup;

import java.util.List;

abstract class MarkdownClass implements MarkdownElement {

    protected static final String EMPHASIS = "*";
    protected static final String STRONG = "__";
    protected static final String STRIKEOUT = "~";
    protected static final String NONE = "";

    protected static final String BBC_EMPHASIS = "[i]";
    protected static final String BBC_END_EMPHASIS = "[/i]";
    protected static final String BBC_STRONG = "[b]";
    protected static final String BBC_END_STRONG = "[/b]";
    protected static final String BBC_STRIKEOUT = "[s]";
    protected static final String BBC_END_STRIKEOUT = "[/s]";


    List<MarkdownElement> elements;
    String appendValue;
    String bbcAppendValue;
    String bbcEndAppendValue;

    @Override
    final public void  toMarkdown(StringBuilder strBuilder) {
        strBuilder.append(appendValue);
        for (MarkdownElement element : elements) {
            element.toMarkdown(strBuilder);
        }
         strBuilder.append(appendValue);
    }
    @Override
    final public  void toBBCode(StringBuilder strBuilder) {
        strBuilder.append(bbcAppendValue);
        for (MarkdownElement element : elements) {
            element.toBBCode(strBuilder);
        }
        strBuilder.append(bbcEndAppendValue);
    }
}
