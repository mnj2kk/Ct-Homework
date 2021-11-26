import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class E {
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        List<String> alphabet = new ArrayList<>(Arrays.asList(IntStream.rangeClosed('a', 'z').mapToObj(c -> (char) c + ",").collect(Collectors.joining()).split(",")));
        String line = scan.nextLine();
        String buffer = String.valueOf(line.charAt(0));
        for (int i = 1; i < line.length(); i++) {
            char c = line.charAt(i);
            if(alphabet.contains(buffer + c)){
                buffer+=c;
            }
            else{
                System.out.print(alphabet.indexOf(buffer)+" ");
                alphabet.add(buffer+c);
                buffer= String.valueOf(c);
            }
        }
        System.err.println(alphabet);
        System.out.println(alphabet.indexOf(buffer));
    }
}
