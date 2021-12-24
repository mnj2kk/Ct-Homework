import java.util.Scanner;
public class One {
    public static void  main(String[] args){
    Scanner scan = new Scanner(System.in);
    int n = scan.nextInt();
    int end = 2 << (n-1);
    for(int i =0;i<end;i++){
        String tmp =Integer.toBinaryString(i);
        System.out.println("0".repeat(n-tmp.length()) +tmp);
    }
    }
}
