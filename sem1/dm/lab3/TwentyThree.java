import java.util.Scanner;

public class TwentyThree {
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        String bit = scan.nextLine();
        boolean smallHasFinished = false;
        StringBuilder small = new StringBuilder();
        StringBuilder big = new StringBuilder();
        boolean bigHasFinished = false;
        for (int i = bit.length()-1; i >= 0; i--) {
            if(bit.charAt(i)=='0'){
                if(!smallHasFinished){
                    small.append('1');
                }
                else{
                    small.append('0');
                }
                if(!bigHasFinished){
                    bigHasFinished=true;
                    big.append('1');
                }
                else{
                    big.append('0');
                }
            }
            else{
                if(!smallHasFinished){
                    small.append('0');
                    smallHasFinished = true;
                }
                else{
                    small.append('1');
                }
                if(!bigHasFinished){
                    big.append('0');
                }
                else{
                    big.append('1');
                }
            }
        }
        big.reverse();
        small.reverse();
        if(!bit.contains("0")){
            big=new StringBuilder("-");
        }
        if(!bit.contains("1")){
            small = new StringBuilder("-");
        }
        System.out.println(small);
        System.out.println(big);
    }
}

