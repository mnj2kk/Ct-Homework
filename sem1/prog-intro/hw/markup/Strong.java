package markup;

import java.util.List;

public class Strong extends MarkdownClass {
    public Strong(List<MarkdownElement> elements){
        this.elements=elements;
        this.appendValue= STRONG;
        this.bbcAppendValue =BBC_STRONG;
        this.bbcEndAppendValue=BBC_END_STRONG;
    }
}
