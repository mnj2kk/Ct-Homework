import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class D {
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        char[] line = scan.nextLine().toCharArray();
        List<String> alphabet = new ArrayList<>(Arrays.asList(IntStream.rangeClosed('a', 'z').mapToObj(c -> (char) c + ",").collect(Collectors.joining()).split(",")));
        for (char symbol : line) {
            alphabet.add(0, String.valueOf(symbol));
            int id = alphabet.lastIndexOf(String.valueOf(symbol));
            alphabet.remove(id);
            System.out.print(id + " ");
        }
    }
}
