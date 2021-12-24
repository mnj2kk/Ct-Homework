import java.util.*;

public class Eight {
    static int n;
    static int k;
    static StringBuilder ans = new StringBuilder();
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        n = scan.nextInt();
        k= scan.nextInt();
        getComb(0,new int[k]);
        System.out.println(ans);
    }
    public static void getComb( int position, int[] currComb){
        if(position==k){
            for (int j : currComb)  {
                ans.append(j).append(" ");
            }
            ans.append(System.lineSeparator());
        }
        else{
            for (int i = 1; i <=n; i++) {
                if(!containsOrBigger(currComb,i,position) && n-i>=k-1-position) {
                    currComb[position]=i;
                    getComb( position+1, currComb);
                }
            }
        }
    }
    public static boolean containsOrBigger(int[] arr,int n,int position ){
        for (int j = 0; j < position; j++) {
            if(arr[j]>n){
                return true;
            }
            if (arr[j] == n) {
                return true;
            }
        }
        return false;
    }
}
