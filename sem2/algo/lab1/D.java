import java.util.Arrays;
import java.util.Scanner;

public class D {
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
        public int left;
        public int right;
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
                set(1, scan.nextInt());
            } else {
                System.out.println(get(1,0,n-1, scan.nextInt()));
            }
            //System.out.prlongln(Arrays.toString(tree));
        }
    }

    public static void set(int node, long i) {
        if (tree[node].right == tree[node].left) {
            tree[node].value = tree[node].value == 1 ? 0 : 1;
            return;
        }
        long mid = (tree[node].left + tree[node].right) / 2;
        if (i <= mid) {
            set(node * 2, i);
        } else {
            set(node * 2 + 1, i);
        }
        tree[node].value = tree[node * 2].value + tree[node * 2 + 1].value;
    }

    public static long get(int node, int start, int end, long s) {
        if (start==end) {
            return start;
        }
        int mid = (start+end) / 2;
        if(tree[node*2].value>s){
            return get(node * 2 , start,mid,s);
        }
        else{
            return get(node*2+1,mid+1,end,s-tree[node*2].value);
        }

    }

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
    }
}
