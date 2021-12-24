package markup;

public class Text implements MarkdownElement {
    public String value;
    public Text(String value){
        this.value=value;
    }
    @Override
    public void  toMarkdown(StringBuilder strBuilder) {
         strBuilder.append(value);
    }

    @Override
    public void  toBBCode(StringBuilder strBuilder) {
        strBuilder.append(value);
    }
}
