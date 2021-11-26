package markdown;

public class Text implements MarkdownElement {
    public String value;
    public Text(String value){
        this.value=value;
    }
    @Override
    public StringBuilder toMarkdown(StringBuilder strBuilder) {
        return strBuilder.append(value);
    }
}
