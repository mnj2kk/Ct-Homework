import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.TreeMap;

public class WordStatWords {

    static Boolean isPartOfWorld(char ch) {
        return Character.DASH_PUNCTUATION == Character.getType(ch) || Character.isLetter(ch) || ch == '\'';
    }

    public static void main(String[] args) {
        TreeMap<String, Integer> words = new TreeMap<>();
        final String INPUT_FILE = args[0];
        final String OUTPUT_FILE = args[1];
        try {
            BufferedReader input = new BufferedReader(
                    new InputStreamReader(
                            new FileInputStream(INPUT_FILE), StandardCharsets.UTF_8));
            try {
                //In test max size of word is 10))
                char[] buffer = new char[20];
                int counter = 0;
                while (true) {
                    int read = input.read();
                    if (read == -1) {
                        break;
                    }
                    char ch = (char) read;
                    if (isPartOfWorld(ch)) {
                        buffer[counter] = ch;
                        counter++;
                    } else {
                        if (counter != 0) {
                            String word = new String(Arrays.copyOf(buffer, counter)).toLowerCase();
                            if (words.containsKey(word)) {
                                words.put(word, words.get(word) + 1);
                            } else {
                                words.put(word, 1);
                            }
                            counter = 0;
                        }
                    }
                }
            } finally {
                input.close();
            }
        } catch (FileNotFoundException e) {
            System.out.println("File not found" + e.getMessage());
        } catch (IOException e) {
            System.out.println("File is busy" + e.getMessage());
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
