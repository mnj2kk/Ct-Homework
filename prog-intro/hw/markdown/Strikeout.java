package markdown;

import java.util.List;

public class Strikeout extends MarkdownClass {
    public Strikeout(List<MarkdownElement> elements){
        this.elements=elements;
        this.appendValue=STRIKEOUT;

    }

}
