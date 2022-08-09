import scanner.MyScanner;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class WsppPosition {
    public static void main(String[] args) {
        final String INPUT_FILE = args[0];
        final String OUTPUT_FILE = args[1];
        Map<String, List<String>> words = new LinkedHashMap<>();
        MyScanner scan;
        try {
            scan = new MyScanner(INPUT_FILE, "UTF-8");
            try {
                while (scan.hasNextWord()) {
                    String word = (scan.nextWord()).toLowerCase();
                    List<String> indexes;
                    indexes = words.getOrDefault(word, new ArrayList<>());


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
        try (BufferedWriter output = new BufferedWriter(
                new OutputStreamWriter(
                        new FileOutputStream(OUTPUT_FILE), StandardCharsets.UTF_8))) {
            for (var word : words.entrySet()) {
                List<String> indexes = word.getValue();
                output.write(word.getKey() + " " + indexes.size());
                for (String i : indexes) {
                    output.write(" " + i);
                }
                output.newLine();
            }
        } catch (IOException e) {
            System.out.println("File is busy" + e.getMessage());
        }
    }
}
//upd
