import scanner.MyScanner;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class Wspp{
    public static void main(String[] args) {
        final String INPUT_FILE = args[0];
        final String OUTPUT_FILE = args[1];
        Map<String, List<Integer>> words = new LinkedHashMap<>();
        MyScanner scan;
        try {
            scan = new MyScanner(INPUT_FILE, "UTF-8");
            try {
                int i =1;
                while (scan.hasNextWord()) {
                    String word = (scan.nextWord()).toLowerCase();
                    List<Integer> indexes;
                    indexes = words.getOrDefault(word,new ArrayList<>());
                    indexes.add(i);
                    words.put(word, indexes);
                    i++;
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
            try (BufferedWriter output = new BufferedWriter(
                    new OutputStreamWriter(
                            new FileOutputStream(OUTPUT_FILE), StandardCharsets.UTF_8))) {
                for (var word : words.entrySet()) {
                    List<Integer> indexes = word.getValue();
                    output.write(word.getKey() + " " + indexes.size());
                    for (Integer i : indexes) {
                        output.write(" " + i);
                    }
                    output.newLine();
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("File not found" + e.getMessage());
        } catch (IOException e) {
            System.out.println("File is busy" + e.getMessage());
        }
    }
}
