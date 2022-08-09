package scanner;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

public class MyScanner {
    BufferedReader reader;
    int BUFFER_LENGTH = 1024;
    int i = 0;
    public int line = 1;
    public int position = 0;


    public MyScanner(InputStream stream) {
        reader = new BufferedReader(new InputStreamReader(stream), BUFFER_LENGTH);
    }

    public MyScanner(String file, String encoding) throws IOException {
        reader = new BufferedReader(new InputStreamReader(new FileInputStream(file),StandardCharsets.UTF_8), BUFFER_LENGTH);
    }

    public MyScanner(String str) {
        reader = new BufferedReader(new InputStreamReader(new ByteArrayInputStream(str.getBytes())), BUFFER_LENGTH);
    }

    public String next(Checker symbol) throws IOException {
        char[] object = new char[16];
        int objectCounter = 0;
        i = reader.read();
        goToNextObject(symbol);
        char ch = (char) i;

        while (symbol.check(ch) && i != -1) {
            object[objectCounter] = ch;
            objectCounter++;
            if (objectCounter == object.length) {
                object = Arrays.copyOf(object, objectCounter * 2);
            }
            i = reader.read();
            ch = (char) i;
        }
        position++;
        return new String(Arrays.copyOf(object, objectCounter));
    }

    public boolean hasNextObject(Checker symbol) throws IOException {
        reader.mark(BUFFER_LENGTH);
        goToNextObjectWithCountingLines(symbol);
        reader.reset();
        return i != -1;
    }

    public void goToNextObject(Checker symbol) throws IOException {
        while (!symbol.check((char) i) && i != -1) {
            i = reader.read();
        }


    }

    public void goToNextObjectWithCountingLines(Checker symbol) throws IOException {
        countLine();
        while (!symbol.check((char) i) && i != -1) {
            i = reader.read();
            countLine();
        }


    }

    private void countLine() {
        if (isLineSeparator((char) i)) {
            line++;
            position = 0;
        }

    }

    private boolean isLineSeparator(char ch) {
        return ch == System.lineSeparator().charAt(0);
    }

    public int nextInt() throws IOException {
        try {
            return Integer.parseInt(next(ObjectDelimiters::isInt));
        } catch (NumberFormatException npm) {
            throw new NumberFormatException("Next object isn't int");
        }
    }

    public boolean hasNextInt() throws IOException {
        return hasNextObject(ObjectDelimiters::isInt);
    }

    public String nextLine() throws IOException {
        return reader.readLine();
    }

    public boolean hasNextLine() throws IOException {
        reader.mark(2);
        int i = reader.read();
        reader.reset();
        return i != -1;
    }

    public String nextWord() throws IOException {
        return next(ObjectDelimiters::isWord);
    }

    public boolean hasNextWord() throws IOException {
        return hasNextObject(ObjectDelimiters::isWord);
    }

    public boolean hasNextAbc2Int() throws IOException {
        return hasNextObject(ObjectDelimiters::isAbc2Int);
    }

    public int nextAbc2Int() throws IOException {
        char[] n = next(ObjectDelimiters::isAbc2Int).toCharArray();
        for (int j = 0; j < n.length; j++) {
            n[j] = parseFromAbcToInt(n[j]);
        }
        try {

            return Integer.parseInt(new String(n));
        } catch (NumberFormatException npm) {
            throw new NumberFormatException("Next object isn't Abc2Int");
        }
    }

    public void close() throws IOException {
        reader.close();
        i = 0;
    }

    static char parseFromAbcToInt(char ch) {
        if (Character.isLetter(ch)) {
            return (char) ((int) ch - 49);
        } else {
            return ch;
        }
    }
    public interface Checker {
        boolean check(char c) throws IOException;
    }

    public static class ObjectDelimiters {

        static boolean isInt(char ch) {
            return Character.isDigit(ch) || ch == '-';
        }

        static boolean isWord(char ch) {
            return Character.DASH_PUNCTUATION == Character.getType(ch) || Character.isAlphabetic(ch) || ch == '\'';
        }

        static boolean isAbc2Int(char ch) {
            return Character.isDigit(ch) || ch == '-' || Character.isLetter(ch);
        }


    }


}



