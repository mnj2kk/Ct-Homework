import java.util.Random;
import java.util.Scanner;

public class I {
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
        Node bin = new Node(1, null, null, 1);
        for (int i = 2; i <= n; i++) {
            bin = merge(bin, new Node(i, null, null, 1));
        }
        StringBuilder o = new StringBuilder();
        for (int i = 0; i < m; i++) {
            int s = scan.nextInt();
            int e = scan.nextInt();
            Pair t = split(bin, s-1);
            Pair t1 = split(t.b,e-(t.a!=null?t.a.c:0));
            bin = merge(t1.a,merge(t.a,t1.b)) ;


        }
        o = new StringBuilder();
        dfs(bin, o);
        System.out.println(o);
    }

    private static void dfs(Node n, StringBuilder stringBuilder) {
        if (n != null) {
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

    private static void upd(Node n) {
        n.c = (n.l != null ? n.l.c : 0) + (n.r != null ? n.r.c : 0) + 1;
    }
}
/*Node{v=3, l=Node{v=2, l=null, r=null, pr=3003367329961094512, c=1, revState=false}, r=Node{v=4, l=null, r=null, pr=-7391103855639612480, c=1, revState=false}, pr=9078727479148830072, c=3, revState=true}
Node{v=3, l=null, r=Node{v=5, l=Node{v=2, l=null, r=null, pr=3003367329961094512, c=1, revState=false}, r=null, pr=6949907733004366440, c=2, revState=false}, pr=9078727479148830072, c=3, revState=true}

Node{v=4, l=null, r=null, pr=-7391103855639612480, c=1, revState=true}*/