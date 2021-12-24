package md2html.html;

public class HtmlElement {
    int id = 0;
    String tag;
    public int offset;
    public int markdownSize;
    public final String getStartingTag() {
        if (id != 0) {
            return  "<" + tag +id +">";
        } else {
            return "<" + tag +">";
        }
    }

    public final String getClosingTag() {
        if (id != 0) {
            return  "</" + tag +id +">";
        } else {
            return "</" + tag +">";
        }
    }
}