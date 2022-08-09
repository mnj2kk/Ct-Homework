import java.io.*;
import java.util.*;

public class L {
    static int[] outP;
    static  int[] outL;
    static  int[] outR;
    static  Node[] arr;
    static class Node implements Comparable<Node> {
        int v;
        Node l;
        Node r;
        int k;
        int i;

        public Node(int v, int k, int i) {
            this.v = v;
            this.k = k;
            this.i = i;
        }

        @Override
        public int compareTo(Node node) {
            return -Integer.compare(node.v, this.v);
        }
    }

    public static void main(String[] args) {
        FastScanner scan = new FastScanner();
        int n = scan.nextInt();
        outL = new int[n];
        outR = new int[n];
        outP = new int[n];

        int c=1;
        while (c<n){
            c*=2;
        }
        arr = new Node[c];
        for (int i = 1; i <= n; i++) {
            arr[i - 1] = new Node(scan.nextInt(), scan.nextInt(), i);
        }
        for (int i = n; i < c; i++) {
            arr[i]=new Node(Integer.MAX_VALUE,Integer.MAX_VALUE,i);
        }
        Arrays.sort(arr);
        Node res = buildCartesian(0,n);
        System.out.println("YES");
        dfs(res, 0);
        StringBuilder o = new StringBuilder();
        for (int i = 0; i < n; i++) {
            o.append(outP[i]).append(" ").append(outL[i]).append(" ").append(outR[i]).append(System.lineSeparator());
        }
        System.out.println(o);

    }
    private static Node buildCartesian(int l,int r){
        if (l > arr.length) {
            return null;
        }
        if (r - l == 1) {
            return arr[l];
        }
        int m = (r + l) / 2;
        return merge(buildCartesian(l, m), buildCartesian(m, r));
    }

    private static void dfs(Node n, int parent) {
        if (n != null ) {
            int i =n.i-1;
            outP[i] = parent;
            outR[i] = (n.r != null ? n.r.i : 0);
            outL[i] =(n.l != null ? n.l.i : 0);
            if (n.r != null) {
                dfs(n.r, n.i);
            }
            if (n.l != null) {
                dfs(n.l, n.i);
            }
        }
    }

    private static Node merge(Node a, Node b) {
        if (a == null) {
            return b;
        }
        if (b == null) {
            return a;
        }
        if (a.k < b.k) {
            a.r = merge(a.r, b);
            return a;
        } else {
            b.l = merge(a, b.l);
            return b;
        }
    }

    static  class FastScanner {
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
