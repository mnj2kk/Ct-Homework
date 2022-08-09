import java.util.Arrays;
import java.util.Scanner;

public class Eleven {
    static int n;
    static int k;
    static StringBuilder ans = new StringBuilder();

    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        n = scan.nextInt();
        k = n;
        getComb(0, new int[k]);
        System.out.println();
        System.out.println(ans);
    }

    public static void getComb(int position, int[] currComb) {

        for (int i = 1; i <= n; i++) {
            if (!containsOrBigger(currComb, i, position) ) {
                Arrays.fill(currComb,position,k,0);
                currComb[position] = i;
                for (int j : currComb) {
                    if(j!=0) {
                        ans.append(j).append(" ");
                    }
                }
                ans.append(System.lineSeparator());
                getComb(position + 1, currComb);
            }

        }

    }

    public static boolean containsOrBigger(int[] arr, int n, int position) {
        for (int j = 0; j < position; j++) {
            if (arr[j] > n) {
                return true;
            }
            if (arr[j] == n) {
                return true;
            }
        }
        return false;
    }
}
