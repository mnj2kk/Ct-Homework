import java.util.*;

import static java.lang.System.arraycopy;
public class B {
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        char[] line = scan.nextLine().toCharArray();
        List<String> words = new ArrayList<String>();
        for (int i = 0; i < line.length; i++) {
            char firstElem = line[0];
            arraycopy(line,1,line,0,line.length-1);
            line[line.length-1]=firstElem;
            words.add(new String(line));
        }
        words.sort(null);
        for (String word:words) {
            System.out.print(word.charAt(line.length-1));
        }
    }
}
