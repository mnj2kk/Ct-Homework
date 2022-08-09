import java.util.Random;
import java.util.Scanner;

public class G {
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
        scan.nextInt();
        Node bin = new Node(scan.nextInt(), null, null, 1);
        StringBuilder o = new StringBuilder();
        int count=1;
        for (int i = 1; i < n; i++) {
            int c = scan.nextInt();
            if (c==1) {
                int x = scan.nextInt();
                Pair t = split(bin, x);
                Node l = t.a;
                bin = merge(l, new Node(x, null, null, 1));
                bin = merge(bin, t.b);
                count++;
            } else if(c==0){
                int x = count -scan.nextInt()+1;
                Pair t = splitK(bin, x-1);
                Pair t1 = splitK(t.b,x-(t.a!=null?t.a.c:0));
                bin=merge(t.a,merge(t1.a,t1.b));
                System.out.println(t1.a.v);
            }else {
                int x = scan.nextInt();
                Pair t = split(bin,x);
                bin = merge(split(t.a,x-1).a,t.b);
                count--;
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
        if (n.v <= x) {
            Pair t = split(n.r, x);
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
    private static Pair splitK(Node n, int x) {
        if (n == null) {
            return new Pair(null, null);
        }
        int s = (n.l != null ? n.l.c : 0) + 1;
        if (s <= x) {
            Pair t = splitK(n.r, x - s );
            n.r = t.a;
            upd(n);
            return new Pair(n, t.b);
        } else {
            Pair t = splitK(n.l, x);
            n.l = t.b;
            upd(n);
            return new Pair(t.a, n);
        }
    }
    private static void upd(Node n) {
        n.c = (n.l != null ? n.l.c : 0) + (n.r != null ? n.r.c : 0) + 1;
    }
}
