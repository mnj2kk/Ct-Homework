import java.util.Arrays;
import java.util.Scanner;

import static java.lang.Long.min;

public class F {
    static long MAX_VALUE = Long.MAX_VALUE-1;
    static long[] arr;
    static Node[] tree;

    static class Node {
        Node() {
            this.t = MAX_VALUE;
        }

        @Override
        public String toString() {
            return "Node{" +
                    "value=" + value +
                    ", left=" + left +
                    ", right=" + right +
                    ", t=" + t +
                    "} \n";
        }

        public long value;
        public long left;
        public long right;
        public long t;
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
        tree = new Node[4 * n];
        for (int i = 0; i < 4 * n; i++) {
            tree[i] = new Node();
        }
        buildTree(1, 0, n - 1);
        for (long i = 0; i < m; i++) {
            if (scan.nextInt() == 1) {
                set(1, scan.nextInt(), scan.nextInt() - 1, scan.nextInt());
            } else {
                System.out.println(get(1, scan.nextInt(), scan.nextInt() - 1));

            }
            // System.out.println(Arrays.toString(tree));
        }
    }

    public static void set(int node, long s, long e, long v) {
        if (tree[node].right < s || tree[node].left > e) {
            return;
        }
        if (s <= tree[node].left && tree[node].right <= e) {
            tree[node].t = v;
        } else {
            long mid = (tree[node].left + tree[node].right) / 2;
            push(node);
            set(node * 2, s, e, v);
            set(node * 2 + 1, s, e, v);
            long l = tree[node * 2 + 1].t == MAX_VALUE ? tree[node * 2 + 1].value : tree[node * 2 + 1].t;
            long r = tree[node * 2].t == MAX_VALUE ? tree[node * 2].value : tree[node * 2].t;

            tree[node].value = Math.min(l, r);
        }
    }

    public static long get(int node, long left, long right) {
        if (tree[node].left > right || tree[node].right < left) {
            return MAX_VALUE;
        }
        push(node);
        if (left <= tree[node].left && tree[node].right <= right) {
            return tree[node].value;
        }
        long i1 = get(node * 2, left, right);
        long i2 = get(node * 2 + 1, left, right);
        return min(i1, i2);

    }

    public static void push(int node) {
        if (tree[node].t != MAX_VALUE) {
            tree[node].value = tree[node].t;
            tree[node * 2].t = tree[node].t;
            tree[node * 2 + 1].t = tree[node].t;
            tree[node].t = MAX_VALUE;
        }
    }

    private static void buildTree(int i, int start, int end) {
        tree[i].left = start;
        tree[i].right = end;
        if (end != start) {
            buildTree(2 * i, start, (end + start) / 2);
            buildTree(2 * i + 1, (end + start) / 2 + 1, end);
        }
    }
}
