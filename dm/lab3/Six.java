import java.util.Scanner;
import java.util.List;
import java.util.ArrayList;
public class Six {
    public static void  main(String[] args){
    Scanner scan = new Scanner(System.in);
    int n = scan.nextInt();
    int end = 2 << (n-1);
    List<String> list = new ArrayList<String>();
    for(int i =0;i<end;i++){
        String tmp =Integer.toBinaryString(i);
        boolean has1 =false;
        boolean isR= true;
        for(int j=0;j<tmp.length();j++){
            if(has1 && tmp.charAt(j)=='1'){
                isR=false;
                break;
            }
            if(tmp.charAt(j)=='1'){
                has1=true;
            }
                if(tmp.charAt(j)=='0'){
                has1=false;
            }
        }
        if(isR){
            tmp="0".repeat(n-tmp.length()) +tmp;
            list.add(tmp);
        }
    }
    System.out.println(list.size());
    for(int i =0;i<list.size();i++){
        System.out.println(list.get(i));
    }
    }

}
