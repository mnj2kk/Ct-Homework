import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;
import java.util.StringTokenizer;

public class E {
    static int s = 0;
    static int l = 0;
    static int[] arr;

    static class Pair {
        Node a;
        Node b;

        public Pair(Node a, Node b) {
            this.a = a;
            this.b = b;
        }
    }

    static class Node {
        int v;
        Node l;
        Node r;
        long pr = new Random().nextLong();
        int c;

        public Node(int v, Node l, Node r, int c) {
            this.v = v;
            this.l = l;
            this.r = r;
            this.c = c;
        }
    }

    public static void main(String[] args) {
        FastScanner scan = new FastScanner();
        int n = scan.nextInt();
        int m = scan.nextInt();
        s = m;
        Node bin = new Node(0, null, null, 1);
        for (int i = 1; i < m; i++) {
            bin = merge(bin, new Node(0, null, null, 1));
        }
        StringBuilder o = new StringBuilder();
        for (int i = 1; i <= n; i++) {
            bin = insert(scan.nextInt(), i, bin);
        }
        o = new StringBuilder();
        arr = new int[s];
        dfs(bin, 0);
        int r = 0;
        for (int i = s - 1; i >= 0; i--) {
            if (arr[i] != 0) {
                r = i;
                break;
            }
        }
        System.out.println(r + 1);
        for (int i = 0; i <= r; i++) {
            o.append(arr[i]).append(" ");
        }
        System.out.println(o);
    }

    private static int dfs(Node n, int i) {
        if (n.l != null) {
            i = dfs(n.l, i);
        }
        arr[i] = n.v;
        i++;
        if (n.r != null) {
            i = dfs(n.r, i);
        }

        return i;
    }

    private static Node merge(Node a, Node b) {
        if (a == null) {
            return b;
        }
        if (b == null) {
            return a;
        }
        if (a.pr > b.pr) {
            a.r = merge(a.r, b);
            upd(a);
            return a;
        } else {
            b.l = merge(a, b.l);
            upd(b);
            return b;
        }
    }

    private static Pair split(Node n, int x) {
        if (n == null) {
            return new Pair(null, null);
        }
        int s = (n.l != null ? n.l.c : 0) + 1;
        if (s <= x) {
            Pair t = split(n.r, x - s);
            n.r = t.a;
            upd(n);
            return new Pair(n, t.b);
        } else {
            Pair t = split(n.l, x);
            n.l = t.b;
            upd(n);
            return new Pair(t.a, n);
        }
    }

    private static Node insert(int k, int v, Node n) {
        if (k > s) {
            s++;
            n = merge(n, new Node(v, null, null, 1));
        } else {
            Pair t1 = split(n, k);
            Pair t2 = split(t1.a, k - 1);
            int t = t2.b.v;

            t2.b.v = v;
            n = merge(t2.a, t2.b);
            n = merge(n, t1.b);

            if (t != 0) {

                n = insert(k + 1, t, n);
            }
        }

        return n;
    }

    private static int contains(Node n, int x) {
        int s = (n.l != null ? n.l.c : 0) + 1;
        if (s < x) {
            return contains(n.r, x - s);
        } else if (s == x) {
            return n.v;
        } else {
            return contains(n.l, x);
        }
    }


    private static void upd(Node n) {
        n.c = (n.l != null ? n.l.c : 0) + (n.r != null ? n.r.c : 0) + 1;
    }

    static class FastScanner {
        BufferedReader br;
        StringTokenizer st;

        FastScanner() {
            br = new BufferedReader(new InputStreamReader(System.in));
        }

        String next() {
            while (st == null || !st.hasMoreTokens()) {
                try {
                    st = new StringTokenizer(br.readLine());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return st.nextToken();
        }

        int nextInt() {
            return Integer.parseInt(next());
        }
    }


}