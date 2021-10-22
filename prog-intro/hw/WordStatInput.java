
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.LinkedHashMap;

public class WordStatInput {
    static Boolean isPartOfWorld(char ch) {
        return Character.DASH_PUNCTUATION == Character.getType(ch) || Character.isLetter(ch) || ch == '\'';
    }

    public static void main(String[] args) {
        LinkedHashMap<String, Integer> words = new LinkedHashMap<>();
        final String INPUT_FILE = args[0];
        final String OUTPUT_FILE = args[1];
        try {
            MyScanner scan = new MyScanner(INPUT_FILE, "UTF-8");
           while (scan.hasNextWord()) {
                String word = (scan.nextWord()).toLowerCase();
                if (words.containsKey(word)) {
                    words.put(word, words.get(word) + 1);
                } else {
                    words.put(word, 1);
                }
            }
           scan.close();
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
        try {
            BufferedWriter output = new BufferedWriter(
                    new OutputStreamWriter(
                            new FileOutputStream(OUTPUT_FILE), StandardCharsets.UTF_8));
            try {
                for (String word : words.keySet()) {
                    output.write(word + " " + words.get(word));
                    output.newLine();
                }
            } finally {
                output.close();
            }

        } catch (FileNotFoundException e) {
            System.out.println("File not found" + e.getMessage());
        } catch (IOException e) {
            System.out.println("File is busy" + e.getMessage());
        }
    }
}