import java.util.Arrays;
import java.util.Scanner;

import static java.lang.Long.MAX_VALUE;
import static java.lang.Long.min;

public class B {
    static long[] arr;
    static Node[] tree;

    static class Node {
        @Override
        public String toString() {
            return "Node{" +
                    "value=" + value +
                    ", left=" + left +
                    ", right=" + right +
                    ", c=" + c +
                    '}';
        }

        public long value  =MAX_VALUE;
        public long left;
        public long right;
        public int c = 1;
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
            //System.out.println(Arrays.toString(tree));
        }
    }

    public static void set(int node, long i, long v) {
        if (tree[node].right == tree[node].left) {
            tree[node].value = v;
            tree[node].c = 1;
            return;
        }
        long mid = (tree[node].left + tree[node].right) / 2;
        if (i <= mid) {
            set(node * 2, i, v);
        } else {
            set(node * 2 + 1, i, v);
        }
        tree[node].value = min(tree[node * 2].value, tree[node * 2 + 1].value);
        setMin(node);
    }

    public static Pair get(int node, long left, long right) {
        if (tree[node].left > right || tree[node].right < left) {
            return new Pair(MAX_VALUE,0);
        }
        if (tree[node].left == left && tree[node].right == right) {
            return new Pair(tree[node].value,tree[node].c);
        }
        long mid = (tree[node].left + tree[node].right) / 2;
        Pair p1 = get(node * 2, left, Math.min(mid, right));
        Pair p2 = get(node * 2 + 1, Math.max(left, mid + 1), right);
        return     setMin(p1,p2);
    }

    private static void buildTree(int i, int start, int end) {
        tree[i].left = start;
        tree[i].right = end;
        if (end == start) {
            tree[i].value = arr[start];
            tree[i].c = 1;
        } else {
            buildTree(2 * i, start, (end + start) / 2);
            buildTree(2 * i + 1, (end + start) / 2 + 1, end);
          setMin(i);

        }
    }

    private static Pair setMin(Pair a,Pair b) {
        Pair res = new Pair(MAX_VALUE,0);
        if (a.a == b.a) {
            res.a = a.a;
            res.b = a.b + b.b;
        } else {

            res.a = min(a.a, b.a);
            if (res.a == a.a) {
                res.b = a.b;
            } else {
                res.b = b.b;
            }
        }
        return res;
    }

    private static void setMin(int i) {
        Node a = tree[2 * i];
        Node b = tree[2 * i + 1];
        if (a.value == b.value) {
            tree[i].value = a.value;
            tree[i].c = a.c + b.c;
        } else {

            tree[i].value = min(a.value, b.value);
            if (tree[i].value == a.value) {
                tree[i].c = a.c;
            } else {
                tree[i].c = b.c;
            }
        }
    }
    private static class Pair{
        long a;

        public Pair(long a, int b) {
            this.a = a;
            this.b = b;
        }

        int b;

        @Override
        public String toString() {
            return  a + " "+ b;
        }
    }
}
