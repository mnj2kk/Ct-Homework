package markup;

import java.util.List;

public class Emphasis extends MarkdownClass {
    public Emphasis(List<MarkdownElement> elements){
        this.elements=elements;
        this.appendValue=EMPHASIS;
        this.bbcAppendValue = BBC_EMPHASIS;
        this.bbcEndAppendValue = BBC_END_EMPHASIS;

    }
}
