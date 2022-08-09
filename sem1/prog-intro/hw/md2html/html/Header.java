package md2html.html;

public class Header extends HtmlElement {
    private static final String CODE = "h";
    public Header(int id){
        this.tag =CODE;
        this.id=id;
    }
}
