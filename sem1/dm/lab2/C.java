import java.util.ArrayList;
import java.util.Scanner;

import static java.lang.System.arraycopy;

public class C {
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        char[] line = scan.nextLine().toCharArray();
        ArrayList<String> list = new ArrayList<>();
        for (int i = 0; i < line.length; i++) {
            list.add("");
        }
        list.sort(null);
        for (int j = 0; j < line.length; j++) {
            for (int i = 0; i < line.length; i++) {
                list.set(i,line[i] +list.get(i));
            }
            list.sort(null);
        }
        System.out.println(list.get(0));

    }
}
