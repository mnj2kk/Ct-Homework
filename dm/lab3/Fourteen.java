import java.util.*;

public class Fourteen {
    static int n;
    static long counter = 0;
    static int[] line;

    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        n = scan.nextInt();
        line = new int[n];
        for (int i = 0; i < n; i++) {
            line[i] = scan.nextInt();
        }
        get(0);
        System.out.println(counter);

    }

    public static void get(int length) {
        if (length +1  != n) {
            for (int i = 1; i <= n; i++) {
                if (line[length] == i) {
                    get(length + 1);
                    break;
                } else if (!contains(line, i, length)) {
                    counter += factorial(n- length -1 );
                }

            }
        }
    }

    public static boolean contains(int[] arr, int n, int position) {
        for (int j = 0; j < position; j++) {
            if (arr[j] == n) {
                return true;
            }
        }
        return false;
    }

    public static long factorial(int n) {
        long ret = 1;
        for (long i = 2; i <= n; i++) {
            ret *= i;
        }
        return ret;
    }
}
