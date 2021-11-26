package markdown;

import java.util.List;

public class Emphasis extends MarkdownClass {
    public Emphasis(List<MarkdownElement> elements){
        this.elements=elements;
        this.appendValue=EMPHASIS;
    }
}
