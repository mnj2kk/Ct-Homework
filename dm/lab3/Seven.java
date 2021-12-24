import java.util.*;

public class Seven {
    static int n;
    static StringBuilder ans = new StringBuilder();
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
         n = scan.nextInt();
         genPerm(0,0,new int[n]);
        System.out.println(ans);
    }
    public static void genPerm(int length, int position, int[] currComb){
        if(length==n){
            for (int j : currComb)  {
                ans.append(j).append(" ");
            }
            ans.append(System.lineSeparator());
        }
        else{
            for (int i = 1; i <=n; i++) {
                if(!contains(currComb,i,position)) {
                    currComb[position]=i;
                    genPerm( length + 1,position+1, currComb);
                }
            }
        }
    }
    public static boolean contains(int[] arr,int n,int position ){
        for (int j = 0; j < position; j++) {
            if (arr[j] == n) {
                return true;
            }
        }
        return false;
    }
}
