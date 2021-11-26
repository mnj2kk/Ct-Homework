package markdown;

import java.util.List;

public class Paragraph  extends MarkdownClass{
    public Paragraph(List<MarkdownElement> elements) {
        this.elements = elements;
        this.appendValue= NONE;
    }
}
