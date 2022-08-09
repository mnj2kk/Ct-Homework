import java.util.Random;
import java.util.Scanner;

public class F {
    static class Pair {
        Node a;
        Node b;

        public Pair(Node a, Node b) {
            this.a = a;
            this.b = b;
        }
    }

    static class Node {
        long v;
        long sum;
        Node l;
        Node r;
        long pr = new Random().nextLong();

        public Node(long v, Node l, Node r) {
            this.v = v;
            this.sum = v;
            this.l = l;
            this.r = r;
        }
    }

    static long MOD = 1000000000;
    public  static  long getSum(Node n){
        return n!=null? n.sum : 0;
    }
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        long n = scan.nextLong();
        StringBuilder o = new StringBuilder();
        Node bin = null;
        String prev = "+";
        long v = 0;
        for (long i = 0; i < n; i++) {
            String curr = scan.next();
            if(curr.equals("+")){
                if(prev.equals("+")){
                    bin =add(scan.nextLong(),bin);
                }
                else{
                    bin = add((scan.nextLong()+v)%MOD,bin);
                    v=0;
                }
            }
            else{
                long l = scan.nextLong();
                long r = scan.nextLong();
                Pair t1 = split(bin,r);
                Pair t2 = split(t1.a,l-1);
                v = t2.b!=null?t2.b.sum:0;
                o.append(v).append(System.lineSeparator());
                bin = merge(t2.a,t2.b);
                bin = merge(bin,t1.b);
            }
            prev=curr;
        }
        System.out.println(o);
    }

    private static Node add(long x, Node bin) {
        if(!exists(x,bin)) {
            Pair t1 = split(bin, x);
            bin = merge(t1.a, new Node(x, null, null));
            return merge(bin,t1.b);
        }
        return bin;
    }

    private static void dfs(Node n, StringBuilder stringBuilder) {
        if (n != null) {
            if (n.l != null) {
                dfs(n.l, stringBuilder);
            }
            stringBuilder.append(n.v).append(" ").append(n.sum).append("||");
            if (n.r != null) {
                dfs(n.r, stringBuilder);
            }
        }
    }

    private static boolean exists(long x, Node n) {
        if (n == null) {
            return false;
        }
        if (x == n.v) {
            return true;
        }
        if (x < n.v) {
            return exists(x, n.l);
        } else {
            return exists(x, n.r);
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
            recalc(a);
            return a;
        } else {
            b.l = merge(a, b.l);
            recalc(b);
            return b;
        }
    }

    private static Pair split(Node n, long x) {
        if (n == null) {
            return new Pair(null, null);
        }
        if (n.v <= x) {
            Pair t = split(n.r, x);
            n.r = t.a;
            recalc(n);
            return new Pair(n, t.b);
        } else {
            Pair t = split(n.l, x);
            n.l = t.b;
            recalc(n);
            return new Pair(t.a, n);
        }
    }

    private static void recalc(Node n) {
        if (n != null) {
            n.sum = (n.v +getSum(n.l) + getSum(n.r));
        }
    }

}