import java.util.Arrays;
import java.util.Scanner;

public class Sixteen {
    static long[][] chooses;
    static int n;
    static int k;
    static long counter = 0;

    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        n = scan.nextInt();
        k = scan.nextInt();
        chooses = new long[n][k];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < k; j++) {
                chooses[i][j] = cnk(i, j);
            }
        }
        for (int i = 0; i < n; i++) {
            chooses[i][0] = 1;
        }
        int[] arr = new int[k + 1];
        for (int i = 1; i <= k; i++) {
            arr[i] = scan.nextInt();
        }
        arr[0] = 0;
        for (int i = 1; i <= k; i++) {
            for (int j = arr[i - 1] + 1; j <= arr[i] - 1; j++) {
                System.err.println(chooses[n-j][k-i]);
                counter += chooses[n - j][k - i];
            }
        }
        System.out.println(counter);
    }

    private static long cnk(int n, int k) {
        return (factorial(n) / factorial(n - k)) / factorial(k);
    }

    public static long factorial(int n) {
        long ret = 1;
        for (long i = 2; i <= n; i++) {
            ret *= i;
        }
        return ret;
    }
}
