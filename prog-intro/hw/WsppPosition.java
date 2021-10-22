import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Map;

public class WsppPosition {
    public static void main(String[] args) {
        final String INPUT_FILE = args[0];
        final String OUTPUT_FILE = args[1];
        LinkedHashMap<String, ArrayList<String>> words = new LinkedHashMap<>();
        MyScanner scan;
        try {
            scan = new MyScanner(INPUT_FILE, "UTF-8");
            try {

                while (scan.hasNextWord()) {
                    String word = (scan.nextWord()).toLowerCase();
                    ArrayList<String> indexes;
                    if (words.containsKey(word)) {
                        indexes = words.get(word);
                    } else {
                        indexes = new ArrayList<>();
                    }

                    indexes.add(scan.line + ":" + scan.position);
                    words.put(word, indexes);


                }
            } finally {
                scan.close();
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
                    ArrayList<String> indexes = words.get(word);
                    output.write(word + " " + indexes.size());
                    for (String i : indexes) {
                        output.write(" " + i);
                    }
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
