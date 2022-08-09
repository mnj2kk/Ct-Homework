package md2html.html;

public class Code extends HtmlElement{
    private static final String CODE = "code";
    public Code(){
        this.tag=CODE;
        this.markdownSize=1;
        this.offset=5;
    }
}
