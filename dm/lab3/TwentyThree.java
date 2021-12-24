import java.util.Scanner;
public class TwentyThree{
    public static void  main(String[] args){
    Scanner scan = new Scanner(System.in);
    String str = scan.nextLine();
    int l = str.length();
        String p = Integer.toBinaryString(Integer.parseInt(str,2)-1);
    try{
        p= "0".repeat(l-p.length()) +p;
    }
    catch(Exception e){
    }
    String n = Integer.toBinaryString(Integer.parseInt(str,2)+1);
    try{
    n  = "0".repeat(l-n.length()) +n;
    }
    catch(Exception e){
    }
    if(Integer.parseInt(str,2)!=0){
    System.out.println(p);
    }
    else{
    System.out.println("-");
    }
     if(Integer.parseInt(str,2)!=(int)Math.pow(2,l)-1){
    System.out.println(n);
    }
    else{
    System.out.println("-");
    }
}
}

