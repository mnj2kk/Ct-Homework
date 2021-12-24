package markup;

import java.util.List;

public class Strikeout extends MarkdownClass {
    public Strikeout(List<MarkdownElement> elements){
        this.elements=elements;
        this.appendValue=STRIKEOUT;
        this.bbcAppendValue =BBC_STRIKEOUT;
        this.bbcEndAppendValue=BBC_END_STRIKEOUT;

    }

}
