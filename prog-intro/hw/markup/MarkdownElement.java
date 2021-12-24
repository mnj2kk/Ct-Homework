package markup;
interface MarkdownElement {
    void toMarkdown(StringBuilder strBuilder);
    void toBBCode(StringBuilder strBuilder);
}
