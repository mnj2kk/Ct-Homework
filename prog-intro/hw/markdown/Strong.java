package markdown;

import java.util.List;

public class Strong extends MarkdownClass {
    public Strong(List<MarkdownElement> elements){
        this.elements=elements;
        this.appendValue= STRONG;
    }
}
