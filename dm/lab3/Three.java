import java.util.Scanner;
import java.util.List;
import java.util.ArrayList;
public class Three{
    public static void  main(String[] args){
    Scanner scan = new Scanner(System.in);
    int n = scan.nextInt();
    int end = (int) Math.pow(3,n-1);
    for(int i =0;i<end;i++){
        String tmp =Integer.toString(i,3);
        tmp ="0".repeat(n-tmp.length()) +tmp;
        System.out.println(tmp);
        for (int j =0;j< 2;j++){
            String tmptmp = "";
            for(int k =0;k<n;k++){
                if(tmp.charAt(k)=='0'){
                tmptmp +='1';
                }
                 if(tmp.charAt(k)=='1'){
                tmptmp+='2';
                }
                if(tmp.charAt(k)=='2'){
                tmptmp+='0';
                }
            }
            tmp=tmptmp;
            System.out.println(tmp);
        }

    }
    }

    public static List<String> reverse(List<String> list ){
         List<String> tmp = new  ArrayList<String>();
        for(int i =list.size()-1;i>=0;i--){
            tmp.add(list.get(i));
        }
        return tmp;
    }
}
