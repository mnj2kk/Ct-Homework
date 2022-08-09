package md2html.html;

public class Variable extends HtmlElement{
    private static final String CODE = "var";
    public Variable(String x){
        this.tag=CODE;
        this.markdownSize=1;
        this.offset=4;
    }
}
