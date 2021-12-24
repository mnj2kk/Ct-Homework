import java.util.ArrayList;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

public class Four {
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        int n  = scan.nextInt();
        String prev = "0".repeat(n);
        Set<String> used = new HashSet<>();
        used.add(prev);
        System.out.println(prev);
        StringBuilder stringBuilder = new StringBuilder();
        int end = (2 << (n-1)) -1;
        for (int i = 0; i < end; i++) {
            String tmp = prev.substring(1) +"1";
            if(!used.contains(tmp)){
            }
            else{
                tmp = prev.substring(1) +"0";
            }
            stringBuilder.append(tmp).append(System.lineSeparator());
            used.add(tmp);
            prev=tmp;
        }
        System.out.println(stringBuilder);

    }
}
