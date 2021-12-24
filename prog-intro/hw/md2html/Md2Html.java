package md2html;

import md2html.html.*;
import scanner.MyScanner;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.*;

import static md2html.html.HtmlCodes.*;

public class Md2Html {
    static Map<Integer, HtmlElement> usedElements = new LinkedHashMap<>();
    static Map<HtmlElement, ArrayList<Integer>> indexesOfUsedElements = new HashMap<>();
    static HtmlElement tag;

    public static void main(String[] args) {

        String INPUT_FILE = args[0];
        String OUTPUT_FILE = args[1];
        List<StringBuilder> blocks = new ArrayList<>();
        try {
            MyScanner scan = new MyScanner(INPUT_FILE, "UTF-8");
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(OUTPUT_FILE), StandardCharsets.UTF_8));
            StringBuilder block = new StringBuilder();
            while (scan.hasNextLine()) {
                String line = scan.nextLine();
                if (line.isEmpty()) {
                    if (!block.toString().equals("")) {
                        blocks.add(block);
                    }
                    block = new StringBuilder();
                } else {
                    block.append(line).append(System.lineSeparator());
                }
            }
            scan.close();
            blocks.add(block);
            for (StringBuilder bl : blocks) {
                int headerSize = 0;
                for (int i = 0; i < bl.length(); i++) {
                    if (bl.charAt(i) == '#') {
                        headerSize++;
                    } else if (bl.charAt(i) == ' ') {
                        break;
                    } else {
                        headerSize = 0;
                        break;
                    }
                }
                HtmlElement headCode;
                if (headerSize != 0) {
                    headCode = new Header(headerSize);
                } else {
                    headCode = new Paragraph();
                    --headerSize;
                }
                //To be sure that there be no ArrayIndexException
                bl.replace(0, 0, " ");
                bl.replace(bl.length() - 1, bl.length() - 1, " ");
                for (int i = 1; i < bl.length() - 1; i++) {
                    getTags(bl, i);
                    if (tag != null) {
                        ArrayList<Integer> list;
                        list = indexesOfUsedElements.getOrDefault(tag,new ArrayList<>());
                        list.add(i);
                        indexesOfUsedElements.put(tag, list);
                        usedElements.put(i, tag);
                        if (tagHasLength2(tag)) {
                            i++;
                        }
                    }
                }
                removeSingleElements();
                writeHtmlCodes(bl);
                bl.replace(0, headerSize + 2, headCode.getStartingTag());
                if(bl.charAt(bl.length()-3)=='\r' && bl.charAt(bl.length()-1)=='\n') {
                    bl.replace(bl.length() - 3, bl.length() - 1, headCode.getClosingTag());
                }
                else {
                    bl.replace(bl.length() - 2, bl.length() - 1, headCode.getClosingTag());
                }
                writer.write(bl.toString());

                indexesOfUsedElements.clear();
                usedElements.clear();

            }


            writer.close();
        } catch (IOException e) {
            System.out.println("Problems with reading/writing files" + e.getMessage());
        }


    }

    static void writeHtmlCodes(StringBuilder bl) {
        int offset = 0;
        for (Map.Entry<Integer, HtmlElement> entry : usedElements.entrySet()) {
            int index = entry.getKey() + offset;
            HtmlElement elem = entry.getValue();
            ArrayList<Integer> indexes = indexesOfUsedElements.get(elem);
            if (indexes.size() > 0) {
                if (indexes.size() % 2 == 0) {
                    bl.replace(index, index + elem.markdownSize, elem.getStartingTag());
                    offset += elem.offset;
                } else {
                    bl.replace(index, index + elem.markdownSize, elem.getClosingTag());
                    offset += elem.offset + 1;

                }
                indexes.remove(indexes.size() - 1);
            }

        }
    }

    static void getTags(StringBuilder bl, int i) {
        char prevSymbol = bl.charAt(i - 1);
        char symbol = bl.charAt(i);
        char nextSymbol = bl.charAt(i + 1);
        tag = null;
        if (prevSymbol != '\\') {
            if (symbol == '`') {
                tag = CODE;
            } else if (symbol == '*' && nextSymbol == '*') {
                tag = STRONG1;
            } else if (symbol == '_' && nextSymbol == '_') {
                tag = STRONG2;
            } else if (symbol == '-' && nextSymbol == '-') {
                tag = STRIKEOUT;
            } else if (symbol == '*') {
                tag = EMPHASIS1;
            } else if (symbol == '_') {
                tag = EMPHASIS2;
            } else if (symbol == '%') {
                tag = VARIABLE;
            } else if (symbol == '<') {
                bl.replace(i, i + 1, "&lt;");
            } else if (symbol == '>') {
                bl.replace(i, i + 1, "&gt;");
            } else if (symbol == '&') {
                bl.replace(i, i + 1, "&amp;");
            } else if (symbol == '\\') {
                bl.replace(i, i + 1, "");
            }
        }
    }

    static void removeSingleElements() {
        for (Map.Entry<HtmlElement, ArrayList<Integer>> entry : indexesOfUsedElements.entrySet()) {
            ArrayList<Integer> list = entry.getValue();
            if (list.size() > 0 && list.size() % 2 == 1) {
                list.remove(list.size() - 1);
                entry.setValue(list);
            }
        }
    }

    static boolean tagHasLength2(HtmlElement tag) {
        return tag instanceof Strong1 || tag instanceof Strong2 || tag instanceof Strikeout;
    }

}