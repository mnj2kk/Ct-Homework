import java.util.Random;
import java.util.Scanner;

public class D {
    static Node bin = new Node(Integer.MAX_VALUE, null, null);

    static class Pair {
        Node a;
        Node b;

        public Pair(Node a, Node b) {
            this.a = a;
            this.b = b;
        }
    }

    static class Node {
        public int d;
        int v;
        Node l;
        Node r;
        int c;

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
            //System.out.println(bin.c);
            switch (op) {
                case "insert":
                    n = scan.nextInt();
                    if (!exists(n, bin)) {
                        bin = insert(n, bin);
                    }
                    break;
                case "delete":
                    n = scan.nextInt();
                    if (exists(n, bin)) {
                        bin = delete(n, bin);
                    }
                    break;
                case "exists":
                    System.out.println(exists(scan.nextInt(), bin));
                    break;
                case "next":
                    Node r = next(scan.nextInt(), bin);
                    System.out.println((r == null || r.v == Integer.MAX_VALUE) ? "none" : r.v);
                    break;
                case "prev":
                    Node prev = prev(scan.nextInt(), bin);
                    System.out.println((prev == null || prev.v == Integer.MAX_VALUE) ? "none" : prev.v);
                    break;

            }

        }
    }

    private static Node insert(int x, Node n) {
        if (n == null) {
            return new Node(x, null, null);
        }
        if (x < n.v) {
            n.l = insert(x, n.l);
        } else if (x > n.v) {
            n.r = insert(x, n.r);
        }
        return correctBin(n);
    }

    private static Node delete(int x, Node n) {
        if (n == null) {
            return null;
        }
        if (x < n.v) {
            n.l = delete(x, n.l);
        } else if (x > n.v) {
            n.r = delete(x, n.r);
        } else if (n.l == null || n.r == null) {
            if (n.r == null && n.l != null) {
                n = n.l;
            } else if (n.l == null && n.r != null) {
                n = n.r;
            } else {
                n = null;
            }
        } else {
            Node s = next(x, n.r);
            if (s != null) {
                n.v = s.v;
                n.r = correctBin(delete(s.v, n.r));
            }
        }
        return correctBin(n);

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

    private static Node lSRotation(Node n) {
        Node t = n.r;
        n.r = t.l;
        t.l = n;
        upd(n);
        upd(t);
        return t;
    }

    private static Node rSRotation(Node n) {
        Node t = n.l;
        n.l = t.r;
        t.r = n;
        upd(n);
        upd(t);
        return t;
    }

    private static Node lBRotation(Node n) {
        n.r = rSRotation(n.r);
        return lSRotation(n);

    }

    private static Node rBRotation(Node n) {
        n.l = lSRotation(n.l);
        return rSRotation(n);

    }

    private static Node correctBin(Node n) {
        if (n != null) {
            upd(n);
            int l = n.l != null ? n.l.d : 3;
            int r = n.r != null ? n.r.d : 3;
            int lc = (n.l != null && n.l.r != null) ? n.l.r.d : 3;
            int rc = (n.r != null && n.r.l != null) ? n.r.l.d : 3;
            if (n.d == -2) {
                if (r == 0 || r == -1) {
                    return lSRotation(n);
                }
                if (r == 1 &&  (rc == 0 || rc == 1)) {
                    return lBRotation(n);
                }
            }
            if (n.d == 2) {
                if (l == 0 || l == 1) {
                    return rSRotation(n);
                }
                if (l == -1 && (lc == -1 || lc == 0 )) {
                    return rBRotation(n);
                }
            }
        }
        return n;
    }

    private static void upd(Node n) {
        if (n != null) {
            n.c = Math.max((n.l != null ? n.l.c : 0), (n.r != null ? n.r.c : 0)) + 1;
            n.d = (n.l != null ? n.l.c : 0) - (n.r != null ? n.r.c : 0);
        }
    }
}
