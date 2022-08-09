import java.util.Random;
import java.util.Scanner;

public class J {
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

        @Override
        public String toString() {
            return "Node{" +
                    "v=" + v +
                    ", l=" + l +
                    ", r=" + r +
                    ", pr=" + pr +
                    ", c=" + c +
                    ", revState=" + revState +
                    '}';
        }

        Node r;
        long pr = new Random().nextLong();
        int c;
        boolean revState = false;

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
        StringBuilder o;
        for (int i = 0; i < m; i++) {

            int s = scan.nextInt();
            int e = scan.nextInt();
            bin = rev(s, e, bin);

        }
        o = new StringBuilder();
        dfs(bin, o);
        System.out.println(o);
    }

    private static Node rev(int s, int e, Node a) {
        Pair t1=split(a,e);
        Pair t2=split(t1.a,s-1);
        t2.b.revState^=true;
        return merge(merge(t2.a,t2.b),t1.b);
    }
    private static void dfs(Node n, StringBuilder stringBuilder) {
        if (n != null) {
            push(n);
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
        push(a);
        push(b);
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

    private static void push(Node n) {
        if(n!=null && n.revState){
            if(n.l!=null){
                n.l.revState =!n.l.revState;
            }
            if(n.r!=null){
                n.r.revState=!n.r.revState;
            }
            Node t = n.r;
            n.r=n.l;
            n.l=t;
            n.revState=false;
        }
    }

    private static Pair split(Node n, int x) {
        push(n);
        if (n == null) {
            return new Pair(null, null);
        }
        int s = (n.l != null ? n.l.c : 0) ;
        if (s < x) {
            Pair t = split(n.r, x - s-1);
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
