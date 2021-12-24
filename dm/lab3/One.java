import java.util.Scanner;

public class One {
    static int n;
    static StringBuilder ans = new StringBuilder();

    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        n = scan.nextInt();
        int end = 2 << (n - 1);
        genVect(0, 0, new int[n]);
        System.out.println(ans);
    }

    public static void genVect(int length, int position, int[] currComb) {
        if (length == n) {
            for (int j : currComb) {
                ans.append(j);
            }
            ans.append(System.lineSeparator());
        } else {
            for (int i = 0; i < 2; i++) {
                currComb[position] = i;
                genVect(length + 1, position + 1, currComb);
            }
        }
    }

}
