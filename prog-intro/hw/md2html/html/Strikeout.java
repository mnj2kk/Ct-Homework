package md2html.html;

public class Strikeout extends HtmlElement {
    private static final String CODE = "s";

    public Strikeout() {
        this.tag = CODE;
        this.offset = 1;
        this.markdownSize = 2;
    }
}
