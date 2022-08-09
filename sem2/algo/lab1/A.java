import java.util.Arrays;
import java.util.Scanner;

public class A {
    static long[] arr;
    static Node[] tree;

    static class Node {
        @Override
        public String toString() {
            return "Node{" +
                    "value=" + value +
                    ", left=" + left +
                    ", right=" + right +
                    '}';
        }

        public long value;
        public long left;
        public long right;
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
            if (scan.nextInt() == 1) {
                set(1, scan.nextInt(), scan.nextInt());
            } else {
                System.out.println(get(1, scan.nextInt(), scan.nextInt() - 1));
            }
            //System.out.prlongln(Arrays.toString(tree));
        }
    }

    public static void set(int node, long i, long v) {
        if (tree[node].right == tree[node].left) {
            tree[node].value = v;
            return;
        }
        long mid = (tree[node].left + tree[node].right) / 2;
        if (i <= mid) {
            set(node * 2, i, v);
        } else {
            set(node * 2 + 1, i , v);
        }
        tree[node].value = tree[node * 2].value + tree[node * 2 + 1].value;
    }

    public static long get(int node, long left, long right) {
        if (tree[node].left > right || tree[node].right < left) {
            return 0;
        }
        if (tree[node].left==left && tree[node].right==right){
            return tree[node].value;
        }
        long mid = (tree[node].left + tree[node].right) / 2;
        return get(node * 2 , left, Math.min(mid,right)) + get(node * 2 + 1, Math.max(left,mid+1),right);

    }
    static int c =0;
    private static void buildTree(int i, int start, int end) {
        tree[i].left = start;
        tree[i].right = end;
        if (end == start) {
            tree[i].value = arr[start];
        } else {
            buildTree(2 * i, start, (end + start) / 2);
            buildTree(2 * i + 1, (end + start) / 2 + 1, end);
            tree[i].value = tree[2 * i].value + tree[2 * i + 1].value;
        }
        c++;
        System.out.println(c);
    }
}
