package md2html.html;

public class Emphasis2 extends HtmlElement {
    private static final String CODE = "em";
    public Emphasis2() {
        this.tag=CODE;
        this.markdownSize=1;
        this.offset=3;
    }
}
