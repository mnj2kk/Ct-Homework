import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class A {
    static     Map<Integer,Node> map ;
    static  class Node{
        int v;
        int l;
        int r;

        public Node(int v, int l, int r) {
            this.v = v;
            this.l = l;
            this.r = r;
        }
    }
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        int n = scan.nextInt();
     map= new HashMap<>();
        for (int i = 1; i <= n; i++) {
            map.put(i,new Node(scan.nextInt(), scan.nextInt(), scan.nextInt()));
        }
        int i =scan.nextInt();
        System.out.println(dfs(i,Integer.MAX_VALUE,0)?"YES":"NO");
        map.clear();
    }

    private static boolean dfs(int i,int max,int min) {
        if(i==-1){
            return true;
        }
        Node n=map.get(i);
        if(min < n.v && n.v < max){
            return dfs(n.l,n.v,min) && dfs(n.r,max,n.v);
        }
        return false;
    }
}
