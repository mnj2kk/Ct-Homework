import java.util.Scanner;

public class K {
    static int[] a;
    static int[][] sparse;
    static int n;
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        n  = scan.nextInt();
        int m = scan.nextInt();
        a = new int[n];

        a[0]= scan.nextInt();
        int u= scan.nextInt();
        int v = scan.nextInt();
        for (int i = 1; i < n; i++) {
            a[i] = (23*a[i-1]+21563)%16714589;
        }
        buildSparse();
        for (int i = 1; i < m; i++) {
            //System.out.println(u +" " + v +" " + getMin(u-1,v-1));
            int r = getMin(u-1,v-1);
            u=((17*u+751+r+2*i)%n)+1;
            v=((13*v+593+r+5*i)%n)+1;
        }
        System.out.println(u +" " + v +" " + getMin(u-1,v-1));

    }

    private static void buildSparse() {
        int log = fl(n);
        sparse = new int[log+3][n];
        sparse[0] = a;
        for (int i = 0; i <= log+3; i++) {
            for (int j = 0; j + (2 << (i))<=n; j++) {
                sparse[i+1][j]=Math.min(sparse[i][j], sparse[i ][j + (1 << (i))]);
            }
        }
        System.err.println();
    }

    private static int getMin(int u, int v) {
        if(u>v){
            int tmp = u;
            u=v;
            v=tmp;
        }
        int log = fl(v - u);
        if(((v-u)&(v-u - 1))!=1 && v-u!=0) {
            log--;
        }

        return Math.min(sparse[log][u], sparse[log][v - (1 << log) +1  ]);
    }

    private static int fl(int i) {
        if(i<1){
            return 0;
        }
        else{
            return fl(i/2)+1;
        }
    }
}