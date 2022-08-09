import java.util.Random;
import java.util.Scanner;

public class H {
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
        Scanner scan = new Scanner(System.in);
        int n = scan.nextInt();
        int m = scan.nextInt();
        Node bin = new Node(scan.nextInt(), null, null, 1);
        for (int i = 1; i < n; i++) {
            bin = merge(bin, new Node(scan.nextInt(), null, null, 1));
        }
        int c =n;
        StringBuilder o;
        for (int i = 0; i < m; i++) {
            if (scan.next().equals("add")) {
                int x = scan.nextInt();
                Pair t = split(bin, x);
                Node l = t.a;
                bin = merge(l, new Node(scan.nextInt(), null, null, 1));
                bin = merge(bin, t.b);
                c++;
            } else {
                int x = scan.nextInt();
                Pair t = split(bin,x);
                bin = merge(split(t.a,x-1).a,t.b);
                c--;
            }

        }
        System.out.println(c);
        o = new StringBuilder();
        dfs(bin, o);
        System.out.println(o);
    }

    private static void dfs(Node n, StringBuilder stringBuilder) {
        if(n!= null) {
            if (n.l != null) {
                dfs(n.l, stringBuilder);
            }
            stringBuilder.append(n.v).append(" ");
            if (n.r != null) {
                dfs(n.r, stringBuilder);
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
            Pair t = split(n.r, x - s );
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

    private static void upd(Node n) {
        n.c = (n.l != null ? n.l.c : 0) + (n.r != null ? n.r.c : 0) + 1;
    }
}
