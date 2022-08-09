import java.util.Arrays;
import java.util.Scanner;

public class C {
    static long[] arr;
    static Node[] tree;

    static class Node {
        public long maxL;
        public long maxR;
        public long max;
        public long sum;
        public long left;
        public long right;
        Node(){
            sum = Integer.MIN_VALUE;
            maxL = Integer.MIN_VALUE;
            maxR = Integer.MIN_VALUE;
            max = Integer.MIN_VALUE;
        }
        @Override
        public String toString() {
            return "Node{" +
                    "maxL=" + maxL +
                    ", maxR=" + maxR +
                    ", max=" + max +
                    ", sum=" + sum +
                    ", left=" + left +
                    ", right=" + right +
                    '}';
        }
    }

    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        int n = scan.nextInt();
        int m = scan.nextInt();
        int[] pows = new int[20];
        int c = 1;
        for (int i = 0; i < 20; i++) {
            c *= 2;
            pows[i] = c;
        }
        int tmp = Arrays.binarySearch(pows, n);
        arr = new long[pows[tmp >= 0 ? tmp : -tmp - 1]];
        for (int i = 0; i < n; i++) {
            arr[i] = scan.nextLong();
        }
        tree = new Node[4 * n];
        for (int i = 0; i < 4 * n; i++) {
            tree[i] = new Node();
        }
        buildTree(1, 0, n - 1);
        for (long i = 0; i < m; i++) {
            System.out.println(tree[1].max);
            set(1, scan.nextInt(), scan.nextInt());
        }
        System.out.println(tree[1].max);
    }

    public static void set(int node, int i, long v) {
        if (tree[node].right == tree[node].left) {
            tree[node].sum = v;
            tree[node].max = Math.max(v,0);
            tree[node].maxL = Math.max(v,0);
            tree[node].maxR = Math.max(v,0);
            return;
        }
        long mid = (tree[node].left + tree[node].right) / 2;
        if (i <= mid) {
            set(node * 2, i, v);
        } else {
            set(node * 2 + 1, i, v);
        }
        update(node);
    }


    private static void buildTree(int i, int start, int end) {
        tree[i].left = start;
        tree[i].right = end;
        if (end == start) {
            tree[i].sum = arr[start];
            tree[i].max = Math.max(arr[start],0);
            tree[i].maxR = Math.max(arr[start],0);
            tree[i].maxL = Math.max(arr[start],0);
        } else {
            buildTree(2 * i, start, (end + start) / 2);
            buildTree(2 * i + 1, (end + start) / 2 + 1, end);
            update(i);
        }
    }

    private static void update(int i) {
        tree[i].sum = tree[2 * i].sum + tree[2 * i + 1].sum;
        tree[i].maxL = Math.max(tree[2*i].sum + tree[2*i+1].maxL,tree[2*i].maxL );
        tree[i].maxR = Math.max(tree[2*i].maxR + tree[2*i+1].sum,tree[2*i+1].maxR );
        tree[i].max = Math.max(tree[2*i].maxR + tree[2*i+1].maxL,Math.max(tree[2*i].max,tree[2*i+1].max));
    }
}
