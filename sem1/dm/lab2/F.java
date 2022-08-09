import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class F {
    static List<String> alphabet = new ArrayList<>(Arrays.asList(IntStream.rangeClosed('a', 'z').mapToObj(c -> (char) c + ",").collect(Collectors.joining()).split(",")));
    static String getLast(StringBuilder str){
        for (int i = 1; i < str.length(); i++) {
            if(!alphabet.contains(str.substring(0,i))){
                return str.substring(0,i);
            }
        }
        return str.toString();
    }
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        int n = scan.nextInt();
        List<Integer> input = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            input.add(scan.nextInt());
        }
        StringBuilder x = new StringBuilder(alphabet.get(input.get(0)));
        System.out.print(x);
        for (int i = 1; i < n; i++) {
            int y =input.get(i);
            if(alphabet.size() <=y){
                alphabet.add(x.toString() + (alphabet.get(input.get(i - 1))).charAt(0));
                x= new StringBuilder();
            }

            System.out.print(alphabet.get(y));
            StringBuilder xC =new StringBuilder(x);
            if(alphabet.contains(xC.append(alphabet.get(y)).toString())){
                x.append(alphabet.get(y));
            }
            else{
                 x = new StringBuilder(getLast(x.append(alphabet.get(y))));
                alphabet.add(x.toString());
                x=new StringBuilder(alphabet.get(y));
            }
       }
    }
}
