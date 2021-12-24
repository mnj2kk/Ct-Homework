import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Ten {
    static int sum ;
    static StringBuilder ans = new StringBuilder();
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        int sum = scan.nextInt();
            getRazb(sum, new ArrayList<>());
        System.out.println(ans);
    }
    public static void getRazb(int s, List<Integer> currComb){
        if(s==0){
            for (int j : currComb)  {
                ans.append(j).append("+");
            }
            ans.deleteCharAt(ans.length()-1);
            ans.append(System.lineSeparator());
        }
        else{
            for (int i = 1; i <=s; i++) {
                if(!containsOrBigger(currComb,i) && s-i>=0 ) {
                    currComb.add(i);
                    getRazb( s-i, currComb);
                    currComb.remove((Object)i);
                }
            }
        }
    }
    public static boolean containsOrBigger(List<Integer> arr,int n){
        for (Integer integer : arr) {
            if (integer < n) {
                return true;
            }
        }
        return false;
    }
}
