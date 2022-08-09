import java.util.Scanner;

public class C {
    static Node bin = new Node(Integer.MAX_VALUE, null, null);

    static class Node {
        int v;
        Node l;
        Node r;

        public Node(int v, Node l, Node r) {
            this.v = v;
            this.l = l;
            this.r = r;
        }
    }

    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        int n;
        while (scan.hasNext()) {
            String op = scan.next();
            switch (op) {
                case "insert":
                     n =scan.nextInt();
                    if (!exists(n,bin)) {
                        insert(n, bin);
                    }
                    break;
                case "delete":
                     n =scan.nextInt();
                    if (exists(n,bin)) {
                        delete(n, bin);
                    }
                    break;
                case "exists":
                    System.out.println(exists(scan.nextInt(), bin));
                    break;
                case "next":
                    Node r = next(scan.nextInt(), bin);
                    System.out.println((r==null|| r.v==Integer.MAX_VALUE)?"none":r.v);
                    break;
                case "prev":
                    Node prev = prev(scan.nextInt(), bin);
                    System.out.println((prev==null|| prev.v==Integer.MAX_VALUE)?"none":prev.v);
                    break;

            }
        }
    }

    private static Node delete(int x, Node n) {
        if (n == null) {
            return null;
        }
        if (x < n.v) {
            n.l= delete(x, n.l);
        } else if(x>n.v) {
            n.r= delete(x, n.r);
        }
        else if(n.l ==null || n.r ==null){
            if(n.r==null && n.l!=null){
                n=n.l;
            }
            else if(n.l==null && n.r!=null){
                n=n.r;
            }
            else{
                n=null;
            }
        }
        else{
            Node s =next(x,n.r);
            if(s!=null && s.v!=Integer.MAX_VALUE) {
                n.v = s.v;
                n.r = delete(s.v, n.r);
            }
        }
        return n;

    }

    private static Node prev(int x, Node n) {
        Node prev = null;
        while (n != null) {
            if (x <= n.v) {
                n = n.l;
            } else {
                prev = n;
                n = n.r;
            }
        }
        return prev;
    }

    private static Node next(int x, Node n) {
        Node next = null;
        while (n != null) {
            if (x < n.v) {
                next = n;
                n = n.l;
            } else {
                n = n.r;
            }
        }
        return next;

    }

    private static Node insert(int x, Node n) {
        if (n == null) {
            return new Node(x, null, null);
        }
        if (x < n.v) {
            n.l = insert(x, n.l);
        } else if(x> n.v) {
            n.r= insert(x, n.r);
        }
        return n;
    }

    private static boolean exists(int x, Node n) {
        if (x == n.v) {
            return true;
        }
        if (x < n.v) {
            return n.l != null && exists(x, n.l);
        } else {
            return n.r != null && exists(x, n.r);
        }

    }

}
