import java.util.*;

public class Nine {
    static int n;
    static StringBuilder ans = new StringBuilder();

    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        n = scan.nextInt();
        rec(0, new char[2 * n], 0, 0);
        System.out.println(ans);
    }

    public static void rec(int position, char[] currBr, int opened, int closed) {
        if (position == 2 * n) {
            for (char j : currBr) {
                ans.append(j);
            }
            ans.append(System.lineSeparator());
        } else {
            for (int i = 0; i < 2; i++) {
                if (opened == n) {
                    currBr[position] = ')';
                    rec(position + 1, currBr, opened, closed + 1);
                    break;
                } else if (i == 1 && closed < opened) {
                    currBr[position] = ')';
                    rec(position + 1, currBr, opened, closed + 1);
                } else if (i == 0) {
                    currBr[position] = '(';
                    rec(position + 1, currBr, opened+1, closed );

                }
            }
        }
    }
}
