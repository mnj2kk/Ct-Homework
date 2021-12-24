package md2html.html;

public class Emphasis1 extends HtmlElement{
    private static final String CODE = "em";
    public Emphasis1() {
        this.tag=CODE;
        this.markdownSize=1;
        this.offset=3;
    }
}
