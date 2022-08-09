import java.util.*;

public class B {
    class Node{
        int v;
        int l;
        int r;

        public Node(int v, int l, int r) {
            this.v = v;
            this.l = l;
            this.r = r;
        }
    }
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        int n = scan.nextInt();
        System.out.println(n);
         int[] arr = new int[n];
        for (int i = 0; i < n; i++) {
            arr[i]= scan.nextInt();
        }
        Arrays.sort(arr);
        StringBuilder ans = new StringBuilder();
        for (int i = 0; i < n-1; i++) {
            ans.append(arr[i]).append(" ").append(-1).append(" ").append(i+2).append("\n");
        }
        ans.append(arr[n-1]).append(" ").append(-1).append(" ").append(-1).append("\n").append(1);
        System.out.println(ans);
    }
}
